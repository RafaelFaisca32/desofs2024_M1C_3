package com.mycompany.myapp.application.controller;

import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.manager.dto.ManagerDTO;
import com.mycompany.myapp.domain.user.User;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.user.dto.CompleteUserDTO;
import com.mycompany.myapp.domain.user.mapper.UserMapper;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.domain.shared.MailService;
import com.mycompany.myapp.domain.user.UserService;
import com.mycompany.myapp.domain.user.dto.AdminUserDTO;
import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import com.mycompany.myapp.application.controller.errors.EmailAlreadyUsedException;
import com.mycompany.myapp.application.controller.errors.LoginAlreadyUsedException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the {@link com.mycompany.myapp.domain.user} entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api/admin")
public class UserController {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList(
            "id",
            "login",
            "firstName",
            "lastName",
            "email",
            "activated",
            "langKey",
            "createdBy",
            "createdDate",
            "lastModifiedBy",
            "lastModifiedDate"
        )
    );

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;


    private final MailService mailService;

    private final UserMapper userMapper;

    public UserController(UserService userService, MailService mailService, UserMapper userMapper) {
        this.userService = userService;
        this.mailService = mailService;
        this.userMapper = userMapper;
    }

    /**
     * {@code POST  /admin/users}  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param completeUserDTO the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user, or with status {@code 400 (Bad Request)} if the login or email is already in use.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if the login or email is already in use.
     */
    @PostMapping("/users")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public ResponseEntity<User> createUser(@Valid @RequestBody CompleteUserDTO completeUserDTO) throws URISyntaxException {

        AdminUserDTO userDTO = completeUserDTO.getAdminUserDTO();
        ApplicationUserDTO applicationUserDTO = completeUserDTO.getApplicationUserDTO();
        DriverDTO driverDTO = completeUserDTO.getDriverDTO();
        CustomerDTO customerDTO = completeUserDTO.getCustomerDTO();
        ManagerDTO managerDTO = completeUserDTO.getManagerDTO();

        Optional<AdminUserDTO> meDTO = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new);

        log.debug("REST request to save User : {}, " +
                "with Application User : {}, " +
                "with Driver : {}, " +
                "with Customer : {}, " +
                "with Manager : {}",
            userDTO,applicationUserDTO,driverDTO,customerDTO,managerDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (applicationUserDTO == null) {
            throw new BadRequestAlertException("User must contain Application User information", "userManagement", "noapplicationuserinfo");
        } else if (userDTO.getAuthorities() == null) {
            throw new BadRequestAlertException("User must contain at least 1 role", "userManagement", "nouserrole");
        } else if ( meDTO.isEmpty() ||
            (!meDTO.get().getAuthorities().contains(AuthoritiesConstants.ADMIN)
                && userDTO.getAuthorities().contains(AuthoritiesConstants.ADMIN))
        ){
            throw new BadRequestAlertException("You have to be an admin to create an admin!", "userManagement", "cancreateadmininssuficientrole");
        } else if (userDTO.getAuthorities().contains(AuthoritiesConstants.DRIVER) && driverDTO == null) {
            throw new BadRequestAlertException("A driver must contain its information", "userManagement", "nodriverinfo");
        } else if(userDTO.getAuthorities().contains(AuthoritiesConstants.MANAGER) && managerDTO == null) {
            throw new BadRequestAlertException("A manager must contain its information", "userManagement", "nomanagerinfo");
        } else if(userDTO.getAuthorities().contains(AuthoritiesConstants.CUSTOMER) && customerDTO == null){
            throw new BadRequestAlertException("A customer must contain its information", "userManagement", "nocustomerinfo");
        } else if (userService.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userService.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {


            //User newUser = userService.createUser(userDTO);
            User newUser = userService.createUserWithApplicationUser(userDTO,applicationUserDTO,driverDTO,customerDTO, managerDTO);

            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/admin/users/" + newUser.getLogin()))
                .headers(
                    HeaderUtil.createAlert(applicationName, "A user is created with identifier " + newUser.getLogin(), newUser.getLogin())
                )
                .body(newUser);
        }
    }

    /**
     * {@code PUT /admin/users/activate/{id}} : Activates an existing User.
     *
     * @param id id of the user to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user.
     */
    @PutMapping({ "/users/activate/{id}" })
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public ResponseEntity<AdminUserDTO> activateUser(
        @PathVariable(name = "id") Long id
    ) {

        Optional<AdminUserDTO> updatedUser = userService.activateUser(id);

        return ResponseUtil.wrapOrNotFound(
            updatedUser,
            HeaderUtil.createAlert(applicationName, "A user is activated with identifier " + id, id.toString())
        );
    }

    /**
     * {@code PUT /admin/deactivate/users} : Deactivates an existing User.
     *
     * @param id id of the user to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user.
     */
    @PutMapping({ "/users/deactivate/{id}" })
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public ResponseEntity<AdminUserDTO> validateUser(
        @PathVariable(name = "id") Long id
    ) {

        Optional<AdminUserDTO> updatedUser = userService.deactivateUser(id);

        return ResponseUtil.wrapOrNotFound(
            updatedUser,
            HeaderUtil.createAlert(applicationName, "A user is deactivated with identifier " + id, id.toString())
        );
    }



    /**
     * {@code GET /admin/users} : get all users with all the details - calling this are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public ResponseEntity<List<AdminUserDTO>> getAllUsers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all User for an admin");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<AdminUserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * {@code GET /admin/users/:login} : get the "login" user.
     *
     * @param login the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "login" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users/{login}")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.MANAGER + "')")
    public ResponseEntity<AdminUserDTO> getUser(@PathVariable("login") @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(userService.getUserWithAuthoritiesByLogin(login).map(AdminUserDTO::new));
    }

    /**
     * {@code DELETE /admin/users/:login} : delete the "login" User.
     *
     * @param login the login of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    /*
    DISABLED
    @DeleteMapping("/users/{login}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable("login") @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createAlert(applicationName, "A user is deleted with identifier " + login, login))
            .build();
    }
     */
}

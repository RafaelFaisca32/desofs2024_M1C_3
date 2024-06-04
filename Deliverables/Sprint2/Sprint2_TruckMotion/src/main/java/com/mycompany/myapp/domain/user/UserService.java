package com.mycompany.myapp.domain.user;

import com.mycompany.myapp.application.controller.errors.BadRequestAlertException;
import com.mycompany.myapp.application.controller.errors.LoginAlreadyUsedException;
import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.customer.CustomerService;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.driver.DriverService;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.manager.ManagerService;
import com.mycompany.myapp.domain.manager.dto.ManagerDTO;
import com.mycompany.myapp.domain.user.dto.ApplicationUserDTO;
import com.mycompany.myapp.domain.user.dto.CreateUserDTO;
import com.mycompany.myapp.domain.user.mapper.UserMapper;
import com.mycompany.myapp.infrastructure.repository.jpa.AuthorityRepository;
import com.mycompany.myapp.infrastructure.repository.jpa.UserRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.domain.user.dto.AdminUserDTO;
import com.mycompany.myapp.domain.user.dto.UserDTO;

import java.awt.*;
import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;
import tech.jhipster.web.util.HeaderUtil;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    private final ApplicationUserService applicationUserService;

    private final CustomerService customerService;

    private final DriverService driverService;

    private final ManagerService managerService;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        CacheManager cacheManager,
        ApplicationUserService applicationUserService,
        DriverService driverService,
        ManagerService managerService,
        CustomerService customerService,
        UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.applicationUserService = applicationUserService;
        this.driverService = driverService;
        this.managerService = managerService;
        this.customerService = customerService;
        this.userMapper = userMapper;

    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository
            .findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(new Activated(true));
                user.setActivationKey(null);
                this.clearUserCaches(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository
            .findOneByResetKey(key)
            .filter(user -> user.getResetDate().getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
            .map(user -> {
                user.setPassword(new Password(passwordEncoder.encode(newPassword)));
                user.setResetKey(null);
                user.setResetDate(null);
                this.clearUserCaches(user);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository
            .findOneByEmailIgnoreCase(mail)
            .filter(user -> user.isActivated().getActivated())
            .map(user -> {
                user.setResetKey(new ResetKey(RandomUtil.generateResetKey()));
                user.setResetDate(new ResetDate(Instant.now()));
                this.clearUserCaches(user);
                return user;
            });
    }

    public User registerUser(AdminUserDTO userDTO, String password) {
        userRepository
            .findOneByLogin(userDTO.getLogin().toLowerCase())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new UsernameAlreadyUsedException();
                }
            });
        userRepository
            .findOneByEmailIgnoreCase(userDTO.getEmail())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(new Login(userDTO.getLogin().toLowerCase()));
        // new user gets initially a generated password
        newUser.setPassword(new Password(encryptedPassword));
        newUser.setFirstName(new FirstName(userDTO.getFirstName()));
        newUser.setLastName(new LastName(userDTO.getLastName()));
        if (userDTO.getEmail() != null) {
            newUser.setEmail(new Email(userDTO.getEmail().toLowerCase()));
        }
        newUser.setImageUrl(new ImageUrl(userDTO.getImageUrl()));
        newUser.setLangKey(new LangKey(userDTO.getLangKey()));
        // new user is not active
        newUser.setActivated(new Activated(false));
        // new user gets registration key
        newUser.setActivationKey(new ActivationKey(RandomUtil.generateActivationKey()));
        Set<Authority> authorities = new HashSet<>();
        //TODO: Pass through parameter what type of User we are registering
        authorityRepository.findById(AuthoritiesConstants.DRIVER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated().getActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public User createUserWithApplicationUser(AdminUserDTO userDTO,
                                              ApplicationUserDTO applicationUserDTO,
                                              DriverDTO driverDTO,
                                              CustomerDTO customerDTO,
                                              ManagerDTO managerDTO
                                              )
    {
        User user = createUser(userDTO);

        applicationUserDTO.setUuidId(user.getId().value());
        applicationUserDTO.setInternalUser(userMapper.userToUserDTO(user));

        ApplicationUserDTO savedApplicationUserDTO = applicationUserService.save(applicationUserDTO);
        if( driverDTO != null &&
            userDTO.getAuthorities() != null &&
            userDTO.getAuthorities().contains(AuthoritiesConstants.DRIVER)
        ){
            driverDTO.setApplicationUser(savedApplicationUserDTO);
            driverService.save(driverDTO);
        }
        if( customerDTO != null &&
            userDTO.getAuthorities() != null &&
            userDTO.getAuthorities().contains(AuthoritiesConstants.CUSTOMER)
        ){
            customerDTO.setApplicationUser(savedApplicationUserDTO);
            customerService.save(customerDTO);
        }
        if( managerDTO != null &&
            userDTO.getAuthorities() != null &&
            userDTO.getAuthorities().contains(AuthoritiesConstants.MANAGER)
        ){
            managerDTO.setApplicationUser(savedApplicationUserDTO);
            managerService.save(managerDTO);
        }

        return user;

    }

    private User newUser(AdminUserDTO userDTO){
        Set<Authority> authorities = new HashSet<>();
        if (userDTO.getAuthorities() != null) {
            authorities = userDTO
                .getAuthorities()
                .stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        }
        return new User(
            new Login(userDTO.getLogin().toLowerCase()),
            new FirstName(userDTO.getFirstName()),
            new LastName(userDTO.getLastName()),
            userDTO.getEmail() != null ? new Email(userDTO.getEmail().toLowerCase()) : null,
            new ImageUrl(userDTO.getImageUrl()),
            userDTO.getLangKey() == null ? new LangKey(Constants.DEFAULT_LANGUAGE) : new LangKey(userDTO.getLangKey()),
            new Password(passwordEncoder.encode(RandomUtil.generatePassword())),
            new ResetKey(RandomUtil.generateResetKey()),
            new ResetDate(Instant.now()),
            new Activated(true),
            authorities
        );
    }

    public User createUser(AdminUserDTO userDTO) {
        User user = newUser(userDTO);
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO) {
        return Optional.of(userRepository.findById(new UserId(userDTO.getId())))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(new Login(userDTO.getLogin().toLowerCase()));
                user.setFirstName(new FirstName(userDTO.getFirstName()));
                user.setLastName(new LastName(userDTO.getLastName()));
                if (userDTO.getEmail() != null) {
                    user.setEmail(new Email(userDTO.getEmail().toLowerCase()));
                }
                user.setImageUrl(new ImageUrl(userDTO.getImageUrl()));
                user.setActivated(new Activated(userDTO.isActivated()));
                user.setLangKey(new LangKey(userDTO.getLangKey()));
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO
                    .getAuthorities()
                    .stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
                userRepository.save(user);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(AdminUserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository
            .findOneByLogin(login)
            .ifPresent(user -> {
                userRepository.delete(user);
                this.clearUserCaches(user);
                log.debug("Deleted User: {}", user);
            });
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(new FirstName(firstName));
                user.setLastName(new LastName(lastName));
                if (email != null) {
                    user.setEmail(new Email(email.toLowerCase()));
                }
                user.setLangKey(new LangKey(langKey));
                user.setImageUrl(new ImageUrl(imageUrl));
                userRepository.save(user);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
            });
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                Password currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword.getPassword())) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(new Password(encryptedPassword));
                this.clearUserCaches(user);
                log.debug("Changed password for User: {}", user);
            });
    }

    public User verifyAuthorizations(CreateUserDTO createUserDTO){
        AdminUserDTO userDTO = createUserDTO.getAdminUserDTO();
        ApplicationUserDTO applicationUserDTO = createUserDTO.getApplicationUserDTO();
        DriverDTO driverDTO = createUserDTO.getDriverDTO();
        CustomerDTO customerDTO = createUserDTO.getCustomerDTO();
        ManagerDTO managerDTO = createUserDTO.getManagerDTO();


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
        } else if (userDTO.getAuthorities().contains(AuthoritiesConstants.DRIVER) && driverDTO == null) {
            throw new BadRequestAlertException("A driver must contain its information", "userManagement", "nodriverinfo");
        } else if(userDTO.getAuthorities().contains(AuthoritiesConstants.MANAGER) && managerDTO == null) {
            throw new BadRequestAlertException("A manager must contain its information", "userManagement", "nomanagerinfo");
        } else if(userDTO.getAuthorities().contains(AuthoritiesConstants.CUSTOMER) && customerDTO == null){
            throw new BadRequestAlertException("A customer must contain its information", "userManagement", "nocustomerinfo");
        } else if (findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new com.mycompany.myapp.application.controller.errors.EmailAlreadyUsedException();
        } else {
            //User newUser = userService.createUser(userDTO);
            return createUserWithApplicationUser(userDTO,applicationUserDTO,driverDTO,customerDTO, managerDTO);
        }
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                log.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                this.clearUserCaches(user);
            });
    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).toList();
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }

    public Optional<User> findOneByEmailIgnoreCase(String email) {
        return userRepository.findOneByEmailIgnoreCase(email);
    }

    public Optional<User> findOneByLogin(String userLogin) {
        return userRepository.findOneByLogin(userLogin);
    }
}

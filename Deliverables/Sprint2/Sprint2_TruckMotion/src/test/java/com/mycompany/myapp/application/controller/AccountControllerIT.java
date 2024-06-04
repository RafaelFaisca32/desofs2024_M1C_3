package com.mycompany.myapp.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.user.*;
import com.mycompany.myapp.infrastructure.repository.jpa.AuthorityRepository;
import com.mycompany.myapp.infrastructure.repository.jpa.UserRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.domain.user.dto.AdminUserDTO;
import com.mycompany.myapp.domain.user.dto.PasswordChangeDTO;
import com.mycompany.myapp.application.controller.vm.KeyAndPasswordVM;
import com.mycompany.myapp.application.controller.vm.ManagedUserVM;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

/**
 * Integration tests for the {@link AccountController} REST controller.
 */
@AutoConfigureMockMvc
@IntegrationTest
class AccountControllerIT {

    static final String TEST_USER_LOGIN = "test";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc restAccountMockMvc;

    @Test
    @WithUnauthenticatedMockUser
    void testNonAuthenticatedUser() throws Exception {
        restAccountMockMvc
            .perform(get("/api/authenticate").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN)
    void testAuthenticatedUser() throws Exception {
        restAccountMockMvc
            .perform(get("/api/authenticate").with(request -> request).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(TEST_USER_LOGIN));
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN)
    void testGetExistingAccount() throws Exception {
        Set<String> authorities = new HashSet<>();
        authorities.add(AuthoritiesConstants.ADMIN);

        AdminUserDTO user = new AdminUserDTO();
        user.setLogin(TEST_USER_LOGIN);
        user.setFirstName("john");
        user.setLastName("doe");
        user.setEmail("john.doe@jhipster.com");
        user.setImageUrl("http://placehold.it/50x50");
        user.setLangKey("en");
        user.setAuthorities(authorities);
        userService.createUser(user);

        restAccountMockMvc
            .perform(get("/api/account").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.login").value(TEST_USER_LOGIN))
            .andExpect(jsonPath("$.firstName").value("john"))
            .andExpect(jsonPath("$.lastName").value("doe"))
            .andExpect(jsonPath("$.email").value("john.doe@jhipster.com"))
            .andExpect(jsonPath("$.imageUrl").value("http://placehold.it/50x50"))
            .andExpect(jsonPath("$.langKey").value("en"))
            .andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.ADMIN));
    }

    @Test
    void testGetUnknownAccount() throws Exception {
        restAccountMockMvc.perform(get("/api/account").accept(MediaType.APPLICATION_PROBLEM_JSON)).andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void testRegisterValid() throws Exception {
        ManagedUserVM validUser = new ManagedUserVM();
        validUser.setLogin("test-register-valid");
        validUser.setPassword("password");
        validUser.setFirstName("Alice");
        validUser.setLastName("Test");
        validUser.setEmail("test-register-valid@example.com");
        validUser.setImageUrl("http://placehold.it/50x50");
        validUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        validUser.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));
        assertThat(userRepository.findOneByLogin("test-register-valid")).isEmpty();

        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(validUser)))
            .andExpect(status().isCreated());

        assertThat(userRepository.findOneByLogin("test-register-valid")).isPresent();
    }

    @Test
    @Transactional
    void testRegisterInvalidLogin() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM();
        invalidUser.setLogin("funky-log(n"); // <-- invalid
        invalidUser.setPassword("password");
        invalidUser.setFirstName("Funky");
        invalidUser.setLastName("One");
        invalidUser.setEmail("funky@example.com");
        invalidUser.setActivated(true);
        invalidUser.setImageUrl("http://placehold.it/50x50");
        invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmailIgnoreCase("funky@example.com");
        assertThat(user).isEmpty();
    }

    static Stream<ManagedUserVM> invalidUsers() {
        return Stream.of(
            createInvalidUser("bob", "password", "Bob", "Green", "invalid", true), // <-- invalid
            createInvalidUser("bob", "123", "Bob", "Green", "bob@example.com", true), // password with only 3 digits
            createInvalidUser("bob", null, "Bob", "Green", "bob@example.com", true) // invalid null password
        );
    }

    @ParameterizedTest
    @MethodSource("invalidUsers")
    @Transactional
    void testRegisterInvalidUsers(ManagedUserVM invalidUser) throws Exception {
        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByLogin("bob");
        assertThat(user).isEmpty();
    }

    private static ManagedUserVM createInvalidUser(
        String login,
        String password,
        String firstName,
        String lastName,
        String email,
        boolean activated
    ) {
        ManagedUserVM invalidUser = new ManagedUserVM();
        invalidUser.setLogin(login);
        invalidUser.setPassword(password);
        invalidUser.setFirstName(firstName);
        invalidUser.setLastName(lastName);
        invalidUser.setEmail(email);
        invalidUser.setActivated(activated);
        invalidUser.setImageUrl("http://placehold.it/50x50");
        invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));
        return invalidUser;
    }

    @Test
    @Transactional
    void testRegisterDuplicateLogin() throws Exception {
        // First registration
        ManagedUserVM firstUser = new ManagedUserVM();
        firstUser.setLogin("alice");
        firstUser.setPassword("password");
        firstUser.setFirstName("Alice");
        firstUser.setLastName("Something");
        firstUser.setEmail("alice@example.com");
        firstUser.setImageUrl("http://placehold.it/50x50");
        firstUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        firstUser.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        // Duplicate login, different email
        ManagedUserVM secondUser = new ManagedUserVM();
        secondUser.setLogin(firstUser.getLogin());
        secondUser.setPassword(firstUser.getPassword());
        secondUser.setFirstName(firstUser.getFirstName());
        secondUser.setLastName(firstUser.getLastName());
        secondUser.setEmail("alice2@example.com");
        secondUser.setImageUrl(firstUser.getImageUrl());
        secondUser.setLangKey(firstUser.getLangKey());
        secondUser.setCreatedBy(firstUser.getCreatedBy());
        secondUser.setCreatedDate(firstUser.getCreatedDate());
        secondUser.setLastModifiedBy(firstUser.getLastModifiedBy());
        secondUser.setLastModifiedDate(firstUser.getLastModifiedDate());
        secondUser.setAuthorities(new HashSet<>(firstUser.getAuthorities()));

        // First user
        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(firstUser)))
            .andExpect(status().isCreated());

        // Second (non activated) user
        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secondUser)))
            .andExpect(status().isCreated());

        Optional<User> testUser = userRepository.findOneByEmailIgnoreCase("alice2@example.com");
        assertThat(testUser).isPresent();
        testUser.orElseThrow().setActivated(new Activated(true));
        userRepository.save(testUser.orElseThrow());

        // Second (already activated) user
        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secondUser)))
            .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void testRegisterDuplicateEmail() throws Exception {
        // First user
        ManagedUserVM firstUser = new ManagedUserVM();
        firstUser.setLogin("test-register-duplicate-email");
        firstUser.setPassword("password");
        firstUser.setFirstName("Alice");
        firstUser.setLastName("Test");
        firstUser.setEmail("test-register-duplicate-email@example.com");
        firstUser.setImageUrl("http://placehold.it/50x50");
        firstUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        firstUser.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        // Register first user
        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(firstUser)))
            .andExpect(status().isCreated());

        Optional<User> testUser1 = userRepository.findOneByLogin("test-register-duplicate-email");
        assertThat(testUser1).isPresent();

        // Duplicate email, different login
        ManagedUserVM secondUser = new ManagedUserVM();
        secondUser.setLogin("test-register-duplicate-email-2");
        secondUser.setPassword(firstUser.getPassword());
        secondUser.setFirstName(firstUser.getFirstName());
        secondUser.setLastName(firstUser.getLastName());
        secondUser.setEmail(firstUser.getEmail());
        secondUser.setImageUrl(firstUser.getImageUrl());
        secondUser.setLangKey(firstUser.getLangKey());
        secondUser.setAuthorities(new HashSet<>(firstUser.getAuthorities()));

        // Register second (non activated) user
        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secondUser)))
            .andExpect(status().isCreated());

        Optional<User> testUser2 = userRepository.findOneByLogin("test-register-duplicate-email");
        assertThat(testUser2).isEmpty();

        Optional<User> testUser3 = userRepository.findOneByLogin("test-register-duplicate-email-2");
        assertThat(testUser3).isPresent();

        // Duplicate email - with uppercase email address
        ManagedUserVM userWithUpperCaseEmail = new ManagedUserVM();
        userWithUpperCaseEmail.setId(firstUser.getId());
        userWithUpperCaseEmail.setLogin("test-register-duplicate-email-3");
        userWithUpperCaseEmail.setPassword(firstUser.getPassword());
        userWithUpperCaseEmail.setFirstName(firstUser.getFirstName());
        userWithUpperCaseEmail.setLastName(firstUser.getLastName());
        userWithUpperCaseEmail.setEmail("TEST-register-duplicate-email@example.com");
        userWithUpperCaseEmail.setImageUrl(firstUser.getImageUrl());
        userWithUpperCaseEmail.setLangKey(firstUser.getLangKey());
        userWithUpperCaseEmail.setAuthorities(new HashSet<>(firstUser.getAuthorities()));

        // Register third (not activated) user
        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userWithUpperCaseEmail)))
            .andExpect(status().isCreated());

        Optional<User> testUser4 = userRepository.findOneByLogin("test-register-duplicate-email-3");
        assertThat(testUser4).isPresent();
        assertThat(testUser4.orElseThrow().getEmail()).isEqualTo("test-register-duplicate-email@example.com");

        testUser4.orElseThrow().setActivated(new Activated(true));
        userService.updateUser((new AdminUserDTO(testUser4.orElseThrow())));

        // Register 4th (already activated) user
        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secondUser)))
            .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    void testRegisterAdminIsIgnored() throws Exception {
        ManagedUserVM validUser = new ManagedUserVM();
        validUser.setLogin("badguy");
        validUser.setPassword("password");
        validUser.setFirstName("Bad");
        validUser.setLastName("Guy");
        validUser.setEmail("badguy@example.com");
        validUser.setActivated(true);
        validUser.setImageUrl("http://placehold.it/50x50");
        validUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        validUser.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(validUser)))
            .andExpect(status().isCreated());

        Optional<User> userDup = userRepository.findOneWithAuthoritiesByLogin("badguy");
        assertThat(userDup).isPresent();
        assertThat(userDup.orElseThrow().getAuthorities())
            .hasSize(1)
            .containsExactly(authorityRepository.findById(AuthoritiesConstants.DRIVER).orElseThrow());
    }

    @Test
    @Transactional
    void testActivateAccount() throws Exception {
        final String activationKey = "some activation key";
        User user = new User();
        user.setLogin(new Login("activate-account"));
        user.setEmail(new Email("activate-account@example.com"));
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setActivated(new Activated(false));
        user.setActivationKey(new ActivationKey(activationKey));

        userRepository.saveAndFlush(user);

        restAccountMockMvc.perform(get("/api/activate?key={activationKey}", activationKey)).andExpect(status().isOk());

        user = userRepository.findOneByLogin(user.getLogin().getLogin()).orElse(null);
        assertThat(user.isActivated().getActivated()).isTrue();
    }

    @Test
    @Transactional
    void testActivateAccountWithWrongKey() throws Exception {
        restAccountMockMvc.perform(get("/api/activate?key=wrongActivationKey")).andExpect(status().isInternalServerError());
    }

    @Test
    @Transactional
    @WithMockUser("save-account")
    void testSaveAccount() throws Exception {
        User user = new User();
        user.setLogin(new Login("save-account"));
        user.setEmail(new Email("save-account@example.com"));
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setActivated(new Activated(true));
        userRepository.saveAndFlush(user);

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setLogin("not-used");
        userDTO.setFirstName("firstname");
        userDTO.setLastName("lastname");
        userDTO.setEmail("save-account@example.com");
        userDTO.setActivated(false);
        userDTO.setImageUrl("http://placehold.it/50x50");
        userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        restAccountMockMvc
            .perform(post("/api/account").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userDTO)))
            .andExpect(status().isOk());

        User updatedUser = userRepository.findOneWithAuthoritiesByLogin(user.getLogin().getLogin()).orElse(null);
        assertThat(updatedUser.getFirstName().getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(updatedUser.getLastName().getLastName()).isEqualTo(userDTO.getLastName());
        assertThat(updatedUser.getEmail().getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(updatedUser.getLangKey().getLangKey()).isEqualTo(userDTO.getLangKey());
        assertThat(updatedUser.getPassword().getPassword()).isEqualTo(user.getPassword());
        assertThat(updatedUser.getImageUrl().getImageUrl()).isEqualTo(userDTO.getImageUrl());
        assertThat(updatedUser.isActivated().getActivated()).isTrue();
        assertThat(updatedUser.getAuthorities()).isEmpty();
    }

    @Test
    @Transactional
    @WithMockUser("save-invalid-email")
    void testSaveInvalidEmail() throws Exception {
        User user = new User();
        user.setLogin(new Login("save-invalid-email"));
        user.setEmail(new Email("save-invalid-email@example.com"));
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setActivated(new Activated(true));

        userRepository.saveAndFlush(user);

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setLogin("not-used");
        userDTO.setFirstName("firstname");
        userDTO.setLastName("lastname");
        userDTO.setEmail("invalid email");
        userDTO.setActivated(false);
        userDTO.setImageUrl("http://placehold.it/50x50");
        userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        restAccountMockMvc
            .perform(post("/api/account").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userDTO)))
            .andExpect(status().isBadRequest());

        assertThat(userRepository.findOneByEmailIgnoreCase("invalid email")).isNotPresent();
    }

    @Test
    @Transactional
    @WithMockUser("save-existing-email")
    void testSaveExistingEmail() throws Exception {
        User user = new User();
        user.setLogin(new Login("save-existing-email"));
        user.setEmail(new Email("save-existing-email@example.com"));
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setActivated(new Activated(true));
        userRepository.saveAndFlush(user);

        User anotherUser = new User();
        anotherUser.setLogin(new Login("save-existing-email2"));
        anotherUser.setEmail(new Email("save-existing-email2@example.com"));
        anotherUser.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        anotherUser.setActivated(new Activated(true));

        userRepository.saveAndFlush(anotherUser);

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setLogin("not-used");
        userDTO.setFirstName("firstname");
        userDTO.setLastName("lastname");
        userDTO.setEmail("save-existing-email2@example.com");
        userDTO.setActivated(false);
        userDTO.setImageUrl("http://placehold.it/50x50");
        userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        restAccountMockMvc
            .perform(post("/api/account").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userDTO)))
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByLogin("save-existing-email").orElse(null);
        assertThat(updatedUser.getEmail().getEmail()).isEqualTo("save-existing-email@example.com");
    }

    @Test
    @Transactional
    @WithMockUser("save-existing-email-and-login")
    void testSaveExistingEmailAndLogin() throws Exception {
        User user = new User();
        user.setLogin(new Login("save-existing-email-and-login"));
        user.setEmail(new Email("save-existing-email-and-login@example.com"));
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setActivated(new Activated(true));
        userRepository.saveAndFlush(user);

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setLogin("not-used");
        userDTO.setFirstName("firstname");
        userDTO.setLastName("lastname");
        userDTO.setEmail("save-existing-email-and-login@example.com");
        userDTO.setActivated(false);
        userDTO.setImageUrl("http://placehold.it/50x50");
        userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        restAccountMockMvc
            .perform(post("/api/account").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userDTO)))
            .andExpect(status().isOk());

        User updatedUser = userRepository.findOneByLogin("save-existing-email-and-login").orElse(null);
        assertThat(updatedUser.getEmail().getEmail()).isEqualTo("save-existing-email-and-login@example.com");
    }

    @Test
    @Transactional
    @WithMockUser("change-password-wrong-existing-password")
    void testChangePasswordWrongExistingPassword() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(new Password(passwordEncoder.encode(currentPassword)));
        user.setLogin(new Login("change-password-wrong-existing-password"));
        user.setEmail(new Email("change-password-wrong-existing-password@example.com"));
        userRepository.saveAndFlush(user);

        restAccountMockMvc
            .perform(
                post("/api/account/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(new PasswordChangeDTO("1" + currentPassword, "new password")))
            )
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByLogin("change-password-wrong-existing-password").orElse(null);
        assertThat(passwordEncoder.matches("new password", updatedUser.getPassword().getPassword())).isFalse();
        assertThat(passwordEncoder.matches(currentPassword, updatedUser.getPassword().getPassword())).isTrue();
    }

    @Test
    @Transactional
    @WithMockUser("change-password")
    void testChangePassword() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(new Password(passwordEncoder.encode(currentPassword)));
        user.setLogin(new Login("change-password"));
        user.setEmail(new Email("change-password@example.com"));
        userRepository.saveAndFlush(user);

        restAccountMockMvc
            .perform(
                post("/api/account/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(new PasswordChangeDTO(currentPassword, "new password")))
            )
            .andExpect(status().isOk());

        User updatedUser = userRepository.findOneByLogin("change-password").orElse(null);
        assertThat(passwordEncoder.matches("new password", updatedUser.getPassword().getPassword())).isTrue();
    }

    @Test
    @Transactional
    @WithMockUser("change-password-too-small")
    void testChangePasswordTooSmall() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(new Password(passwordEncoder.encode(currentPassword)));
        user.setLogin(new Login("change-password-too-small"));
        user.setEmail(new Email("change-password-too-small@example.com"));
        userRepository.saveAndFlush(user);

        String newPassword = RandomStringUtils.random(ManagedUserVM.PASSWORD_MIN_LENGTH - 1);

        restAccountMockMvc
            .perform(
                post("/api/account/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(new PasswordChangeDTO(currentPassword, newPassword)))
            )
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByLogin("change-password-too-small").orElse(null);
        assertThat(updatedUser.getPassword().getPassword()).isEqualTo(user.getPassword().getPassword());
    }

    @Test
    @Transactional
    @WithMockUser("change-password-too-long")
    void testChangePasswordTooLong() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(new Password(passwordEncoder.encode(currentPassword)));
        user.setLogin(new Login("change-password-too-long"));
        user.setEmail(new Email("change-password-too-long@example.com"));
        userRepository.saveAndFlush(user);

        String newPassword = RandomStringUtils.random(ManagedUserVM.PASSWORD_MAX_LENGTH + 1);

        restAccountMockMvc
            .perform(
                post("/api/account/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(new PasswordChangeDTO(currentPassword, newPassword)))
            )
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByLogin("change-password-too-long").orElse(null);
        assertThat(updatedUser.getPassword().getPassword()).isEqualTo(user.getPassword().getPassword());
    }

    @Test
    @Transactional
    @WithMockUser("change-password-empty")
    void testChangePasswordEmpty() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(new Password(passwordEncoder.encode(currentPassword)));
        user.setLogin(new Login("change-password-empty"));
        user.setEmail(new Email("change-password-empty@example.com"));
        userRepository.saveAndFlush(user);

        restAccountMockMvc
            .perform(
                post("/api/account/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(new PasswordChangeDTO(currentPassword, "")))
            )
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByLogin("change-password-empty").orElse(null);
        assertThat(updatedUser.getPassword().getPassword()).isEqualTo(user.getPassword().getPassword());
    }

    @Test
    @Transactional
    void testRequestPasswordReset() throws Exception {
        User user = new User();
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setActivated(new Activated(true));
        user.setLogin(new Login("password-reset"));
        user.setEmail(new Email("password-reset@example.com"));
        user.setLangKey(new LangKey("en"));
        userRepository.saveAndFlush(user);

        restAccountMockMvc
            .perform(post("/api/account/reset-password/init").content("password-reset@example.com"))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void testRequestPasswordResetUpperCaseEmail() throws Exception {
        User user = new User();
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setActivated(new Activated(true));
        user.setLogin(new Login("password-reset-upper-case"));
        user.setEmail(new Email("password-reset-upper-case@example.com"));
        user.setLangKey(new LangKey("en"));
        userRepository.saveAndFlush(user);

        restAccountMockMvc
            .perform(post("/api/account/reset-password/init").content("password-reset-upper-case@EXAMPLE.COM"))
            .andExpect(status().isOk());
    }

    @Test
    void testRequestPasswordResetWrongEmail() throws Exception {
        restAccountMockMvc
            .perform(post("/api/account/reset-password/init").content("password-reset-wrong-email@example.com"))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void testFinishPasswordReset() throws Exception {
        User user = new User();
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setLogin(new Login("finish-password-reset"));
        user.setEmail(new Email("finish-password-reset@example.com"));
        user.setResetDate(new ResetDate(Instant.now().plusSeconds(60)));
        user.setResetKey(new ResetKey("reset key"));
        userRepository.saveAndFlush(user);

        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey(user.getResetKey().getResetKey());
        keyAndPassword.setNewPassword("new password");

        restAccountMockMvc
            .perform(
                post("/api/account/reset-password/finish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(keyAndPassword))
            )
            .andExpect(status().isOk());

        User updatedUser = userRepository.findOneByLogin(user.getLogin().getLogin()).orElse(null);
        assertThat(passwordEncoder.matches(keyAndPassword.getNewPassword(), updatedUser.getPassword().getPassword())).isTrue();
    }

    @Test
    @Transactional
    void testFinishPasswordResetTooSmall() throws Exception {
        User user = new User();
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setLogin(new Login("finish-password-reset-too-small"));
        user.setEmail(new Email("finish-password-reset-too-small@example.com"));
        user.setResetDate(new ResetDate(Instant.now().plusSeconds(60)));
        user.setResetKey(new ResetKey("reset key too small"));
        userRepository.saveAndFlush(user);

        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey(user.getResetKey().getResetKey());
        keyAndPassword.setNewPassword("foo");

        restAccountMockMvc
            .perform(
                post("/api/account/reset-password/finish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(keyAndPassword))
            )
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByLogin(user.getLogin().getLogin()).orElse(null);
        assertThat(passwordEncoder.matches(keyAndPassword.getNewPassword(), updatedUser.getPassword().getPassword())).isFalse();
    }

    @Test
    @Transactional
    void testFinishPasswordResetWrongKey() throws Exception {
        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey("wrong reset key");
        keyAndPassword.setNewPassword("new password");

        restAccountMockMvc
            .perform(
                post("/api/account/reset-password/finish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(keyAndPassword))
            )
            .andExpect(status().isInternalServerError());
    }
}

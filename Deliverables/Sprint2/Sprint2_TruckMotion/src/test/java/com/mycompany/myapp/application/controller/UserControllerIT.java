package com.mycompany.myapp.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.application.controller.UserController;
import com.mycompany.myapp.domain.user.*;
import com.mycompany.myapp.infrastructure.repository.jpa.UserRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.domain.user.dto.AdminUserDTO;
import com.mycompany.myapp.domain.user.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserController} REST controller.
 */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
class UserControllerIT {

    private static final String DEFAULT_LOGIN = "johndoe";
    private static final String UPDATED_LOGIN = "jhipster";

    private static final Long DEFAULT_ID = 1L;

    private static final String DEFAULT_PASSWORD = "passjohndoe";
    private static final String UPDATED_PASSWORD = "passjhipster";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";
    private static final String UPDATED_EMAIL = "jhipster@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";
    private static final String UPDATED_FIRSTNAME = "jhipsterFirstName";

    private static final String DEFAULT_LASTNAME = "doe";
    private static final String UPDATED_LASTNAME = "jhipsterLastName";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";
    private static final String UPDATED_IMAGEURL = "http://placehold.it/40x40";

    private static final String DEFAULT_LANGKEY = "en";
    private static final String UPDATED_LANGKEY = "fr";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MockMvc restUserMockMvc;

    private User user;

    @BeforeEach
    public void setup() {
        cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).clear();
        cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).clear();
    }

    /**
     * Create a User.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which has a required relationship to the User entity.
     */
    public static User createEntity(EntityManager em) {
        User user = new User();
        user.setLogin(new Login(DEFAULT_LOGIN + RandomStringUtils.randomAlphabetic(5)));
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setActivated(new Activated(true));
        user.setEmail(new Email(RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL));
        user.setFirstName(new FirstName(DEFAULT_FIRSTNAME));
        user.setLastName(new LastName(DEFAULT_LASTNAME));
        user.setImageUrl(new ImageUrl(DEFAULT_IMAGEURL));
        user.setLangKey(new LangKey(DEFAULT_LANGKEY));
        return user;
    }

    /**
     * Setups the database with one user.
     */
    public static User initTestUser(UserRepository userRepository, EntityManager em) {
        userRepository.deleteAll();
        User user = createEntity(em);
        user.setLogin(new Login(DEFAULT_LOGIN));
        user.setEmail(new Email(DEFAULT_EMAIL));
        return user;
    }

    @BeforeEach
    public void initTest() {
        user = initTestUser(userRepository, em);
    }

    @Test
    @Transactional
    void createUser() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        // Create the User
        AdminUserDTO user = new AdminUserDTO();
        user.setLogin(DEFAULT_LOGIN);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setEmail(DEFAULT_EMAIL);
        user.setActivated(true);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
        user.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        restUserMockMvc
            .perform(post("/api/admin/users").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(user)))
            .andExpect(status().isCreated());

        // Validate the User in the database
        assertPersistedUsers(users -> {
            assertThat(users).hasSize(databaseSizeBeforeCreate + 1);
            User testUser = users.get(users.size() - 1);
            assertThat(testUser.getLogin().getLogin()).isEqualTo(DEFAULT_LOGIN);
            assertThat(testUser.getFirstName().getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
            assertThat(testUser.getLastName().getLastName()).isEqualTo(DEFAULT_LASTNAME);
            assertThat(testUser.getEmail().getEmail()).isEqualTo(DEFAULT_EMAIL);
            assertThat(testUser.getImageUrl().getImageUrl()).isEqualTo(DEFAULT_IMAGEURL);
            assertThat(testUser.getLangKey().getLangKey()).isEqualTo(DEFAULT_LANGKEY);
        });
    }

    @Test
    @Transactional
    void createUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        AdminUserDTO user = new AdminUserDTO();
        user.setId(new UUID(0L, DEFAULT_ID));
        user.setLogin(DEFAULT_LOGIN);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setEmail(DEFAULT_EMAIL);
        user.setActivated(true);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
        user.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMockMvc
            .perform(post("/api/admin/users").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(user)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        assertPersistedUsers(users -> assertThat(users).hasSize(databaseSizeBeforeCreate));
    }

    @Test
    @Transactional
    void createUserWithExistingLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        AdminUserDTO user = new AdminUserDTO();
        user.setLogin(DEFAULT_LOGIN); // this login should already be used
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setEmail("anothermail@localhost");
        user.setActivated(true);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
        user.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        // Create the User
        restUserMockMvc
            .perform(post("/api/admin/users").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(user)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        assertPersistedUsers(users -> assertThat(users).hasSize(databaseSizeBeforeCreate));
    }

    @Test
    @Transactional
    void createUserWithExistingEmail() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        AdminUserDTO user = new AdminUserDTO();
        user.setLogin("anotherlogin");
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setEmail(DEFAULT_EMAIL); // this email should already be used
        user.setActivated(true);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
        user.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        // Create the User
        restUserMockMvc
            .perform(post("/api/admin/users").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(user)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        assertPersistedUsers(users -> assertThat(users).hasSize(databaseSizeBeforeCreate));
    }

    @Test
    @Transactional
    void getAllUsers() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get all the users
        restUserMockMvc
            .perform(get("/api/admin/users?sort=id,desc").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGEURL)))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANGKEY)));
    }

    @Test
    @Transactional
    void getUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).get(user.getLogin())).isNull();

        // Get the user
        restUserMockMvc
            .perform(get("/api/admin/users/{login}", user.getLogin()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.login").value(user.getLogin()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGEURL))
            .andExpect(jsonPath("$.langKey").value(DEFAULT_LANGKEY));

        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).get(user.getLogin())).isNotNull();
    }

    @Test
    @Transactional
    void getNonExistingUser() throws Exception {
        restUserMockMvc.perform(get("/api/admin/users/unknown")).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();

        AdminUserDTO user = new AdminUserDTO();
        user.setId(updatedUser.getId().value());
        user.setLogin(updatedUser.getLogin().getLogin());
        user.setFirstName(UPDATED_FIRSTNAME);
        user.setLastName(UPDATED_LASTNAME);
        user.setEmail(UPDATED_EMAIL);
        user.setActivated(updatedUser.isActivated().getActivated());
        user.setImageUrl(UPDATED_IMAGEURL);
        user.setLangKey(UPDATED_LANGKEY);
        user.setCreatedBy(updatedUser.getCreatedBy());
        user.setCreatedDate(updatedUser.getCreatedDate());
        user.setLastModifiedBy(updatedUser.getLastModifiedBy());
        user.setLastModifiedDate(updatedUser.getLastModifiedDate());
        user.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        restUserMockMvc
            .perform(put("/api/admin/users").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(user)))
            .andExpect(status().isOk());

        // Validate the User in the database
        assertPersistedUsers(users -> {
            assertThat(users).hasSize(databaseSizeBeforeUpdate);
            User testUser = users.stream().filter(usr -> usr.getId().value().equals(updatedUser.getId().value())).findFirst().orElseThrow();
            assertThat(testUser.getFirstName().getFirstName()).isEqualTo(UPDATED_FIRSTNAME);
            assertThat(testUser.getLastName().getLastName()).isEqualTo(UPDATED_LASTNAME);
            assertThat(testUser.getEmail().getEmail()).isEqualTo(UPDATED_EMAIL);
            assertThat(testUser.getImageUrl().getImageUrl()).isEqualTo(UPDATED_IMAGEURL);
            assertThat(testUser.getLangKey().getLangKey()).isEqualTo(UPDATED_LANGKEY);
        });
    }

    @Test
    @Transactional
    void updateUserLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();

        AdminUserDTO user = new AdminUserDTO();
        user.setId(updatedUser.getId().value());
        user.setLogin(UPDATED_LOGIN);
        user.setFirstName(UPDATED_FIRSTNAME);
        user.setLastName(UPDATED_LASTNAME);
        user.setEmail(UPDATED_EMAIL);
        user.setActivated(updatedUser.isActivated().getActivated());
        user.setImageUrl(UPDATED_IMAGEURL);
        user.setLangKey(UPDATED_LANGKEY);
        user.setCreatedBy(updatedUser.getCreatedBy());
        user.setCreatedDate(updatedUser.getCreatedDate());
        user.setLastModifiedBy(updatedUser.getLastModifiedBy());
        user.setLastModifiedDate(updatedUser.getLastModifiedDate());
        user.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        restUserMockMvc
            .perform(put("/api/admin/users").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(user)))
            .andExpect(status().isOk());

        // Validate the User in the database
        assertPersistedUsers(users -> {
            assertThat(users).hasSize(databaseSizeBeforeUpdate);
            User testUser = users.stream().filter(usr -> usr.getId().value().equals(updatedUser.getId().value())).findFirst().orElseThrow();
            assertThat(testUser.getLogin().getLogin()).isEqualTo(UPDATED_LOGIN);
            assertThat(testUser.getFirstName().getFirstName()).isEqualTo(UPDATED_FIRSTNAME);
            assertThat(testUser.getLastName().getLastName()).isEqualTo(UPDATED_LASTNAME);
            assertThat(testUser.getEmail().getEmail()).isEqualTo(UPDATED_EMAIL);
            assertThat(testUser.getImageUrl().getImageUrl()).isEqualTo(UPDATED_IMAGEURL);
            assertThat(testUser.getLangKey().getLangKey()).isEqualTo(UPDATED_LANGKEY);
        });
    }

    @Test
    @Transactional
    void updateUserExistingEmail() throws Exception {
        // Initialize the database with 2 users
        userRepository.saveAndFlush(user);

        User anotherUser = new User();
        anotherUser.setLogin(new Login("jhipster"));
        anotherUser.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        anotherUser.setActivated(new Activated(true));
        anotherUser.setEmail(new Email("jhipster@localhost"));
        anotherUser.setFirstName(new FirstName("java"));
        anotherUser.setLastName(new LastName("hipster"));
        anotherUser.setImageUrl(new ImageUrl(""));
        anotherUser.setLangKey(new LangKey("en"));
        userRepository.saveAndFlush(anotherUser);

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();

        AdminUserDTO user = new AdminUserDTO();
        user.setId(updatedUser.getId().value());
        user.setLogin(updatedUser.getLogin().getLogin());
        user.setFirstName(updatedUser.getFirstName().getFirstName());
        user.setLastName(updatedUser.getLastName().getLastName());
        user.setEmail("jhipster@localhost"); // this email should already be used by anotherUser
        user.setActivated(updatedUser.isActivated().getActivated());
        user.setImageUrl(updatedUser.getImageUrl().getImageUrl());
        user.setLangKey(updatedUser.getLangKey().getLangKey());
        user.setCreatedBy(updatedUser.getCreatedBy());
        user.setCreatedDate(updatedUser.getCreatedDate());
        user.setLastModifiedBy(updatedUser.getLastModifiedBy());
        user.setLastModifiedDate(updatedUser.getLastModifiedDate());
        user.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        restUserMockMvc
            .perform(put("/api/admin/users").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(user)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void updateUserExistingLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        User anotherUser = new User();
        anotherUser.setLogin(new Login("jhipster"));
        anotherUser.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        anotherUser.setActivated(new Activated(true));
        anotherUser.setEmail(new Email("jhipster@localhost"));
        anotherUser.setFirstName(new FirstName("java"));
        anotherUser.setLastName(new LastName("hipster"));
        anotherUser.setImageUrl(new ImageUrl(""));
        anotherUser.setLangKey(new LangKey("en"));
        userRepository.saveAndFlush(anotherUser);

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();

        AdminUserDTO user = new AdminUserDTO();
        user.setId(updatedUser.getId().value());
        user.setLogin("jhipster"); // this login should already be used by anotherUser
        user.setFirstName(updatedUser.getFirstName().getFirstName());
        user.setLastName(updatedUser.getLastName().getLastName());
        user.setEmail(updatedUser.getEmail().getEmail());
        user.setActivated(updatedUser.isActivated().getActivated());
        user.setImageUrl(updatedUser.getImageUrl().getImageUrl());
        user.setLangKey(updatedUser.getLangKey().getLangKey());
        user.setCreatedBy(updatedUser.getCreatedBy());
        user.setCreatedDate(updatedUser.getCreatedDate());
        user.setLastModifiedBy(updatedUser.getLastModifiedBy());
        user.setLastModifiedDate(updatedUser.getLastModifiedDate());
        user.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        restUserMockMvc
            .perform(put("/api/admin/users").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(user)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeDelete = userRepository.findAll().size();

        // Delete the user
        restUserMockMvc
            .perform(delete("/api/admin/users/{login}", user.getLogin()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).get(user.getLogin())).isNull();

        // Validate the database is empty
        assertPersistedUsers(users -> assertThat(users).hasSize(databaseSizeBeforeDelete - 1));
    }

    @Test
    void testUserEquals() throws Exception {
        TestUtil.equalsVerifier(User.class);
        User user1 = new User();
        user1.setId(new UserId(new UUID(0L,DEFAULT_ID)));
        User user2 = new User();
        user2.setId(user1.getId());
        assertThat(user1).isEqualTo(user2);
        user2.setId(new UserId(new UUID(0L,2L)));
        assertThat(user1).isNotEqualTo(user2);
        user1.setId(null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    void testUserDTOtoUser() {
        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setId(new UUID(0L,DEFAULT_ID));
        userDTO.setLogin(DEFAULT_LOGIN);
        userDTO.setFirstName(DEFAULT_FIRSTNAME);
        userDTO.setLastName(DEFAULT_LASTNAME);
        userDTO.setEmail(DEFAULT_EMAIL);
        userDTO.setActivated(true);
        userDTO.setImageUrl(DEFAULT_IMAGEURL);
        userDTO.setLangKey(DEFAULT_LANGKEY);
        userDTO.setCreatedBy(DEFAULT_LOGIN);
        userDTO.setLastModifiedBy(DEFAULT_LOGIN);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.DRIVER));

        User user = userMapper.userDTOToUser(userDTO);
        assertThat(user.getId().value()).isEqualTo(new UUID(0L,DEFAULT_ID));
        assertThat(user.getLogin().getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(user.getFirstName().getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(user.getLastName().getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(user.getEmail().getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(user.isActivated().getActivated()).isTrue();
        assertThat(user.getImageUrl().getImageUrl()).isEqualTo(DEFAULT_IMAGEURL);
        assertThat(user.getLangKey().getLangKey()).isEqualTo(DEFAULT_LANGKEY);
        assertThat(user.getCreatedBy()).isNull();
        assertThat(user.getCreatedDate()).isNotNull();
        assertThat(user.getLastModifiedBy()).isNull();
        assertThat(user.getLastModifiedDate()).isNotNull();
        assertThat(user.getAuthorities()).extracting("name").containsExactly(AuthoritiesConstants.DRIVER);
    }

    @Test
    void testUserToUserDTO() {
        user.setId(new UserId(new UUID(0L,DEFAULT_ID)));
        user.setCreatedBy(DEFAULT_LOGIN);
        user.setCreatedDate(Instant.now());
        user.setLastModifiedBy(DEFAULT_LOGIN);
        user.setLastModifiedDate(Instant.now());
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.DRIVER);
        authorities.add(authority);
        user.setAuthorities(authorities);

        AdminUserDTO userDTO = userMapper.userToAdminUserDTO(user);

        assertThat(userDTO.getId()).isEqualTo(new UUID(0L,DEFAULT_ID));
        assertThat(userDTO.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(userDTO.getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(userDTO.getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(userDTO.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(userDTO.isActivated()).isTrue();
        assertThat(userDTO.getImageUrl()).isEqualTo(DEFAULT_IMAGEURL);
        assertThat(userDTO.getLangKey()).isEqualTo(DEFAULT_LANGKEY);
        assertThat(userDTO.getCreatedBy()).isEqualTo(DEFAULT_LOGIN);
        assertThat(userDTO.getCreatedDate()).isEqualTo(user.getCreatedDate());
        assertThat(userDTO.getLastModifiedBy()).isEqualTo(DEFAULT_LOGIN);
        assertThat(userDTO.getLastModifiedDate()).isEqualTo(user.getLastModifiedDate());
        assertThat(userDTO.getAuthorities()).containsExactly(AuthoritiesConstants.DRIVER);
        assertThat(userDTO.toString()).isNotNull();
    }

    private void assertPersistedUsers(Consumer<List<User>> userAssertion) {
        userAssertion.accept(userRepository.findAll());
    }
}

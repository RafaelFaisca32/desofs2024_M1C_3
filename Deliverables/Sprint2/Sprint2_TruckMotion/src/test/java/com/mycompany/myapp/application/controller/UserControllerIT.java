package com.mycompany.myapp.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.user.Authority;
import com.mycompany.myapp.domain.user.User;
import com.mycompany.myapp.domain.user.UserId;
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
    private static final UUID DEFAULT_UUID = UUID.fromString("dc39723a-35e2-4f14-b682-933995d5c4bf");

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
        user.updateLogin(DEFAULT_LOGIN + RandomStringUtils.randomAlphabetic(5));
        user.updatePassword(RandomStringUtils.randomAlphanumeric(60));
        user.activate();
        user.updateEmail(RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL);
        user.updateFirstName(DEFAULT_FIRSTNAME);
        user.updateLastName(DEFAULT_LASTNAME);
        user.updateImageUrl(DEFAULT_IMAGEURL);
        user.updateLangKey(DEFAULT_LANGKEY);
        return user;
    }

    /**
     * Setups the database with one user.
     */
    public static User initTestUser(UserRepository userRepository, EntityManager em) {
        userRepository.deleteAll();
        User user = createEntity(em);
        user.updateLogin(DEFAULT_LOGIN);
        user.updateEmail(DEFAULT_EMAIL);
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
            assertThat(testUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
            assertThat(testUser.getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
            assertThat(testUser.getLastName()).isEqualTo(DEFAULT_LASTNAME);
            assertThat(testUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
            assertThat(testUser.getImageUrl()).isEqualTo(DEFAULT_IMAGEURL);
            assertThat(testUser.getLangKey()).isEqualTo(DEFAULT_LANGKEY);
        });
    }

    @Test
    @Transactional
    void createUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        AdminUserDTO user = new AdminUserDTO();
        user.setId(DEFAULT_ID);
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
        user.setId(updatedUser.getId());
        user.setLogin(updatedUser.getLogin());
        user.setFirstName(UPDATED_FIRSTNAME);
        user.setLastName(UPDATED_LASTNAME);
        user.setEmail(UPDATED_EMAIL);
        user.setActivated(updatedUser.isActivated());
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
            User testUser = users.stream().filter(usr -> usr.getId().equals(updatedUser.getId())).findFirst().orElseThrow();
            assertThat(testUser.getFirstName()).isEqualTo(UPDATED_FIRSTNAME);
            assertThat(testUser.getLastName()).isEqualTo(UPDATED_LASTNAME);
            assertThat(testUser.getEmail()).isEqualTo(UPDATED_EMAIL);
            assertThat(testUser.getImageUrl()).isEqualTo(UPDATED_IMAGEURL);
            assertThat(testUser.getLangKey()).isEqualTo(UPDATED_LANGKEY);
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
        user.setId(updatedUser.getId());
        user.setLogin(UPDATED_LOGIN);
        user.setFirstName(UPDATED_FIRSTNAME);
        user.setLastName(UPDATED_LASTNAME);
        user.setEmail(UPDATED_EMAIL);
        user.setActivated(updatedUser.isActivated());
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
            User testUser = users.stream().filter(usr -> usr.getId().equals(updatedUser.getId())).findFirst().orElseThrow();
            assertThat(testUser.getLogin()).isEqualTo(UPDATED_LOGIN);
            assertThat(testUser.getFirstName()).isEqualTo(UPDATED_FIRSTNAME);
            assertThat(testUser.getLastName()).isEqualTo(UPDATED_LASTNAME);
            assertThat(testUser.getEmail()).isEqualTo(UPDATED_EMAIL);
            assertThat(testUser.getImageUrl()).isEqualTo(UPDATED_IMAGEURL);
            assertThat(testUser.getLangKey()).isEqualTo(UPDATED_LANGKEY);
        });
    }

    @Test
    @Transactional
    void updateUserExistingEmail() throws Exception {
        // Initialize the database with 2 users
        userRepository.saveAndFlush(user);

        User anotherUser = new User();
        anotherUser.updateLogin("jhipster");
        anotherUser.updatePassword(RandomStringUtils.randomAlphanumeric(60));
        anotherUser.activate();
        anotherUser.updateEmail("jhipster@localhost");
        anotherUser.updateFirstName("java");
        anotherUser.updateLastName("hipster");
        anotherUser.updateImageUrl("");
        anotherUser.updateLangKey("en");
        userRepository.saveAndFlush(anotherUser);

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();

        AdminUserDTO user = new AdminUserDTO();
        user.setId(updatedUser.getId());
        user.setLogin(updatedUser.getLogin());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail("jhipster@localhost"); // this email should already be used by anotherUser
        user.setActivated(updatedUser.isActivated());
        user.setImageUrl(updatedUser.getImageUrl());
        user.setLangKey(updatedUser.getLangKey());
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
        anotherUser.updateLogin("jhipster");
        anotherUser.updatePassword(RandomStringUtils.randomAlphanumeric(60));
        anotherUser.activate();
        anotherUser.updateEmail("jhipster@localhost");
        anotherUser.updateFirstName("java");
        anotherUser.updateLastName("hipster");
        anotherUser.updateImageUrl("");
        anotherUser.updateLangKey("en");
        userRepository.saveAndFlush(anotherUser);

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();

        AdminUserDTO user = new AdminUserDTO();
        user.setId(updatedUser.getId());
        user.setLogin("jhipster"); // this login should already be used by anotherUser
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setActivated(updatedUser.isActivated());
        user.setImageUrl(updatedUser.getImageUrl());
        user.setLangKey(updatedUser.getLangKey());
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
        User user1 = new User(DEFAULT_ID,new UserId(DEFAULT_UUID));
        User user2 = new User(user1);
        assertThat(user1).isEqualTo(user2);
        user2 = new User(2L,new UserId() );
        assertThat(user1).isNotEqualTo(user2);
        user1 = new User(null, null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    void testUserDTOtoUser() {
        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setId(DEFAULT_ID);
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
        assertThat(user.getId()).isEqualTo(DEFAULT_ID);
        assertThat(user.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(user.getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(user.getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(user.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(user.isActivated()).isTrue();
        assertThat(user.getImageUrl()).isEqualTo(DEFAULT_IMAGEURL);
        assertThat(user.getLangKey()).isEqualTo(DEFAULT_LANGKEY);
        assertThat(user.getCreatedBy()).isNull();
        assertThat(user.getCreatedDate()).isNotNull();
        assertThat(user.getLastModifiedBy()).isNull();
        assertThat(user.getLastModifiedDate()).isNotNull();
        assertThat(user.getAuthorities()).extracting("name").containsExactly(AuthoritiesConstants.DRIVER);
    }

    @Test
    void testUserToUserDTO() {

        user = new User(DEFAULT_ID,new UserId(DEFAULT_UUID));
        user.updateCreatedBy(DEFAULT_LOGIN);
        user.updateCreatedDate(Instant.now());
        user.updateLastModifiedBy(DEFAULT_LOGIN);
        user.updateLastModifiedDate(Instant.now());
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.updateName(AuthoritiesConstants.DRIVER);
        authorities.add(authority);
        user.updateAuthorities(authorities);

        AdminUserDTO userDTO = userMapper.userToAdminUserDTO(user);

        assertThat(userDTO.getId()).isEqualTo(DEFAULT_ID);
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

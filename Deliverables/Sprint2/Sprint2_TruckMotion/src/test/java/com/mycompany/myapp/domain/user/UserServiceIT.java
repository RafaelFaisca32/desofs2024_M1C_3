package com.mycompany.myapp.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.infrastructure.repository.jpa.UserRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/**
 * Integration tests for {@link UserService}.
 */
@IntegrationTest
@Transactional
class UserServiceIT {

    private static final String DEFAULT_LOGIN = "johndoe";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";

    private static final String DEFAULT_LASTNAME = "doe";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";

    private static final String DEFAULT_LANGKEY = "dummy";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditingHandler auditingHandler;

    @MockBean
    private DateTimeProvider dateTimeProvider;

    private User user;

    @BeforeEach
    public void init() {
        user = new User();
        user.setLogin(new Login(DEFAULT_LOGIN));
        user.setPassword(new Password(RandomStringUtils.randomAlphanumeric(60)));
        user.setActivated(new Activated(true));
        user.setEmail(new Email(DEFAULT_EMAIL));
        user.setFirstName(new FirstName(DEFAULT_FIRSTNAME));
        user.setLastName(new LastName(DEFAULT_LASTNAME));
        user.setImageUrl(new ImageUrl(DEFAULT_IMAGEURL));
        user.setLangKey(new LangKey(DEFAULT_LANGKEY));

        when(dateTimeProvider.getNow()).thenReturn(Optional.of(LocalDateTime.now()));
        auditingHandler.setDateTimeProvider(dateTimeProvider);
    }

    @Test
    @Transactional
    void assertThatUserMustExistToResetPassword() {
        userRepository.saveAndFlush(user);
        Optional<User> maybeUser = userService.requestPasswordReset("invalid.login@localhost");
        assertThat(maybeUser).isNotPresent();

        maybeUser = userService.requestPasswordReset(user.getEmail().getEmail());
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.orElse(null).getEmail().getEmail()).isEqualTo(user.getEmail().getEmail());
        assertThat(maybeUser.orElse(null).getResetDate().getResetDate()).isNotNull();
        assertThat(maybeUser.orElse(null).getResetKey().getResetKey()).isNotNull();
    }

    @Test
    @Transactional
    void assertThatOnlyActivatedUserCanRequestPasswordReset() {
        user.setActivated(new Activated(false));
        userRepository.saveAndFlush(user);

        Optional<User> maybeUser = userService.requestPasswordReset(user.getLogin().getLogin());
        assertThat(maybeUser).isNotPresent();
        userRepository.delete(user);
    }

    @Test
    @Transactional
    void assertThatResetKeyMustNotBeOlderThan24Hours() {
        Instant daysAgo = Instant.now().minus(25, ChronoUnit.HOURS);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(new Activated(true));
        user.setResetDate(new ResetDate(daysAgo));
        user.setResetKey(new ResetKey(resetKey));
        userRepository.saveAndFlush(user);

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey().getResetKey());
        assertThat(maybeUser).isNotPresent();
        userRepository.delete(user);
    }

    @Test
    @Transactional
    void assertThatResetKeyMustBeValid() {
        Instant daysAgo = Instant.now().minus(25, ChronoUnit.HOURS);
        user.setActivated(new Activated(true));
        user.setResetDate(new ResetDate(daysAgo));
        user.setResetKey(new ResetKey("1234"));
        userRepository.saveAndFlush(user);

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey().getResetKey());
        assertThat(maybeUser).isNotPresent();
        userRepository.delete(user);
    }

    @Test
    @Transactional
    void assertThatUserCanResetPassword() {
        String oldPassword = user.getPassword().getPassword();
        Instant daysAgo = Instant.now().minus(2, ChronoUnit.HOURS);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(new Activated(true));
        user.setResetDate(new ResetDate(daysAgo));
        user.setResetKey(new ResetKey(resetKey));
        userRepository.saveAndFlush(user);

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey().getResetKey());
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.orElse(null).getResetDate().getResetDate()).isNull();
        assertThat(maybeUser.orElse(null).getResetKey().getResetKey()).isNull();
        assertThat(maybeUser.orElse(null).getPassword().getPassword()).isNotEqualTo(oldPassword);

        userRepository.delete(user);
    }

    @Test
    @Transactional
    void assertThatNotActivatedUsersWithNotNullActivationKeyCreatedBefore3DaysAreDeleted() {
        Instant now = Instant.now();
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now.minus(4, ChronoUnit.DAYS)));
        user.setActivated(new Activated(false));
        user.setActivationKey(new ActivationKey(RandomStringUtils.random(20)));
        User dbUser = userRepository.saveAndFlush(user);
        dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        userRepository.saveAndFlush(user);
        Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
        List<User> users = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo);
        assertThat(users).isNotEmpty();
        userService.removeNotActivatedUsers();
        users = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo);
        assertThat(users).isEmpty();
    }

    @Test
    @Transactional
    void assertThatNotActivatedUsersWithNullActivationKeyCreatedBefore3DaysAreNotDeleted() {
        Instant now = Instant.now();
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now.minus(4, ChronoUnit.DAYS)));
        user.setActivated(new Activated(false));
        User dbUser = userRepository.saveAndFlush(user);
        dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        userRepository.saveAndFlush(user);
        Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
        List<User> users = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo);
        assertThat(users).isEmpty();
        userService.removeNotActivatedUsers();
        Optional<User> maybeDbUser = userRepository.findById(dbUser.getId());
        assertThat(maybeDbUser).contains(dbUser);
    }
}

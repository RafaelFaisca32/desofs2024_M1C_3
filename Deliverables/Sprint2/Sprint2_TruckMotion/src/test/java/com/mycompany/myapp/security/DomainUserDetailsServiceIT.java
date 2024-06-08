package com.mycompany.myapp.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.user.User;
import com.mycompany.myapp.infrastructure.repository.jpa.UserRepository;
import java.util.Locale;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integrations tests for {@link DomainUserDetailsService}.
 */
@Transactional
@IntegrationTest
class DomainUserDetailsServiceIT {

    private static final String USER_ONE_LOGIN = "test-user-one";
    private static final String USER_ONE_EMAIL = "test-user-one@localhost";
    private static final String USER_TWO_LOGIN = "test-user-two";
    private static final String USER_TWO_EMAIL = "test-user-two@localhost";
    private static final String USER_THREE_LOGIN = "test-user-three";
    private static final String USER_THREE_EMAIL = "test-user-three@localhost";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService domainUserDetailsService;

    @BeforeEach
    public void init() {
        User userOne = new User();
        userOne.updateLogin(USER_ONE_LOGIN);
        userOne.updatePassword(RandomStringUtils.randomAlphanumeric(60));
        userOne.activate();
        userOne.updateEmail(USER_ONE_EMAIL);
        userOne.updateFirstName("userOne");
        userOne.updateLastName("doe");
        userOne.updateLangKey("en");
        userRepository.save(userOne);

        User userTwo = new User();
        userTwo.updateLogin(USER_TWO_LOGIN);
        userTwo.updatePassword(RandomStringUtils.randomAlphanumeric(60));
        userTwo.activate();
        userTwo.updateEmail(USER_TWO_EMAIL);
        userTwo.updateFirstName("userTwo");
        userTwo.updateLastName("doe");
        userTwo.updateLangKey("en");
        userRepository.save(userTwo);

        User userThree = new User();
        userThree.updateLogin(USER_THREE_LOGIN);
        userThree.updatePassword(RandomStringUtils.randomAlphanumeric(60));
        userThree.deactivate();
        userThree.updateEmail(USER_THREE_EMAIL);
        userThree.updateFirstName("userThree");
        userThree.updateLastName("doe");
        userThree.updateLangKey("en");
        userRepository.save(userThree);
    }

    @Test
    void assertThatUserCanBeFoundByLogin() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_LOGIN);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    @Test
    void assertThatUserCanBeFoundByLoginIgnoreCase() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_LOGIN.toUpperCase(Locale.ENGLISH));
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    @Test
    void assertThatUserCanBeFoundByEmail() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_TWO_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TWO_LOGIN);
    }

    @Test
    void assertThatUserCanBeFoundByEmailIgnoreCase() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_TWO_EMAIL.toUpperCase(Locale.ENGLISH));
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TWO_LOGIN);
    }

    @Test
    void assertThatEmailIsPrioritizedOverLogin() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    @Test
    void assertThatUserNotActivatedExceptionIsThrownForNotActivatedUsers() {
        assertThatExceptionOfType(UserNotActivatedException.class).isThrownBy(
            () -> domainUserDetailsService.loadUserByUsername(USER_THREE_LOGIN)
        );
    }
}

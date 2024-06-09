package com.mycompany.myapp.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.shared.AbstractAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
public class User extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Embedded
    @Column(name = "uuid_id")
    private UserId uuidId;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    public User(){ this.uuidId = new UserId(); }

    public User(Long id, UserId uuidId){ this.id = id; this.uuidId = uuidId; }

    public User(User user){
        this.uuidId = new UserId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.isActivated();
        this.langKey = user.getLangKey();
        this.imageUrl = user.getImageUrl();
        this.activationKey = user.getActivationKey();
        this.resetKey = user.getResetKey();
        this.resetDate = user.getResetDate();
        this.uuidId = user.getUuidId();
        this.authorities = user.getAuthorities();

    }
    public User(Long id,
                String login,
                UserId userId,
                String firstName,
                String lastName,
                String email,
                String imageUrl,
                boolean activated,
                String langKey,
                Set<Authority> authorities
    )
    {
        this.id = id;
        this.login = login;
        this.uuidId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = activated;
        this.langKey = langKey;
        if(authorities != null){
            this.authorities.addAll(authorities);
        }
    }

    public User(String login,
                UserId userId,
                String firstName,
                String lastName,
                String email,
                String imageUrl,
                String langKey,
                String password,
                String resetKey,
                Instant resetDate,
                boolean activated,
                Set<Authority> authorities
    )
    {
        this.login = login;
        this.uuidId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = activated;
        this.resetKey = resetKey;
        this.resetDate = resetDate;
        this.langKey = langKey;
        this.password = password;
        if(authorities != null){
            this.authorities.addAll(authorities);
        }
    }

    public UserId getUuidId() {
        return this.uuidId != null ? new UserId(this.uuidId.value()) : null;
    }
    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    // Lowercase the login before saving it in database
    public void updateLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void updateFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void updateLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void updateActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void updateResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void updateResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void updateLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities != null ? new HashSet<>(this.authorities) : new HashSet<>();
    }

    public void updateAuthorities(Set<Authority> authorities) {
        this.authorities = authorities != null ? new HashSet<>(this.authorities) : new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }

    public void activate() {
        this.activated = true;
    }

    public void deactivate(){
        this.activated = false;
    }
}

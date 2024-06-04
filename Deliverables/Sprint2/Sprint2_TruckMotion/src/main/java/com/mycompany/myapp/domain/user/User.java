package com.mycompany.myapp.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.shared.AbstractAuditingEntity;
import com.mycompany.myapp.domain.transport.TransportId;
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

    @EmbeddedId
    @GeneratedValue
    @Column(name = "id")
    private UserId id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private Login login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private Password password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private FirstName firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private LastName lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private com.mycompany.myapp.domain.user.Email email;

    @NotNull
    @Column(nullable = false)
    private Activated activated;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private LangKey langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private ImageUrl imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private ActivationKey activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private ResetKey resetKey;

    @Column(name = "reset_date")
    private ResetDate resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    public User(){}

    public User(Login login,
                FirstName firstName,
                LastName lastName,
                com.mycompany.myapp.domain.user.Email email,
                ImageUrl imageUrl,
                LangKey langKey,
                Password password,
                ResetKey resetKey,
                ResetDate resetDate,
                Activated activated,
                Set<Authority> authorities
    )
    {
        this.login = login;
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

    public UserId getId() {
        return this.id != null ? new UserId(id.value()) : null;
    }

    public void setId(UserId id) {
        this.id = id;
    }

    public Login getLogin() {
        return login;
    }

    // Lowercase the login before saving it in database
    public void setLogin(Login login) {
        this.login = new Login(StringUtils.lowerCase(login.getLogin(), Locale.ENGLISH));
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public void setFirstName(FirstName firstName) {
        this.firstName = firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

    public void setLastName(LastName lastName) {
        this.lastName = lastName;
    }

    public com.mycompany.myapp.domain.user.Email getEmail() {
        return email;
    }

    public void setEmail(com.mycompany.myapp.domain.user.Email email) {
        this.email = email;
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ImageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Activated isActivated() {
        return activated;
    }

    public void setActivated(Activated activated) {
        this.activated = activated;
    }

    public ActivationKey getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(ActivationKey activationKey) {
        this.activationKey = activationKey;
    }

    public ResetKey getResetKey() {
        return resetKey;
    }

    public void setResetKey(ResetKey resetKey) {
        this.resetKey = resetKey;
    }

    public ResetDate getResetDate() {
        return resetDate;
    }

    public void setResetDate(ResetDate resetDate) {
        this.resetDate = resetDate;
    }

    public LangKey getLangKey() {
        return langKey;
    }

    public void setLangKey(LangKey langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
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
}

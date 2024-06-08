package com.mycompany.myapp.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.customer.Customer;
import com.mycompany.myapp.domain.driver.Driver;
import com.mycompany.myapp.domain.manager.Manager;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * A ApplicationUser.
 */
@Entity
@Table(name = "application_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Embedded
    @Column(name = "uuid_id")
    private ApplicationUserId uuidId;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    @Embedded
    private ApplicationUserBirthDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;


    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id", nullable = false,updatable = true)
    private User internalUser;

    @JsonIgnoreProperties(value = { "truck", "applicationUser", "transport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "applicationUser")
    private Driver driver;

    @JsonIgnoreProperties(value = { "applicationUser" }, allowSetters = true)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "applicationUser")
    private Manager manager;

    @JsonIgnoreProperties(value = { "applicationUser", "locations", "serviceRequest" }, allowSetters = true)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "applicationUser")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public ApplicationUser(){

    }

    public ApplicationUser(ApplicationUser applicationUser){

            this.id = applicationUser.getId();
            this.uuidId = applicationUser.getUuidId();
            this.birthDate = applicationUser.getBirthDate();
            this.gender = applicationUser.getGender();
            this.internalUser = applicationUser.getInternalUser();
            this.driver = applicationUser.getDriver();
            this.manager = applicationUser.getManager();
            this.customer = applicationUser.getCustomer();


    }
    public ApplicationUser(Long id,
                           ApplicationUserId uuidId,
                           ApplicationUserBirthDate birthDate,
                           Gender gender,
                           User internalUser) {
        this.id = id;
        this.uuidId = uuidId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.internalUser = internalUser;
    }

    public ApplicationUser(long id, ApplicationUserId applicationUserId) {
        this.id = id;
        this.uuidId = applicationUserId;
    }

    public Long getId() {
        return this.id;
    }

    public ApplicationUserId getUuidId() {
        return this.uuidId != null  ? new ApplicationUserId(this.uuidId.value()) : null;
    }
    public ApplicationUserBirthDate getBirthDate() {
        return new ApplicationUserBirthDate(this.birthDate.value());
    }

    public void updateBirthDate(ApplicationUserBirthDate birthDate) {
        this.birthDate = birthDate != null ? new ApplicationUserBirthDate(birthDate.value()) : null;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public User getInternalUser() {
        return this.internalUser != null ? new User(this.internalUser) : null;
    }

    public void updateInternalUser(User user) {
        this.internalUser = user != null ? new User(user) : null;
    }

    public Driver getDriver() {
        return this.driver != null ? new Driver(this.driver) : null;
    }

    public void updateDriver(Driver driver) {
        if (this.driver != null) {
            this.driver.updateApplicationUser(null);
        }
        if (driver != null) {
            driver.updateApplicationUser(this);
        }
        this.driver = driver != null ? new Driver(driver) : null;
    }

    public Manager getManager() {
        return this.manager != null ? new Manager(this.manager) : null;
    }

    public void updateManager(Manager manager) {
        if (this.manager != null) {
            this.manager.updateApplicationUser(null);
        }
        if (manager != null) {
            manager.updateApplicationUser(this);
        }
        this.manager = manager != null ? new Manager(manager) : null;
    }
    public Customer getCustomer() {
        return this.customer != null ? new Customer(this.customer) : null;
    }

    public void updateCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.updateApplicationUser(null);
        }
        if (customer != null) {
            customer.updateApplicationUser(this);
        }
        this.customer = customer != null ? new Customer(customer) : null;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUser)) {
            return false;
        }
        return getId() != null && getId().equals(((ApplicationUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUser{" +
            "id=" + getId() +
            ", birthDate='" + getBirthDate() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}

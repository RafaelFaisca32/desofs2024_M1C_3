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

    public Long getId() {
        return this.id;
    }
    public ApplicationUserId getUuidId() {
        if(this.uuidId == null) return null;

        return new ApplicationUserId(this.uuidId.value());
    }
    public ApplicationUser id(Long id) {
        this.setId(id);
        return this;
    }
    public ApplicationUser uuidid(ApplicationUserId uuidId) {
        this.setUuidId(uuidId);
        return this;
    }

    public void setUuidId(ApplicationUserId uuidId) {
        this.uuidId = uuidId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationUserBirthDate getBirthDate() {
        return new ApplicationUserBirthDate(this.birthDate.value());
    }

    public ApplicationUser birthDate(ApplicationUserBirthDate birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(ApplicationUserBirthDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return this.gender;
    }

    public ApplicationUser gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public ApplicationUser internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        if (this.driver != null) {
            this.driver.updateApplicationUser(null);
        }
        if (driver != null) {
            driver.updateApplicationUser(this);
        }
        this.driver = driver;
    }

    public ApplicationUser driver(Driver driver) {
        this.setDriver(driver);
        return this;
    }

    public Manager getManager() {
        return this.manager;
    }

    public void setManager(Manager manager) {
        if (this.manager != null) {
            this.manager.updateApplicationUser(null);
        }
        if (manager != null) {
            manager.updateApplicationUser(this);
        }
        this.manager = manager;
    }

    public ApplicationUser manager(Manager manager) {
        this.setManager(manager);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.updateApplicationUser(null);
        }
        if (customer != null) {
            customer.updateApplicationUser(this);
        }
        this.customer = customer;
    }

    public ApplicationUser customer(Customer customer) {
        this.setCustomer(customer);
        return this;
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

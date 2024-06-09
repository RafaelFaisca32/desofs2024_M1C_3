package com.mycompany.myapp.domain.manager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.user.ApplicationUser;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Manager.
 */
@Entity
@Table(name = "manager")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @GeneratedValue
    @Column(name = "id")
    private ManagerId id;

    @JsonIgnoreProperties(value = { "internalUser", "driver", "manager", "customer" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private ApplicationUser applicationUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Manager(){
        this.id = new ManagerId();
    }
    public Manager(Manager manager){
        this.id = new ManagerId();
        this.applicationUser = manager.getApplicationUser();
    }
    public Manager(ManagerId id, ApplicationUser applicationUser) {
        this.id = id;
        this.applicationUser = applicationUser;
    }

    public ManagerId getId() {
        return this.id != null ? new ManagerId(this.id.value()) : null;
    }

    public ApplicationUser getApplicationUser() {
        return this.applicationUser;
    }

    public void updateApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manager)) {
            return false;
        }
        return getId() != null && getId().equals(((Manager) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        String id = this.id != null ? this.id.toString() : "null";
        return "Manager{" +
            "id=" + id +
            "}";
    }
}

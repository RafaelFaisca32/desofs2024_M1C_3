package com.mycompany.myapp.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.myapp.domain.customer.dto.CustomerDTO;
import com.mycompany.myapp.domain.driver.dto.DriverDTO;
import com.mycompany.myapp.domain.manager.dto.ManagerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class CompleteUserDTO {

    @NotNull
    @JsonProperty("userDTO")
    @Valid
    private AdminUserDTO userDTO;
    @NotNull
    @Valid
    private ApplicationUserDTO applicationUserDTO;
    private ManagerDTO managerDTO;
    private DriverDTO driverDTO;
    private CustomerDTO customerDTO;

    public @NotNull AdminUserDTO getAdminUserDTO() {
        return userDTO;
    }

    public @NotNull ApplicationUserDTO getApplicationUserDTO() {
        return applicationUserDTO;
    }

    public ManagerDTO getManagerDTO() {
        return managerDTO;
    }

    public DriverDTO getDriverDTO() {
        return driverDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }
}

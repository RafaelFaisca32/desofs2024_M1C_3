package com.mycompany.myapp.domain.user.mapper;

import com.mycompany.myapp.domain.user.Authority;
import com.mycompany.myapp.domain.user.User;
import com.mycompany.myapp.domain.user.UserId;
import com.mycompany.myapp.domain.user.dto.AdminUserDTO;
import com.mycompany.myapp.domain.user.dto.UserDTO;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link user} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        if(users == null) return List.of();
        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).toList();
    }

    public UserDTO userToUserDTO(User user) {
        if(user == null) return null;
        return new UserDTO(user);
    }

    public UserDTO adminUserDTOToUserDTO(AdminUserDTO adminUserDTO) {
        if(adminUserDTO == null) return null;
        return new UserDTO(adminUserDTO.getId(),adminUserDTO.getLogin(), adminUserDTO.getUuidId());
    }

    public List<AdminUserDTO> usersToAdminUserDTOs(List<User> users) {
        if(users == null) return List.of();
        return users.stream().filter(Objects::nonNull).map(this::userToAdminUserDTO).toList();
    }

    public AdminUserDTO userToAdminUserDTO(User user) {
        if(user == null) return null;
        return new AdminUserDTO(user);
    }

    public List<User> userDTOsToUsers(List<AdminUserDTO> userDTOs) {
        if(userDTOs == null) return List.of();
        return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).toList();
    }

    public User userDTOToUser(AdminUserDTO userDTO) {
        if (userDTO == null) return null;
        Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());

        User user = new User(userDTO.getId(),
            userDTO.getLogin(),
            new UserId(userDTO.getUuidId()),
            userDTO.getFirstName(),
            userDTO.getLastName(),
            userDTO.getEmail(),
            userDTO.getImageUrl(),
            userDTO.isActivated(),
            userDTO.getLangKey(),
            authorities);
        return user;

    }

    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities = authoritiesAsString
                .stream()
                .map(string -> {
                    Authority auth = new Authority();
                    auth.updateName(string);
                    return auth;
                })
                .collect(Collectors.toSet());
        }

        return authorities;
    }

    public User userFromId(Long id, UUID uuid) {
        if (id == null) return null;
        User user = new User(id, new UserId(uuid));
        return user;
    }

    public User userFromDTO(UserDTO dto){
        if(dto == null) return null;
        User user = new User(dto.getId(), new UserId(dto.getUuid()));
        return user;
    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public UserDTO toDtoId(User user) {
        if (user == null) return null;
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        return userDto;
    }

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public Set<UserDTO> toDtoIdSet(Set<User> users) {
        if (users == null) return Collections.emptySet();

        Set<UserDTO> userSet = new HashSet<>();
        for (User userEntity : users) {
            userSet.add(this.toDtoId(userEntity));
        }

        return userSet;
    }

    @Named("login")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    public UserDTO toDtoLogin(User user) {
        if (user == null) return null;

        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        return userDto;
    }

    @Named("loginSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    public Set<UserDTO> toDtoLoginSet(Set<User> users) {
        if (users == null) return Collections.emptySet();

        Set<UserDTO> userSet = new HashSet<>();
        for (User userEntity : users) {
            userSet.add(this.toDtoLogin(userEntity));
        }

        return userSet;
    }
}

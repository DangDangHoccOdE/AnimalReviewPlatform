package com.animalreview.api.repository;

import com.animalreview.api.entity.Role;
import com.animalreview.api.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void UserRepository_FindByName_ReturnUser(){
        User user = User.builder().username("username").password("password").build();

        userRepository.save(user);

        User savedUser = userRepository.findByUsername(user.getUsername()).get();
        Assertions.assertThat(savedUser).isNotNull();

    }

    @Test
    public void UserRepository_FindById_ReturnUser(){
        User user = User.builder().username("username").password("password").build();

        userRepository.save(user);

        User savedUser = userRepository.findById(user.getId()).get();
        Assertions.assertThat(savedUser).isNotNull();

    }

    @Test
    public void UserRepository_ExistsByUsername_ReturnTrue(){
        User user = User.builder().username("username").password("password").build();

        userRepository.save(user);

        Boolean b = userRepository.existsByUsername(user.getUsername());
        Assertions.assertThat(b).isTrue();
    }

    @Test
    public void UserRepository_SaveUser_ReturnPersisted(){
        User user = User.builder().username("username").password("password").build();

        Role role = Role.builder().name("USER").build();
        roleRepository.save(role);
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);

        User saveUser = userRepository.findByUsername(user.getUsername()).get();
        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(user.getRoles().size()).isEqualTo(1);
    }
}

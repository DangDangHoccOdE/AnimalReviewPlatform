package com.animalreview.api.repository;

import com.animalreview.api.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void RoleRepository_FindByName_ReturnNotNull(){
        Role role = Role.builder().name("USER").build();
        roleRepository.save(role);

        Role findRole = roleRepository.findByName("USER").get();
        Assertions.assertThat(findRole).isNotNull();
    }
}

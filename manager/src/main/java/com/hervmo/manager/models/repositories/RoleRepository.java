package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSInput;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    Optional<Role> findByName(String roleName);

    Optional<List<Role>> findByActive(boolean active);
}

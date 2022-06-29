package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesRepository extends JpaRepository<States, Long> {
}

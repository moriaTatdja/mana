package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.Boxes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxesRepository extends JpaRepository<Boxes, Long> {

}

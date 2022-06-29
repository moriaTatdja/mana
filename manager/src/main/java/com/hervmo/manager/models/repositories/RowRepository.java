package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.Row;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowRepository extends JpaRepository<Row, Long> {
}

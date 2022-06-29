package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.ColumnOfRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnOfRowRepository extends JpaRepository<ColumnOfRow, Long> {
}

package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.BoxHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxHistoryRepository extends JpaRepository<BoxHistory, Long> {
}

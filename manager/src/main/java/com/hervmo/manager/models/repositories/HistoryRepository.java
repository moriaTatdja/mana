package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.ArticleHistory;
import com.hervmo.manager.models.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

}

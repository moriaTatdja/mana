package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {
}

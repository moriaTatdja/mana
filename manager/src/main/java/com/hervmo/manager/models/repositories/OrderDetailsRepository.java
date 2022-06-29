package com.hervmo.manager.models.repositories;

import com.hervmo.manager.models.entities.Articles;
import com.hervmo.manager.models.entities.Order;
import com.hervmo.manager.models.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    Optional<OrderDetails> findByArticleAndAndOrder(Articles a, Order o);

    Optional<List<OrderDetails>> findByOrder(Order o);
}

package core.basesyntax.repository.order;

import core.basesyntax.model.Order;
import core.basesyntax.model.OrderItem;
import core.basesyntax.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT i FROM OrderItem i WHERE i.order.user = :user")
    Page<OrderItem> findAllItemsByUser(@Param("user") User user, Pageable pageable);
}

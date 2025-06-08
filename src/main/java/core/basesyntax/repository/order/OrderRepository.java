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

    Page<Order> findAllOrdersByUser(User user, Pageable pageable);

    @Query("SELECT i FROM OrderItem i WHERE i.order.user = :user AND i.order.id = :orderId")
    Page<OrderItem> findAllItemsByUserAndOrderId(@Param("user") User user,
                                                 @Param("orderId") Long orderId, Pageable pageable);

    @Query("SELECT i FROM OrderItem i WHERE i.id = :id "
            + "AND i.order.id = :orderId AND i.order.user = :user")
    OrderItem findSecureItem(@Param("id") Long id,
                             @Param("orderId") Long orderId,
                             @Param("user") User user);
}

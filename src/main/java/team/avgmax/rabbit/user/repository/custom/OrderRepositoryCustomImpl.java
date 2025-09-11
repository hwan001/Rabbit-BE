package team.avgmax.rabbit.user.repository.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import team.avgmax.rabbit.bunny.entity.QOrder;
import team.avgmax.rabbit.user.dto.response.OrderResponse;
import team.avgmax.rabbit.user.dto.response.OrdersResponse;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public OrdersResponse findOrdersByUserId(String personalUserId) {
        QOrder order = QOrder.order;

        List<OrderResponse> orders = queryFactory
                .select(Projections.constructor(OrderResponse.class,
                        order.id,
                        order.bunny.bunnyName,
                        order.bunny.id,
                        order.quantity,
                        order.unitPrice,
                        order.orderType.stringValue()))
                .from(order)
                .where(order.user.id.eq(personalUserId))
                .fetch();

        return OrdersResponse.builder()
                .orders(orders)
                .build();
    }

}

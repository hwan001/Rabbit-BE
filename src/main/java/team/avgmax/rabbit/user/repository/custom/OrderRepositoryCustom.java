package team.avgmax.rabbit.user.repository.custom;

import team.avgmax.rabbit.user.dto.response.OrdersResponse;

public interface OrderRepositoryCustom {
    OrdersResponse findOrdersByUserId(String personalUserId);
}
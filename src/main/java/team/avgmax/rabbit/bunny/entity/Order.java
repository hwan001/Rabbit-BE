package team.avgmax.rabbit.bunny.entity;

import jakarta.persistence.*;
import lombok.*;
import team.avgmax.rabbit.global.entity.BaseTime;
import team.avgmax.rabbit.global.util.UlidGenerator;
import team.avgmax.rabbit.bunny.entity.enums.OrderType;

import java.math.BigDecimal;

import team.avgmax.rabbit.user.entity.PersonalUser;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`order`")
public class Order extends BaseTime {

    @Id
    @Column(name = "order_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private PersonalUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bunny_id", nullable = false)
    private Bunny bunny;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;
}

package team.avgmax.rabbit.bunny.entity;

import jakarta.persistence.*;
import lombok.*;
import team.avgmax.rabbit.bunny.entity.id.BunnyHistoryId;
import team.avgmax.rabbit.global.entity.BaseTime;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BunnyHistoryId.class)
public class BunnyHistory extends BaseTime {

    @Id
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Id
    @Column(name = "bunny_id", length = 26, nullable = false)
    private String bunnyId;

    private BigDecimal closingPrice;

    private BigDecimal highPrice;

    private BigDecimal lowPrice;

    private BigDecimal buyQuantity;

    private BigDecimal sellQuantity;

    private BigDecimal tradeQuantity;

    private BigDecimal marketCap;
}

package team.avgmax.rabbit.funding.repository;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.avgmax.rabbit.funding.entity.FundBunny;
import team.avgmax.rabbit.funding.entity.QFundBunny;
import team.avgmax.rabbit.bunny.entity.enums.BunnyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FundBunnyRepositoryCustomImpl implements FundBunnyRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;
    
    // BunnyType별 가격 상수
    private static final BigDecimal TYPE_A_PRICE = BigDecimal.valueOf(100_000);
    private static final BigDecimal TYPE_B_PRICE = BigDecimal.valueOf(1_000);
    private static final BigDecimal TYPE_C_PRICE = BigDecimal.valueOf(100);
    
    @Override
    public Page<FundBunny> findAllByOrderByCollectedAmountDesc(Pageable pageable) {
        QFundBunny fundBunny = QFundBunny.fundBunny;
        
        // 각 BunnyType별 가격을 CASE 문으로 정의
        NumberExpression<BigDecimal> priceExpression = new CaseBuilder()
                .when(fundBunny.type.eq(BunnyType.A)).then(TYPE_A_PRICE)
                .when(fundBunny.type.eq(BunnyType.B)).then(TYPE_B_PRICE)
                .when(fundBunny.type.eq(BunnyType.C)).then(TYPE_C_PRICE)
                .otherwise(BigDecimal.ZERO);
        
        List<FundBunny> content = queryFactory
                .selectFrom(fundBunny)
                .orderBy(fundBunny.collectedBny.multiply(priceExpression).desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        
        long total = queryFactory
                .selectFrom(fundBunny)
                .fetch()
                .size();
        
        return new PageImpl<>(content, pageable, total);
    }
    
    @Override
    public Page<FundBunny> findAllByOrderByCollectedAmountAsc(Pageable pageable) {
        QFundBunny fundBunny = QFundBunny.fundBunny;
        
        // 각 BunnyType별 가격을 CASE 문으로 정의
        NumberExpression<BigDecimal> priceExpression = new CaseBuilder()
                .when(fundBunny.type.eq(BunnyType.A)).then(TYPE_A_PRICE)
                .when(fundBunny.type.eq(BunnyType.B)).then(TYPE_B_PRICE)
                .when(fundBunny.type.eq(BunnyType.C)).then(TYPE_C_PRICE)
                .otherwise(BigDecimal.ZERO);
        
        List<FundBunny> content = queryFactory
                .selectFrom(fundBunny)
                .orderBy(fundBunny.collectedBny.multiply(priceExpression).asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        
        long total = queryFactory
                .selectFrom(fundBunny)
                .fetch()
                .size();
        
        return new PageImpl<>(content, pageable, total);
    }
    
    @Override
    public long countByEndAtWithin24Hours() {
        QFundBunny fundBunny = QFundBunny.fundBunny;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyFourHoursLater = now.plusHours(24);
        
        return queryFactory
                .selectFrom(fundBunny)
                .where(fundBunny.endAt.between(now, twentyFourHoursLater))
                .fetch()
                .size();
    }
}

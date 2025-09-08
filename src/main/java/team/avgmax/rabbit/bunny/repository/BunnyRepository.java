package team.avgmax.rabbit.bunny.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.avgmax.rabbit.bunny.entity.Bunny;
import team.avgmax.rabbit.bunny.repository.custom.BunnyRepositoryCustom;

import java.util.List;

public interface BunnyRepository extends JpaRepository<Bunny, String>, BunnyRepositoryCustom {

    // GOT 탑승한 버니들 (모든 Bunny 조회)
    List<Bunny> findAllByOrderByCreatedAtDesc();

    // 로켓 탑승한 버니들 (배지 3개인 Bunny 중 생성시간이 오래된 순으로 조회)
//    @Query("SELECT b " +
//            "FROM Bunny b JOIN Badge bg ON b.user.id = bg.userId " +
//            "GROUP BY b.id " +
//            "HAVING COUNT(b.id) = :badgeCount " +
//            "ORDER BY b.createdAt ASC")
    List<Bunny> findBunniesWithBadgeCount(@Param("badgeCount") long badgeCount); // COUNT Query 의 경우 long 으로 받는 것이 일반적

    // Top 5 버니들 (Bunny 의 시가총액이 가장 큰 순으로 5개 조회)
    List<Bunny> findTop5ByOrderByMarketCapDesc();
}
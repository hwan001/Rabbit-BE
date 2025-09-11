package team.avgmax.rabbit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.avgmax.rabbit.user.entity.Education;

// import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, String> {   
    //long deleteByPersonalUserId(String personalUserId);
}
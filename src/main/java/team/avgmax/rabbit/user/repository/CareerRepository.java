package team.avgmax.rabbit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.avgmax.rabbit.user.entity.Career;

// import java.util.Optional;

public interface CareerRepository extends JpaRepository<Career, String> {   
    // Optional<PersonalUser> findByEmail(String email);
    // Optional<PersonalUser> getUserById(String personalUserId);
}
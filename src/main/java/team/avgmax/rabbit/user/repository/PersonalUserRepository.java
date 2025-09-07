package team.avgmax.rabbit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team.avgmax.rabbit.user.entity.PersonalUser;

public interface PersonalUserRepository extends JpaRepository<PersonalUser, String> {   
}

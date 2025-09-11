package team.avgmax.rabbit.user.dto.request;

import java.time.LocalDate;
import java.util.List;

import team.avgmax.rabbit.user.entity.enums.Position;


public record PersonalUserRequest (
    String name,
    LocalDate birthdate,
    String image,
    String resume,
    Position position,
    List<SnsRequest> link,
    List<EducationRequest> education,
    List<CareerRequest> career,
    List<CertificationRequest> certification,
    List<String> skill 
){
    
}

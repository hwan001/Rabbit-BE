package team.avgmax.rabbit.auth.oauth2;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import team.avgmax.rabbit.user.service.PersonalUserService;
import team.avgmax.rabbit.user.entity.PersonalUser;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final PersonalUserService personalUserService;

    @Override
public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google, naver, github ...
    Map<String, Object> attributes = oAuth2User.getAttributes();

    String email;
    String name;
    String providerId;

    if ("naver".equals(registrationId)) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        email = (String) response.get("email");
        name = (String) response.get("name");
        providerId = (String) response.get("id");

    } else if ("google".equals(registrationId)) {
        email = (String) attributes.get("email");
        name = (String) attributes.get("name");
        providerId = (String) attributes.get("sub");

    } else if ("github".equals(registrationId)) {
        email = (String) attributes.get("email");
        name = (String) attributes.get("name");
        providerId = String.valueOf(attributes.get("id"));

    } else if ("kakao".equals(registrationId)) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        email = registrationId + "_" + attributes.get("id") + "@avgmax.team";
        name = profile.get("nickname").toString();

        providerId = String.valueOf(attributes.get("id"));

    } else {
        throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
    }

    // DB 조회 or 신규 가입 처리
    PersonalUser personalUser = personalUserService.findOrCreateUser(email, name, registrationId, providerId);

    return new CustomOAuth2User(personalUser, attributes, userRequest.getClientRegistration()
            .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());
}
}
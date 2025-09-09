package team.avgmax.rabbit.user.entity.enums;

public enum ProviderType {
    GOOGLE, KAKAO, NAVER, GITHUB;

    public static ProviderType from(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> GOOGLE;
            case "kakao" -> KAKAO;
            case "naver" -> NAVER;
            case "github" -> GITHUB;
            default -> throw new IllegalArgumentException("Unknown provider: " + registrationId);
        };
    }
}
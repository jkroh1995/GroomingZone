package tdd.groomingzone.auth.oauth2;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private static final String GOOGLE_ATTRIBUTE_NAME = "sub";
    private static final String KAKAO_NAVER_ATTRIBUTE_NAME = "id";

    private final Map<String, Object> attributes;     // OAuth2 반환하는 유저 정보
    private final String nameAttributesKey;
    private final String name;
    private final String email;
    private final String profileImageUrl;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributesKey,
                           String name, String email, String profileImageUrl) {
        this.attributes = attributes;
        this.nameAttributesKey = nameAttributesKey;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }

    public static OAuthAttributes of(String socialName, Map<String,Object> attributes){
        if(socialName.equals("kakao")){
            return ofKakao(attributes);
        }
        if(socialName.equals("google")){
            return ofGoogle(attributes);
        }
        if(socialName.equals("naver")){
            return ofNaver(attributes);
        }
        throw new BusinessException(ExceptionCode.INVALID_OAUTH_LOGIN);
    }

    private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .profileImageUrl(String.valueOf(attributes.get("profile_image")))
                .attributes(attributes)
                .nameAttributesKey(KAKAO_NAVER_ATTRIBUTE_NAME)
                .build();
    }

    private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .profileImageUrl(String.valueOf(attributes.get("picture")))
                .attributes(attributes)
                .nameAttributesKey(GOOGLE_ATTRIBUTE_NAME)
                .build();
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .attributes(attributes)
                .nameAttributesKey(KAKAO_NAVER_ATTRIBUTE_NAME)
                .build();
    }
}
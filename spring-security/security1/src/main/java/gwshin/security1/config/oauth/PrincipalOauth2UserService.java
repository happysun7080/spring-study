package gwshin.security1.config.oauth;

import gwshin.security1.config.authentication.PrincipalDetails;
import gwshin.security1.config.oauth.provider.FacebookUserInfo;
import gwshin.security1.config.oauth.provider.GoogleUserInfo;
import gwshin.security1.config.oauth.provider.OAuth2UserInfo;
import gwshin.security1.model.User;
import gwshin.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    /**
     * Google로부터 받은 userRequest 데이터 후처리 메소드
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest={}", userRequest);
        log.info("userRequest.getClientRegistration={}", userRequest.getClientRegistration());
        log.info("userRequest.getAccessToken={}", userRequest.getAccessToken());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User.getAttributes={}", oAuth2User.getAttributes());

        User user = addOAuth2User(userRequest, oAuth2User);
        log.info("user={}", user.toString());

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

    private User addOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        User user = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            log.info("구글 로그인 요청");
            user = addOAuthUserInfo(new GoogleUserInfo(oAuth2User.getAttributes()));
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            log.info("페이스북 로그인 요청");
            user = addOAuthUserInfo(new FacebookUserInfo(oAuth2User.getAttributes()));
        } else {
            log.warn("구글 또는 페이스북 로그인만 지원합니다.");
        }

        return user;
    }

    private User addOAuthUserInfo(OAuth2UserInfo oAuth2UserInfo) {

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("1234");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.info("구글/페이스북 로그인이 최초입니다.");
            user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            userRepository.save(user);
        } else {
            log.info("구글 로그인을 이미 한 적이 있습니다. 당신은 자동 회원가입이 되어있습니다.");
        }

        return user;
    }
}

package gwshin.security1.controller;

import gwshin.security1.config.authentication.PrincipalDetails;
import gwshin.security1.model.User;
import gwshin.security1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller  // view 리턴
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    @ResponseBody
    public String loginTest(
            Authentication authentication,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("principalDetails.getUser()={}", principalDetails.getUser());
        log.info("userDetails.getUsername()={}", userDetails.getUsername());

        return "세션 정보 확인";
    }

    @GetMapping("/test/oauth/login")
    @ResponseBody
    public String oAuthLoginTest(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oAuth2User
    ) {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        log.info("user.getAttributes()={}", user.getAttributes());
        log.info("oAuth2User.getAttributes()={}", oAuth2User.getAttributes());

        return "OAuth 세션 정보 확인";
    }

    public IndexController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("principalDetails.getUser()={}", principalDetails.getUser());

        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute User user) {
        log.info("user={}", user);
        user.setRole("ROLE_USER");
        encodePassword(user);

        userRepository.save(user);
        return "redirect:/loginForm";
    }

    private void encodePassword(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
    }

//    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/info")
    @ResponseBody
    public String info() {
        return "개인정보";
    }
}

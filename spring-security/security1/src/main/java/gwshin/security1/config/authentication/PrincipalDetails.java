package gwshin.security1.config.authentication;

// Spring Security가 "/login" 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인이 완료되면 Security session을 만들어준다. (Security ContextHolder)
// session에 들어갈 수 있는 오브젝트 타입 -> Authentication 객체
// Authentication 안에 User 정보가 있어야한다.
// User 오브젝트 타입 -> UserDetails 객체

// Security Session -> Authentication -> UserDetails(PrincipalDetails)

import gwshin.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    /**
     * 해당 User의 권한 반환
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

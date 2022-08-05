package gwshin.core;

import gwshin.core.member.MemberRepository;
import gwshin.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "gwshin.core",   // default: 현재 ComponentScan이 붙은 클래스의 위치
        basePackageClasses = AutoAppConfig.class,

        // AppConfig 유지하기 위함
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    // MemoryMemberRepository의 bean 이름과 중복
    // 수동 빈이 자동 빈을 오버라이딩
//    @Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }

}

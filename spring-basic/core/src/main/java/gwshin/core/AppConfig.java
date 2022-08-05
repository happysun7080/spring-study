package gwshin.core;

import gwshin.core.discount.DiscountPolicy;
import gwshin.core.discount.FixDiscountPolicy;
import gwshin.core.discount.RateDiscountPolicy;
import gwshin.core.member.MemberRepository;
import gwshin.core.member.MemberService;
import gwshin.core.member.MemberServiceImpl;
import gwshin.core.member.MemoryMemberRepository;
import gwshin.core.order.OrderService;
import gwshin.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configutation: AppConfig에 설정을 구성
@Configuration
public class AppConfig {

    // @Bean: Spring Container에 Spring Bean으로 등록
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}

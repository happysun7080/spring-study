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

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}

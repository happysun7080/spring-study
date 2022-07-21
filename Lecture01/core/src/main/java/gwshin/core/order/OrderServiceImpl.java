package gwshin.core.order;

import gwshin.core.discount.DiscountPolicy;
import gwshin.core.discount.FixDiscountPolicy;
import gwshin.core.discount.RateDiscountPolicy;
import gwshin.core.member.Member;
import gwshin.core.member.MemberRepository;
import gwshin.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements  OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // for test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

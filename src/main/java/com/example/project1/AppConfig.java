package com.example.project1;

import com.example.project1.discount.DiscountPolicy;
import com.example.project1.discount.FixDiscountPolicy;
import com.example.project1.discount.RateDiscountPolicy;
import com.example.project1.member.MemberRepository;
import com.example.project1.member.MemberService;
import com.example.project1.member.MemberServiceImpl;
import com.example.project1.member.MemoryMemberRepository;
import com.example.project1.order.OrderService;
import com.example.project1.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /* 역할과 구현 클래스가 한눈에 들어야 한다.*/

    // @Bean memberService -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository()
    // 각각 다른 2개의 MemoryMemberRepository 가 생성되어 싱글톤이 깨지는 것 처럼 보임
    // 하지만 다 같은 인스턴스 사용 => @Configuration 때문에


    @Bean
    public MemberService memberService() {
        //1번
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public OrderService orderService() {
        //1번
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }
    @Bean
    public MemberRepository memberRepository() {
        //2번? 3번?
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

}

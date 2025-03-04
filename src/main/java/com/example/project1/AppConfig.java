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

    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public static MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}

package com.example.project1.order;

import com.example.project1.discount.FixDiscountPolicy;
import com.example.project1.member.Grade;
import com.example.project1.member.Member;
import com.example.project1.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void createOrder() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderServiceImpl.createOrder(1L, "item`A", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(10000);
    }

}
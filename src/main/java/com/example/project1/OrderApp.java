package com.example.project1;

import com.example.project1.member.Grade;
import com.example.project1.member.Member;
import com.example.project1.member.MemberService;
import com.example.project1.member.MemberServiceImpl;
import com.example.project1.order.Order;
import com.example.project1.order.OrderService;
import com.example.project1.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order.toString());
    }
}

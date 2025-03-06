package com.example.project1.discount;

import com.example.project1.member.Grade;
import com.example.project1.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 10000;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        }else {
            return 0;
        }
    }
}

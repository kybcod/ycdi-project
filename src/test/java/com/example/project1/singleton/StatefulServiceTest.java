package com.example.project1.singleton;

import com.example.project1.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        /*

        // ThreadA : A사용자 10000원 주문
        statefulService1.order("userA", 10000);
        // ThreadB : B사용자 120000원 주문
        statefulService1.order("userB", 20000);

        // ThreadA : 사용자 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price); //20000 : statefulService11,2 모두 같은 인스턴스이기 때문

        */

        /* 스프링 빈은 항상 무상태로 설계해야 함(지역변수 ...) */
        int userA = statefulService1.order("userA", 10000);
        int userB = statefulService1.order("userB", 20000);
        System.out.println("price = " + userA); //10000

        assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}
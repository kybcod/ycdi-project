package com.example.project1;

import aop.TimeTraceAop;
import com.example.project1.repository.*;
import com.example.project1.service.OldMemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// 컴포넌트 스캔을 사용하지 않고(@Service, @Repository) 직접 빈 설정
@Configuration
public class SpringConfig {

    //@Autowired DataSource dataSource;

/*  JPA
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }*/

    //Spring JPA
    private final OldMemberRepository memberRepository;

    public SpringConfig(OldMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public OldMemberService memberService() {
        return new OldMemberService(memberRepository);
    }

    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }

/*    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//        return new JpaMemberRepository(em);
    }*/
}

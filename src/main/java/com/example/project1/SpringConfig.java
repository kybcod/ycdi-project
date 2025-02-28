package com.example.project1;

import com.example.project1.repository.JdbcMemberRepository;
import com.example.project1.repository.JdbcTemplateMemberRepository;
import com.example.project1.repository.MemberRepository;
import com.example.project1.repository.MemoryMemberRepository;
import com.example.project1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// 컴포넌트 스캔을 사용하지 않고(@Service, @Repository) 직접 빈 설정
@Configuration
public class SpringConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
}

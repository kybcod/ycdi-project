package com.example.project1.repository;

import com.example.project1.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String username);
    List<Member> findAll();
    void clearStore();
}



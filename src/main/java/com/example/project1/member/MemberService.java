package com.example.project1.member;

public interface MemberService {
    void join(Member member);
    Member findMember(Long id);
}

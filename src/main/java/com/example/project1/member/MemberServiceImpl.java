package com.example.project1.member;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {

    }

    @Override
    public Member findMember(Long id) {
        return null;
    }
}

package com.example.project1.service;

import com.example.project1.domain.OldMember;
import com.example.project1.repository.OldMemoryMemberRepository;
import com.example.project1.repository.OldMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    OldMemberService memberService;
    OldMemberRepository memberRepository ;

    // 각 테스트 실행하기 전에 실행
    @BeforeEach
    public void beforeEach() {
        memberRepository = new OldMemoryMemberRepository();
        memberService = new OldMemberService(memberRepository);
    }

    // 테스트 끝날 때 마다 저장소를 지워주어야 한다.
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }


    @Test
    void 회원가입() {
        // given
        OldMember member = new OldMember();
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then
        OldMember findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_에러(){
        //given
        OldMember member1 = new OldMember();
        member1.setName("spring");

        OldMember member2 = new OldMember();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/


    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
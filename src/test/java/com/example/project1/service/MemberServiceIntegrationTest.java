package com.example.project1.service;

import com.example.project1.domain.OldMember;
import com.example.project1.repository.OldMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired OldMemberService memberService;
    @Autowired OldMemberRepository memberRepository ;

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

    }

}
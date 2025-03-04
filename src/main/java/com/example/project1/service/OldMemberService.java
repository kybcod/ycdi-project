package com.example.project1.service;
import com.example.project1.domain.OldMember;
import com.example.project1.repository.OldMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OldMemberService {

    private final OldMemberRepository memberRepository;

    // 외부에서 memberRepository 넣어줌
    @Autowired
    public OldMemberService(OldMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(OldMember member) {
        // 같은 이름이 있는 중복 회원X

        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(OldMember member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회
     * @return
     */
    public List<OldMember> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 단일 회원 조회
     * @param id
     * @return
     */
    public Optional<OldMember> findOne(Long id) {
        return memberRepository.findById(id);
    }



}

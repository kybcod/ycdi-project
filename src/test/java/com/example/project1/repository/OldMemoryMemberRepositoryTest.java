package com.example.project1.repository;

import com.example.project1.domain.OldMember;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OldMemoryMemberRepositoryTest {

    OldMemoryMemberRepository repository = new OldMemoryMemberRepository();

    // 테스트 끝날 때 마다 저장소를 지워주어야 한다.
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        OldMember member = new OldMember();
        member.setName("spring");

        repository.save(member);

        OldMember result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        OldMember member1 = new OldMember();
        member1.setName("spring1");
        repository.save(member1);

        OldMember member2 = new OldMember();
        member2.setName("spring2");
        repository.save(member2);

        OldMember result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        OldMember member1 = new OldMember();
        member1.setName("spring1");
        repository.save(member1);

        OldMember member2 = new OldMember();
        member2.setName("spring2");
        repository.save(member2);

        List<OldMember> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}

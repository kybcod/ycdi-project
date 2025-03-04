package com.example.project1.repository;
import com.example.project1.domain.OldMember;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OldMemoryMemberRepository implements OldMemberRepository {

    private static Map<Long, OldMember> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public OldMember save(OldMember member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<OldMember> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<OldMember> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<OldMember> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void clearStore() {
        store.clear();
    }
}

package com.example.project1.repository;

import com.example.project1.domain.OldMember;

import java.util.List;
import java.util.Optional;

public interface OldMemberRepository {
    OldMember save(OldMember member);
    Optional<OldMember> findById(Long id);
    Optional<OldMember> findByName(String name);
    List<OldMember> findAll();
    void clearStore();
}



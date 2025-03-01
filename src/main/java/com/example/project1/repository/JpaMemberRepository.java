package com.example.project1.repository;

import com.example.project1.domain.OldMember;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements OldMemberRepository {

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public OldMember save(OldMember member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<OldMember> findById(Long id) {
        OldMember member = em.find(OldMember.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<OldMember> findByName(String name) {
        List<OldMember> result = em.createQuery("select m from Member m where m.name = :name", OldMember.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<OldMember> findAll() {
        // 객체 차제를 select
        return em.createQuery("select m from Member m", OldMember.class)
                .getResultList();
    }

    @Override
    public void clearStore() {

    }
}

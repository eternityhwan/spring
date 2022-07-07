package nomu.spring.repository;

import nomu.spring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; // jpa는 EntityManager로 모든것이 동작된다.

    public JpaMemberRepository(EntityManager em) { // 생성자
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member; // 이렇게까지만해도 JPA가 인서트부터 쿼리까지 때려준다.
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
                                        // JPQL 테이블대상으로 쿼리를 날리지 않아, 객체를 대상으로 날린다.
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return result;
    }

    @Override
    public void clearStore() {

    }
}

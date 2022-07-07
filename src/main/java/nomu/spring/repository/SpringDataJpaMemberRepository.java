package nomu.spring.repository;

import nomu.spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository 를 받아와서 쓰는게 jpa데이터이다
//                                                      멤버, 아이디타입 // 인터페이스 다중 상속가능 멤버리포지토리
// 인터페이스만 있지만 JpaRepository를 받고 있으면 스프링데이터제이피에이가 구현체를 만들어서 등록해준다.
// JpaRepository를 가져다 쓰기만 하면 된다 이것이지.
// jpa 레파지토리 들어가보면 crud 기능같은 것들은 다 구현 되어있어.

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}

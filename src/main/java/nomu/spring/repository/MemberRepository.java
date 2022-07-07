package nomu.spring.repository;

import nomu.spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id); // Optional 자바 8에 들어간 기능
    Optional<Member> findByName(String name); // 옵셔널, 널을 처리하는 방법, 찾다가 없으면
    List<Member> findAll(); /// 레파지토리 4가지 기능을 만들었어.

    void clearStore();

    // 말그대로 인터페이스일 뿐, 구현 코드 없으니까 클래스 하나 만들어서 구현을 해줘야해.

}

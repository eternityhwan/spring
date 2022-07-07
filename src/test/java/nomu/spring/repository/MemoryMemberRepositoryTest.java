package nomu.spring.repository;

import nomu.spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {

    MemberRepository repository = new MemoryMemberRepository();
    // 테스트 하고자하는 레파지토리 객체 만들면 됨.

    @Test
    public void save() { // 메인 메서드 쓰는 것처럼 해랏
        Member member = new Member(); // 맴버 객체 쓸거니까 객체 생성.
        member.setName("spring");

        repository.save(member); // 리포지토리에 멤머를 저장함

        Member result = repository.findById(member.getId()).get(); // findByid로 잘 가져왔는지 확인함.
        System.out.println("result = " + (result == member )); // result와 member와 같으면 제대로 가져온거야. true값 출력
        Assertions.assertThat(member).isEqualTo(result); // member가 result와 같아.
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get(); // get으로 꺼낸다.

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}

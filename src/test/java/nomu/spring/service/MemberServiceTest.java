package nomu.spring.service;

import nomu.spring.domain.Member;
import nomu.spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService();
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
       memberRepository.clearStore(); // 실행 돌릴 때마다 메모리
    }


    @Test
        // 메서드 이름 한글로 써도됨, 회원가입 으로 써된다 이거다. // 조인 메서드 검증
    void join() {
        // given --- 주석을 깔고 실행해봐라
        Member member = new Member();
        member.setName("hello"); // 회원가입 기능

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
        // 중복 회원 예외
    void findMembers() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));


//        @Test
//        void findOne () {
        }
    }

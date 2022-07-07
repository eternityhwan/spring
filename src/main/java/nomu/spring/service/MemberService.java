package nomu.spring.service;

import nomu.spring.domain.Member;
import nomu.spring.repository.MemberRepository;
import nomu.spring.repository.MemoryMemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
        // ctl + shift + T 로 테스트 만들기

// 서비스어노테이션안에 Component가 있어. 컴포넌트 스캔
// 스프링이 올라올 때 컴포넌트와 관련된 어노테이션을 보고 스프링이 객체를 하나씩 생성해서 등록한다
// 오토와이어드는 연결을 해줘 객체를 쓸 수 있게 해주는거지.
// 웬만한것은 다 스프링 빈으로 등록해서 써야하지.
@Transactional
public class MemberService {
    // 서비스를 만드려면 리포지토리가 있어야해 써야하니 객체 가져오기 final,
    private static final MemberRepository memberRepository = new MemoryMemberRepository();

            public MemberService(MemberRepository memberRepository) {
            }

            public MemberService() {
            }

            // 회원가입 이니까 세이브만 해주면됨
            // 중복회원만 제거
    public static Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        // 로직이 있으니까
        // null 가능성 있으면 Optional을 쓰면 됨.
        memberRepository.save(member);
        return member.getId();
    }

    // 메서드 추출 (ctrl + alt + m)
    private static void validateDuplicateMember(Member member) {
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> { //ifPresent 만약 존재하면.
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll(); // 반환타입 리스트 맴버(List<Member>
    }

    public static Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}

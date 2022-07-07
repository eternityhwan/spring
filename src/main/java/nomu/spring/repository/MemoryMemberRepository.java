package nomu.spring.repository;

import nomu.spring.domain.Member;

import java.util.*;
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>(); // 메모리니까 Map에 저장하는거야.
    private static long sequence = 0L; // 시퀀스 0,1,2, 키값 만들어주는 애.
    @Override
    public Member save(Member member) {
        member.setId(++sequence); // save하기전에 맴머에게 아이디 정해줌(시퀀스로)
        store.put(member.getId(), member); //store라는 애에게 put 메서드 사용함 멤머 객체에서 아이디 가져오고 저장
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null이 반환될 가능성 있으면 optional.ofNullable로 감싼다
    }                           // 스토어 객체에서 아이디를 get 하면됨.
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // value,stream()을 쓰고
                .filter(member -> member.getName().equals(name)) // 필터 람다 멤버.getname이 파라메터로 넘어온 이름인지 확인
                .findAny(); //
    }
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store 객체에 있는 values(멤버들) 반환됨.
    }

    public void clearStore() {
    }
}
// 검증 어디서? 테스트 케이스 작성

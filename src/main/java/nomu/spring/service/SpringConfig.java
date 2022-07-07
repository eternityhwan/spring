package nomu.spring.service;

import nomu.spring.aop.TimeTraceAop;
import nomu.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    // 스프링데이터 JPA가 만든 구현체를 등록함.
    private final MemberRepository memberRepository;


    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    private EntityManager em;
//    private DataSource dataSource;
//
////    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }
    // 스프링이 맴버 서비스와, 멤버 리포지토리를 스프링빈에 등록함.
    // 스프링빈에 등록된 맴버 리포지토리를 맴버 서비스에 넣어줌
   @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }               // 맴버서비스는 리포지토리를 써야하니까

    @Bean // AOP로 지정한 코드를 스프링빈에 등록하고 쓰는거다.
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
}
//    @Bean
//    public MemberRepository memberRepository() {
//        return JpaMemberRepository();
////        return new JpaMemberRepository(em);
////       return new JdbcTemplateMemberRepository(dataSource);
////        return new JdbcMemberRepository(dataSource);
//        // DI를 해주면됨. dataSource가 있어야 DB에 접근 가능하니까.
//        // return new MemoryMemberRepository();
        // 인터페이스를 두고고 구현체르 바꿔 끼울 수 있게
        // 스프링컨테이너가 지원 해준다.
        // 과거에는 코드 수정이 어마어마했다.



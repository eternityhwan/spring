package nomu.spring.controller;

import nomu.spring.domain.Member;
import nomu.spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
// 컨트롤러라는 애노테이션을보고 스프링이 뜰 때 헬로컨트롤러 객체를 들고 있다, 스프링 빈이 관리된다고 표현한다.
// 컨트롤러는 컴포넌트 스캔으로 할 수 밖에 없어.
public class MemberController {

    private MemberService memberService;
    // new로 MemberService() 하면 MemberController가 아닌 여러 컨트롤러들이 MemberService를 가져다 쓸 수 있어.
    // 스프링 컨테이너로 등록하면 생성자를 매번 생성할 필요가 없어.
    // 생성자를 만들어준 후 Autowired를 붙여주면 된다고

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName()); // 도메인 멤버에서 이름설정해라(setname), memberForm 클래스의 form 객체로부터 이름 가져와.

        System.out.println("member = " + member.getName());
        memberService.join(member); // 멤버서비스에서 조인 메서드를 실시
        return "redirect:/"; // 홈 화면으로 보내버림 회원 목록을 만들어야 작동한다
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}

//    @Autowired // 생성자 방식의 DI 주입
//    public MemberController(MemberService memberService) {
//        // MemberService 클래스에 들어가보면 순수한 자바 코드, 스프링이 얘를 알 수 있는 방법이 없어.
//        // 클래스에 @Service, @Repository를 붙여주면된다.
//
//        // 컨트롤러와 서비스를 연결시켜줄 때 @Autowired를 쓰는거임 - MemberController가 생성될 때 스프링빈에 등록된 멤버 서비스 객체를 넣어줌
//        // 이게 Dependency injection, 의존 관계 주입
//        this.memberService = memberService;
//    }


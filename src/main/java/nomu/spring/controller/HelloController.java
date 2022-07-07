package nomu.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","hello!!");
        return "hello";
    }
    // localhost:8080/hello, url로 들어가면 hello 메서드를 사용
    // model 객체로 data라는 key값을 넘기면 hello!! 밸류 값을 출력하는 메서드
    // templates 디렉토리에 hello.html에 값을 넘긴다.

    @GetMapping("hello-mvc") // 외부에서 파라메터를 받을 것 name이라는 것으로.
    // localhost8080/hello-mvc?name=파라메터값 이렇게 실행 시킴.
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-String")
    @ResponseBody
    //// http 통신 프로토콜 바디부에 직접넣어주겠다.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
        // view 없이 문자 그대로 내려간다.
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello(); // 인텔리제이 ctrl + shift + enter 넣으면 괄호 만듦
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

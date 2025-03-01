package com.example.project1.controller;

import com.example.project1.domain.OldMember;
import com.example.project1.service.OldMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OldMemberController {

 /*   // 필드 주입
    @Autowired private MemberService memberService;

    // setter 주입
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }*/

    // 생성자 주입
    private OldMemberService memberService;

    @Autowired
    public OldMemberController(OldMemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass()); //프록시 : CGLIB
    }

    @GetMapping("/oldmembers/new")
    public String createForm() {
        return "members/createMemberForm"; // createMemberForm.html
    }

    @PostMapping("/oldmembers/new")
    public String create(OldMemberForm oldMemberForm) {
        OldMember member = new OldMember();
        member.setName(oldMemberForm.getName());

        System.out.println("member = " + member.getName());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/oldmembers")
    public String list(Model model) {
        List<OldMember> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "oldmembers/memberList";
    }
}

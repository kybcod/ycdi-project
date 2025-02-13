package com.example.project1.controller;

import com.example.project1.domain.LoginRequest;
import com.example.project1.exception.PasswordIncorrectException;
import com.example.project1.service.MemberService;
import com.example.project1.vo.Member;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final MemberService memberService;

	@Description("로그인")
	@GetMapping("/login")
	public String loginPage() {
		return "layout/login";
	}

	@PostMapping("/login")
	public String login(@Valid LoginRequest login, HttpSession session, RedirectAttributes rttr, Model model) {
		Member foundMember = memberService.getMemberById(login.getId());

		// 조회된 아이디가 없을 경우(삭제된 id도 마찬가지)
		if (login.getId() == null || foundMember == null || (foundMember.getDeleted() != null && foundMember.getDeleted())) {
			throw new IllegalArgumentException("아이디를 확인해주세요.");
		}
		
		if (foundMember.getLocked() != null && foundMember.getLocked() == true) {
			throw new IllegalStateException("잠긴 계정입니다. 관리자에게 문의 해주시기 바랍니다.");
		}

		// 비밀번호 맞는지 확인
		if (!foundMember.getPassword().equals(login.getPassword())) {
			// 실패 시도 횟수
			int failedAttempts = memberService.incrementFailedAttempts(foundMember);
			
			if (failedAttempts >= 1 && failedAttempts < 5) { 
				throw new PasswordIncorrectException("비밀번호가 틀렸습니다.", failedAttempts, login.getId());
	        }

			// 5회 이상 틀릴 시 잠김 알려주기
			if (failedAttempts >= 5) {
				memberService.updateLocked(login.getId());
				throw new IllegalStateException("비밀번호 5회 입력 오류입니다. 관리자에게 문의 해주시기 바랍니다.");
            }

			rttr.addFlashAttribute("loggedId", login.getId()); 
			session.invalidate(); // 세션 초기화
			return "redirect:/login";
		}

		// 로그인 성공 시 id, 등급, 유효시간(무한) session에 담기
		session.setAttribute("id", foundMember.getId());
		session.setAttribute("authority", foundMember.getAuthority());
		session.setMaxInactiveInterval(-1);

		// 로그인 성공 시 실패 횟수 초기화
		memberService.resetFailedAttempts(login.getId());
		
		return "redirect:/members/list";
	}


	
	@Description("로그아웃")
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

}

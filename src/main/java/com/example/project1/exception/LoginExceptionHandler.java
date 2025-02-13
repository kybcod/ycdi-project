package com.example.project1.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class LoginExceptionHandler {
	
	@ExceptionHandler(PasswordIncorrectException.class)
	public String handlePasswordIncorrectException(PasswordIncorrectException ex, RedirectAttributes rttr) {
	    rttr.addFlashAttribute("message", ex.getMessage() + " (" + ex.getFailedAttempts() + "회 실패)");
	    rttr.addFlashAttribute("loggedId", ex.getId()); 
	    return "redirect:/login";
	}

	// 아이디나 비밀번호가 존재하지 않은 인자를 받을 때 
	@ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, RedirectAttributes rttr) {
        rttr.addFlashAttribute("message", ex.getMessage());
        return "redirect:/login";
    }

	// 계정이 잠긴 경우
    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(IllegalStateException ex, RedirectAttributes rttr) {
        rttr.addFlashAttribute("message", ex.getMessage());
        return "redirect:/login";
    }

}
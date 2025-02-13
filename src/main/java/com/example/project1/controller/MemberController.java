

package com.example.project1.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.project1.domain.*;
import com.example.project1.service.MemberService;
import com.example.project1.vo.Member;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@Description("사용자 목록 화면 조회")
	@GetMapping("/list")
	public String memberListView() {
		return "layout/memberList";
	}

	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> memberListPageSearch(
	        @RequestParam(value = "searchType", required = false) MemberSearchType searchType,
	        @RequestParam(value = "searchValue", required = false) String searchValue,
	        @RequestParam(value = "authoritySearchValue", required = false) String authoritySearchValue,
	        @RequestParam(value = "startDate", required = false) String startDate,
	        @RequestParam(value = "endDate", required = false) String endDate,
	        @PageableDefault(value=10) Pageable pageable) {

	    Page<MemberResponse> memberPage = memberService.getMemberAll(searchType, searchValue, authoritySearchValue, startDate, endDate, pageable);

	    Map<String, Object> response = new HashMap<>();
	    response.put("memberList", memberPage.getContent());
	    response.put("totalPages", memberPage.getTotalPages());
	    response.put("currentPage", pageable.getPageNumber()); 
	    response.put("totalElements", memberPage.getTotalElements());

	    return ResponseEntity.ok(response);
	}


	@Description("회원 정보 상세 조회")
	@GetMapping("/{id}")
	@ResponseBody
	public Member getMemberById(@PathVariable("id") String id) {
		return memberService.getMemberById(id);
	}
	
	
	@Description("회원가입")
	@PostMapping
	public String join(@Valid MemberJoinRequest member,
			@RequestParam(value = "file", required = false) MultipartFile profile) throws Exception {
		memberService.join(member, profile);
		return "redirect:/members/list";
	}


	@Description("아이디 중복확인")
	@PostMapping("/idCheck")
	public ResponseEntity<Boolean> idcheck(@RequestParam("id") String id) {
	    boolean isDuplicate;

	    // id가 null이거나 빈 문자열일 경우 false 설정
	    if (id == null || id.isEmpty()) {
	        isDuplicate = true;
	    } else {
	        Member member = memberService.getMemberById(id);
	        isDuplicate = (member != null);
	    }

	    return ResponseEntity.ok(isDuplicate);
	}

	

	@Description("세션에 담긴 로그인 유저 정보")
	@ModelAttribute("loggedMember")
	public MemberResponse addMemberToModel(HttpSession session) {
		String memberId = (String) session.getAttribute("id");
		if (memberId != null) {
			return MemberResponse.toResponse(memberService.getMemberById(memberId));
		}
		return new MemberResponse();
	}

	
    @Description("회원 정보 수정")
    @PutMapping
    public ResponseEntity<?> updateMemberInfo(@Valid MemberUpdateRequest memberUpdateRequest,
                                                 @RequestParam(value = "file", required = false) MultipartFile profile, 
                                                 HttpSession session) throws IOException {
        String loggedMemberId = (String) session.getAttribute("id");
        memberService.updateMember(memberUpdateRequest, profile, loggedMemberId);
        return ResponseEntity.ok().build();
    }

	
	@Description("로그인된 회원 프로필 변경")
	@PutMapping("/profile")
	public ResponseEntity<?> updateMemberInfo(
			@RequestParam(value = "file", required = false) MultipartFile profile, 
			HttpSession session)
			throws Exception {

		String loggedMemberId = (String) session.getAttribute("id");

		memberService.updateMemberProfile(profile, loggedMemberId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Description("회원 정보 삭제")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMember(@PathVariable("id") String id, HttpSession session) {

		String loggedMemberId = (String) session.getAttribute("id");

		// 로그인한 사용자가 없을 때
		if (loggedMemberId == null) {
			return ResponseEntity.status(401).body("로그인이 필요합니다.");
		}

		// 로그인한 id랑 삭제할 아이디가 같다면 오류
		if (loggedMemberId.equals(id)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("자신의 계정은 삭제할 수 없습니다.");
		}

		try {
			memberService.deleteMemberById(id, loggedMemberId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Description("여러 회원 삭제")
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteMembers(@RequestBody MemberDeleteRequest request, HttpSession session) {
	    String loggedMemberId = (String) session.getAttribute("id");
	    
	    try {
	        memberService.deleteMembers(request.getMemberIds(), loggedMemberId);
	        return ResponseEntity.ok("회원이 성공적으로 삭제되었습니다.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("회원 삭제 중 오류가 발생했습니다.");
	    }
	}
	
	@Description("계정 잠금/해제")
	@PutMapping("/{id}/locked")
	public ResponseEntity<?> updateMemberLockStatus(@PathVariable("id") String id, @RequestParam("locked") boolean locked,
	        HttpSession session) {
	    String authority = (String) session.getAttribute("authority");

	    // 중간 관리자 : 잠금 해제만 가능
	    if ("admin".equals(authority) || ("middle-admin".equals(authority) && !locked)) {
	        memberService.updateMemberLocked(id, locked);
	        return ResponseEntity.ok().build(); // 성공 시 200 OK 응답
	    }

	    return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 권한이 없을 경우 403 Forbidden 응답
	}

}
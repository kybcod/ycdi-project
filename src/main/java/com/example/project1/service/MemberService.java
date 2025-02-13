package com.example.project1.service;

import com.example.project1.dao.MemberMapper;
import com.example.project1.domain.MemberJoinRequest;
import com.example.project1.domain.MemberResponse;
import com.example.project1.domain.MemberSearchType;
import com.example.project1.domain.MemberUpdateRequest;
import com.example.project1.exception.InvalidImageFileException;
import com.example.project1.exception.MemberNotFoundException;
import com.example.project1.vo.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MemberService {

	private final MemberMapper memberMapper;

	@Value("${file.upload.directory}")
	private String uploadDir;

	// 회원 상세 조회
	public Member getMemberById(String id) {
		return memberMapper.selectById(id);
	}

	
	// 회원 등록
	public void join(MemberJoinRequest request, MultipartFile profile) throws Exception {
		File directory = new File(uploadDir);
		if (!directory.exists()) {
			directory.mkdirs(); // 모든 경로의 부모 디렉토리도 생성
		}

		String profileUrl;
		if (profile != null && !profile.isEmpty()) {
			String fileName = profile.getOriginalFilename();
			
			// 유효성 검사 (파일 확장자)
			validateImageFile(fileName);
			
			// 파일 크기 10MB까지
	        long fileSize = profile.getSize(); // 바이트 단위
	        if (fileSize > 10 * 1024 * 1024) { // 10MB 초과
	            throw new InvalidImageFileException("파일 크기는 10MB를 초과할 수 없습니다.");
	        }
			
			// UUID를 사용하여 랜덤한 파일명 생성
	        String uuid = UUID.randomUUID().toString();
	        String fileExtension = fileName.substring(fileName.lastIndexOf(".")); // 파일 확장자
	        String newFileName = uuid + fileExtension; // 새로운 파일명

			File saveFile = new File(uploadDir, newFileName); //저장 폴더 이름 + 새로운 파일명
			profile.transferTo(saveFile);
			profileUrl = "/profile/" + newFileName;
		} else {
			// 기본 프로필 전환
			profileUrl = "/profile/original.png";
		}
		
		Member member = Member.builder().id(request.getId()).password(request.getPassword()).name(request.getName())
				.tel(request.getTel()).postcode(request.getPostcode()).address(request.getAddress())
				.detailAddress(request.getDetailAddress()).authority(request.getAuthority()).profile(profileUrl)
				.build();

		memberMapper.insert(member);
	}

	// 페이지/검색 처리된 회원 리스트
	public Page<MemberResponse> getMemberAll(MemberSearchType memberSearchType, String memberSearchValue,
											 String authoritySearchValue, String startDate, String endDate, Pageable pageable) {
		
		int pageSize = pageable.getPageSize(); //반환할 항목의 수
		int offset = (int) pageable.getOffset(); // 페이지 크기에 따라 달라짐

		String searchType = MemberSearchType.getName(memberSearchType); //소문자로 변환

		String searchValue; //serachValue 구분(등급/이외)
		if (memberSearchType == MemberSearchType.AUTHORITY)
			searchValue = authoritySearchValue;
		else
			searchValue = memberSearchValue;

		List<Member> memberList = memberMapper.selectMemberByCondition(searchType, searchValue, startDate, endDate,pageSize, offset);
		int total = memberMapper.countMember(searchType, searchValue, startDate, endDate);

		List<MemberResponse> result = memberList.stream().map(data -> MemberResponse.toResponse(data))
				.collect(Collectors.toList());

		return new PageImpl<>(result, pageable, total);
	}

	// 비밀번호 실패
	public int incrementFailedAttempts(Member member) {
		int failedAttempts = 1;
		Boolean locked = false;

		if (member.getFailedAttempts() != 0) {
			failedAttempts = member.getFailedAttempts() + 1;
		}

		// 시도횟수가 5이상일 경우 => 계정 잠김
		if (member.getFailedAttempts() >= 5) {
			locked = true;
		}

		Member updatedMember = Member.builder().id(member.getId()).failedAttempts(failedAttempts).locked(locked)
				.build();

		memberMapper.updateFailedAttempts(updatedMember);
		return failedAttempts;
	}

	// 로그인 성공 시 시도 리셋
	public void resetFailedAttempts(String id) {
		Member member = Member.builder()
				.id(id)
				.failedAttempts(0)
				.locked(false)
				.build();
		memberMapper.updateFailedAttempts(member);
	}

	// 회원 정보 수정
	public void updateMember(MemberUpdateRequest memberUpdateRequest, MultipartFile profile, String loggedMemberId) throws IOException {

		File directory = new File(uploadDir);
		if (!directory.exists()) {
			directory.mkdirs(); // 모든 경로의 부모 디렉토리도 생성
		}

		String profileUrl;
		if (!profile.isEmpty() && profile != null) {
			String fileName = profile.getOriginalFilename();

			// 유효성 검사 (파일 확장자, 파일 크기)
			validateImageFile(fileName);
	        long fileSize = profile.getSize(); // 바이트 단위
	        if (fileSize > 10 * 1024 * 1024) { // 10MB 초과
	            throw new InvalidImageFileException("파일 크기는 10MB를 초과할 수 없습니다.");
	        }
			
			// UUID를 사용하여 랜덤한 파일명 생성
	        String uuid = UUID.randomUUID().toString();
	        String fileExtension = fileName.substring(fileName.lastIndexOf(".")); // 파일 확장자
	        String newFileName = uuid + fileExtension; // 새로운 파일명
	        
	        
			File saveFile = new File(uploadDir, newFileName);
			profile.transferTo(saveFile);
			profileUrl = "/profile/" + newFileName;
		} else {
			profileUrl = memberUpdateRequest.getProfile();
		}
		
		Member member = Member.builder()
				.id(memberUpdateRequest.getId())
				.name(memberUpdateRequest.getName())
				.tel(memberUpdateRequest.getTel())
				.postcode(memberUpdateRequest.getPostcode())
				.address(memberUpdateRequest.getAddress())
				.detailAddress(memberUpdateRequest.getDetailAddress())
				.authority(memberUpdateRequest.getAuthority())
				.profile(profileUrl)
				.updatedAt(LocalDateTime.now())
				.updatedBy(loggedMemberId)
				.build();
		
		memberMapper.update(member);
	}
	
	// 회원 잠금 상태 변화
	public void updateMemberLocked(String id, boolean locked) {
        // 잠금 해제 시 실패 횟수 초기화 및 locked 상태 변경
        if (!locked) {
            resetFailedAttempts(id);  // 로그인 실패로 인해 잠금되었을 때 초기화
        } else {
            updateLocked(id);  // 잠금 상태 설정
        }
    }

	public void updateLocked(String id) {
		Member member = Member.builder()
		.id(id)
		.locked(true)
		.build();
		
		memberMapper.updateLocked(member);
	}

	
	// 회원 프로필 수정
	public void updateMemberProfile(MultipartFile profile, String loggedMemberId) throws Exception {
		
		String profileUrl;
		if (!profile.isEmpty() && profile != null) {
			String fileName = profile.getOriginalFilename();
			
			// 유효성 검사 (파일 확장자, 파일 크기)
			validateImageFile(fileName);
	        long fileSize = profile.getSize(); // 바이트 단위
	        if (fileSize > 10 * 1024 * 1024) { // 10MB 초과
	            throw new InvalidImageFileException("파일 크기는 10MB를 초과할 수 없습니다.");
	        }
			
			// UUID를 사용하여 랜덤한 파일명 생성
	        String uuid = UUID.randomUUID().toString();
	        String fileExtension = fileName.substring(fileName.lastIndexOf(".")); // 파일 확장자
	        String newFileName = uuid + fileExtension; // 새로운 파일명
	        
	        
			File saveFile = new File(uploadDir, newFileName);
			profile.transferTo(saveFile);
			profileUrl = "/profile/" + newFileName;
		} else {
			profileUrl = "/profile/original.png";
		}
		
		Member member = Member.builder()
				.profile(profileUrl)
				.id(loggedMemberId)
				.build();
		
		memberMapper.updateProfile(member);
		
	}

	// 한명의 회원 삭제
	public void deleteMemberById(String id, String loggedMemberId) {

		if (id == null) {
	        throw new MemberNotFoundException("회원이 존재하지 않습니다.");
	    }
		
		Member deleteMember = Member.builder()
				.id(id)
				.deleted(true).updatedBy(loggedMemberId).updatedAt(LocalDateTime.now())
				.build();

		memberMapper.updateMember(deleteMember);
	}
	
	// 여러명의 회원 삭제
	public void deleteMembers(List<String> memberIds, String loggedMemberId) throws Exception {
		
        for (String memberId : memberIds) {
            if (memberIds == null) {
                throw new MemberNotFoundException("회원이 존재하지 않습니다: " + memberId);
            }
            
        	Member deleteMember = Member.builder()
    				.id(memberId)
    				.deleted(true)
    				.updatedBy(loggedMemberId)
    				.updatedAt(LocalDateTime.now())
    				.build();

    		memberMapper.updateMember(deleteMember);
        }
		
	}
	
	// 파일 형식 유효성 검사
	public void validateImageFile(String fileName) {
        if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")) {
            throw new InvalidImageFileException("jpg, jpeg, png 파일만 업로드 가능합니다.");
        }
    }

}

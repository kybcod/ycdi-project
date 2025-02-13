package com.example.project1.domain;

import com.example.project1.vo.Member;
import lombok.*;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class MemberResponse {
	private int no;
	private String id;
	private String profile;
	private String password;
	private String name;
	private String tel;
	private String postcode;
	private String address;
	private String detailAddress;
	private String authority;
	private int failedAttempts;
	private Boolean locked;
	private String createdAt;
	private String updatedAt;
	private String updatedBy;
	private Boolean deleted;

	public static MemberResponse toResponse(Member entity) {
		return MemberResponse.builder()
				.no(entity.getNo())
				.id(entity.getId())
				.password(entity.getPassword())
				.name(entity.getName())
				.tel(entity.getTel().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3"))
				.postcode(entity.getPostcode())
				.address(entity.getAddress())
				.detailAddress(entity.getDetailAddress())
				.authority(MemberAuthority.getName(entity.getAuthority()))
				.locked(entity.getLocked())
				.createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.profile(entity.getProfile())
				.build();
	}

}

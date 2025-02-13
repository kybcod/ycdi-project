package com.example.project1.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Member {
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
	private Boolean locked;
	private LocalDateTime createdAt;
	private int failedAttempts;
	private LocalDateTime updatedAt;
	private Boolean deleted;
	private String updatedBy;
	
	@Override
	public String toString() {
		return "Member [no=" + no + ", id=" + id + ", profile=" + profile + ", password=" + password + ", name=" + name
				+ ", tel=" + tel + ", postcode=" + postcode + ", address=" + address + ", detailAddress="
				+ detailAddress + ", authority=" + authority + ", locked=" + locked + ", createdAt=" + createdAt
				+ ", failedAttempts=" + failedAttempts + ", updatedAt=" + updatedAt + ", deleted=" + deleted
				+ ", updatedBy=" + updatedBy + "]";
	}
}

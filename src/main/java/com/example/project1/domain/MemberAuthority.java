package com.example.project1.domain;

import java.util.Arrays;

public enum MemberAuthority {
	ADMIN("admin", "관리자"),
	MIDDLE_ADMIN("middle-admin", "중간 관리자"),
	USER("user", "일반 사용자"),
	;

	private final String value;
	private final String descreption;

	MemberAuthority(String value, String descreption) {
		this.value = value;
		this.descreption = descreption;
	}
	
	public String getValue() {
		return value;
	}

	public String getDescreption() {
		return descreption;
	}
	
	public static String getName(String authority) {
		return Arrays.stream(values())
		.filter(val -> authority.equals(val.value))
        .findFirst().orElse(null).descreption;
	}
	
}

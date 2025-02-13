package com.example.project1.domain;

public enum MemberSearchType {
	ID("아이디"),
	NAME("이름"),
	TEL("전화번호"),
	ADDRESS("주소"),
	CREATEDAT("가입일자"),
	AUTHORITY("사용자 등급"),
	;

	private final String descreption;

	MemberSearchType(String descreption) {
		this.descreption = descreption;
	}
	
	public String getDescreption() {
		return descreption;
	}

	public static String getName(MemberSearchType memberSearchType) {
		if(memberSearchType == null) return "";
		else return memberSearchType.name().toLowerCase();
	}
	
}

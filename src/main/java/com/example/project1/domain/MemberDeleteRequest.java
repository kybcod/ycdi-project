package com.example.project1.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberDeleteRequest {
	private List<String> memberIds;
}

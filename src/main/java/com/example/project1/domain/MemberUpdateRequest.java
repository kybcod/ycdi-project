package com.example.project1.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberUpdateRequest {
	
	@Size(max = 50)
	@NotBlank (message = "아이디를 입력하세요")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 염문자와 숫자가 모두 포함되어야 합니다")
	private String id;
	
	@NotBlank (message = "이름을 입력하세요")
	@Size(max = 50, message = "이름은 최대 50자까지 가능합니다")
	@Pattern(regexp = "^[가-힣]+$", message = "이름은 한글로만 작성해야 합니다")
	private String name;
	
	@NotBlank(message = "전화번호를 입력하세요")
	@Size(max = 15, message = "전화번호는 최대 15자까지 가능합니다.")
	@Pattern(regexp = "^010\\d{8}$", message = "전화번호는 010으로 시작하는 11자리 숫자여야 합니다.")
    private String tel;
	
	@NotBlank(message = "우편번호를 입력하세요") 
	@Size(max = 10, message = "우편번호는 최대 10자까지 가능합니다")
    private String postcode;

    @NotBlank(message = "주소를 입력하세요") 
    @Size(max = 200, message = "주소는 최대 200까지 가능합니다")
    private String address;
        
    @NotBlank(message = "상세 주소를 입력하세요") 
    @Size(max = 100, message = "상세 주소는 최대 100까지 가능합니다")
    private String detailAddress;

    @NotBlank(message = "권한을 선택하세요") 
    private String authority;
    
    @Size(max = 200, message = "파일 경로는 최대 200자까지 가능합니다.")
    @Pattern(regexp = ".*\\.(png|jpg|jpge)$", message = "png, jpg, jpge 확장자만 가능합니다.")
    private String profile;

}

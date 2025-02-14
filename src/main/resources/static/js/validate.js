const validateElement = function () {

    $.validator.addMethod("pwdRegx", function (value, element, pwdRegx) {
        return pwdRegx.test(value);
    }, "비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다.");

    $.validator.addMethod("phoneRegx", function (value, element, phoneRegx) {
        return phoneRegx.test(value);
    }, "전화번호 형식으로 입력해주세요.");

    $.validator.addMethod("idRegx", function (value, element, idRegx) {
        return idRegx.test(value);
    }, valiables.idRegxMessage);

    $.validator.addMethod("idCheck", function () {
        return valiables.isCheck === true;
    }, "아이디 중복체크를 해주세요.");

    $("#joinForm").validate({
        ignore: [], // display :none인 element로 체크
        rules: {
            p_user_id: {
                idRegx: valiables.idRegx, // 5~20자, 영문 소문자, 숫자, 특수기호 (_), (-)만 사용 가능
                idCheck: true
            },
            p_pwd: {
                minlength: 8,
                maxlength: 20,
                pwdRegx: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>]).{8,}$/ // 대소문자, 숫자, 특수기호를 사용하여 8자 이상
            },
            p_pwd_confirm: {
                equalTo: "#p_pwd"
            },
            p_company_nm: {
                maxlength: 50
            },
            p_man_nm: {
                maxlength: 10
            },
            p_man_hp: {
                phoneRegx: /^(010|011)\d{3,4}\d{4}$/
            },
            p_man_email: {
                email: true
            }
        },
        messages: {
            p_user_id: {
                required: "아이디를 입력해주세요."
            },
            p_pwd: {
                required: "비밀번호를 입력해주세요.",
                minlength: "비밀번호는 최소 {0}자 이상이어야 합니다.",
                maxlength: "비밀번호는 최대 {0}자 이하이어야 합니다."
            },
            p_pwd_confirm: {
                required: "비밀번호를 입력해주세요.",
                equalTo: "입력한 비밀번호가 서로 일치하지 않습니다."
            },
            p_company_nm: {
                required: "회사/단체명을 입력해주세요.",
                maxlength: "회사/단체명은 최대 {0}자 이하이어야 합니다."
            },
            p_man_nm: {
                required: "담당자 이름을 입력해주세요.",
                maxlength: "담당자 이름은 최대 {0}자 이하이어야 합니다."
            },
            p_man_hp: {
                required : "담당자 연락처를 입력해주세요."
            },
            p_man_email: {
                required: "담당자 이메일을 입력해주세요.",
                email: "이메일 형식을 확인해주세요."
            },
            p_svc_check: {
                required: "(필수) 항목을 모두 동의해 주세요."
            },
            p_pers_check: {
                required: "(필수) 항목을 모두 동의해 주세요."
            },
            p_age_ck: {
                required: "(필수) 항목을 모두 동의해 주세요."
            }
        },
        errorPlacement: function (error, element) {
            const name = $(element).attr("name");

            if (!error.text()) {
                return;
            }

            // checkbox 공통 에러 메시지
            if (['p_svc_check', 'p_pers_check', 'p_age_ck'].includes(name)) {
                $('#p_check_message').text(error.text());
            }

            $(`#${name}_message`).text(error.text());
        },
        success: function (label, element) {
            const name = $(element).attr("name");

            if (name === "p_user_id") {

                if (valiables.confirmId !== $('#p_user_id').val()) {
                    $("#p_user_id_message").text("아이디 중복체크를 해주세요.");
                    valiables.isCheck = false;
                    return;
                }

                $("#p_user_id_message").text("사용가능한 아이디입니다.");
            }

            $(`#${name}_message`).text('');
        },
        submitHandler: function() {
            userJoinHandler.join();
        }
    });
};
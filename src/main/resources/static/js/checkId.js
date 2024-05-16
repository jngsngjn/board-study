let duplicateChecked = false;

function checkDuplicate() {
    const loginId = $("#loginId").val().trim();
    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");

    if (loginId === "") {
        alert("아이디를 입력해주세요.");
        duplicateChecked = false;
        return;
    }

    // 영문 6자리 아이디 검사
    const idRegex = /^[a-zA-Z]{6,15}$/;
    if (!idRegex.test(loginId)) {
        alert("아이디는 영문 6자리 이상 15자리 이하로 입력해주세요.");
        duplicateChecked = false;
        return;
    }

    $.ajax({
        url: "/register/check-duplicate-id",
        type: "POST",
        data: {loginId: loginId},
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(response) {
            if (response) {
                if (confirm("사용 가능한 아이디입니다. 사용하시겠습니까?")) {
                    // 사용 버튼을 클릭한 경우
                    $("#loginId").prop("readonly", true);
                    $("#duplicateCheckButton").hide();
                    $("#changeButton").show();
                    duplicateChecked = true;
                }
            } else {
                alert("이미 사용 중인 아이디입니다.");
                duplicateChecked = false;
            }
        },
        error: function() {
            alert("중복 확인 중 오류가 발생했습니다.");
            duplicateChecked = false;
        }
    });
}

function validateForm() {
    if (!duplicateChecked) {
        alert("아이디 중복 확인을 완료해 주세요.");
        return false;
    }
    return true;
}

function changeLoginId() {
    $("#loginId").prop("readonly", false);
    $("#duplicateCheckButton").show();
    $("#changeButton").hide();
    duplicateChecked = false;
}
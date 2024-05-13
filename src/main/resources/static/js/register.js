var duplicateChecked = false;

function checkDuplicate() {
    var loginId = $("#loginId").val().trim();
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    if (loginId === "") {
        alert("아이디를 입력해주세요.");
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
                    // 사용 버튼을 클릭한 경우 추가 작업 수행
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

$(document).ready(function() {
    $("#sendVerificationEmailBtn").click(function() {
        var email = $("#email").val();
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            type: "POST",
            url: "/send-verification-email",
            data: {
                email: email
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
                if (response.includes("인증 이메일이 전송되었습니다.")) {
                    $("#verificationMessage").text(response);
                } else {
                    $("#verificationMessage").text("인증 이메일 전송에 실패했습니다.");
                }
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
            }
        });
    });
});
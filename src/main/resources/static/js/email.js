$(document).ready(function() {
    $("#sendVerificationEmailBtn").click(function() {
        const email = $("#email").val();

        if (email === "") {
            alert("이메일을 입력해 주세요.")
            return;
        }

        const csrfToken = $("meta[name='_csrf']").attr("content");
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            type: "POST",
            url: "/send-verification-email",
            data: {
                email: email
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        })
            .done(function() {
                $("#verificationMessage").text("인증 메일이 전송되었습니다.");
            })
            .fail(function(xhr) {
                if (xhr.status === 400) {
                    $("#verificationMessage").text("잘못된 이메일 주소입니다.");
                } else {
                    $("#verificationMessage").text("인증 메일 전송에 실패했습니다.");
                }
            });
    });
});
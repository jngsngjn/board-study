$(document).ready(function() {
    $("#sendVerificationEmailBtn").click(function() {
        const email = $("#email").val();
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
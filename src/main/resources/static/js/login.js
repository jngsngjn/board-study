$(document).ready(function() {
    $("#loginForm").on("submit", function(event) {
        const username = $("#username").val().trim();
        const password = $("#password").val().trim();

        if (username === "" || password === "") {
            alert("아이디와 비밀번호를 입력해주세요.");
            event.preventDefault(); // 폼 제출 중단
            return false;
        }
    });
});
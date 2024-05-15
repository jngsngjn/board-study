document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');

    loginForm.addEventListener('submit', function(event) {
        const loginId = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();
        const emptyErrorDiv = document.getElementById('emptyError');
        const loginErrorDiv = document.getElementById('loginError');

        if (loginId === "" || password === "") {
            event.preventDefault(); // 폼 제출 막기
            emptyErrorDiv.textContent = '아이디와 비밀번호를 입력해 주세요.';
            if (loginErrorDiv) {
                loginErrorDiv.style.display = 'none';
            }
        }
    });
});
function validateForm() {
    var loginId = document.getElementById('username').value.trim();
    var password = document.getElementById('password').value.trim();
    var errorDiv = document.getElementById('inputError');
    var serverErrorDiv = document.getElementById('serverError');

    if (loginId === "" || password === "") {
        errorDiv.textContent = '아이디와 비밀번호를 입력해 주세요.';
        if (serverErrorDiv) {
            serverErrorDiv.style.display = 'none'; // serverError 숨김
        }
        return false;
    }
    return true;
}
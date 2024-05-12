// 로그인 모달 시작
var loginModal = document.getElementById("loginModal");
var loginBtn = document.getElementById("loginBtn");
var loginClose = document.getElementsByClassName("close")[0];

// 사용자가 버튼을 클릭하면 모달 열기
loginBtn.onclick = function() {
    loginModal.style.display = "block";
}

// 사용자가 <span> (x)를 클릭하면 모달 닫기
loginClose.onclick = function() {
    loginModal.style.display = "none";
}
// 로그인 모달 끝

// 회원가입 모달 시작
var registerModal = document.getElementById("registerModal");
var registerBtn = document.getElementById("registerBtn");
var registerClose = document.getElementsByClassName("close")[1];

registerBtn.onclick = function() {
    registerModal.style.display = "block";
}

registerClose.onclick = function() {
    registerModal.style.display = "none";
}
// 회원가입 모달 끝



// 사용자가 모달 바깥을 클릭하면 모달 닫기 (공통)
window.onclick = function(event) {
    if (event.target == loginModal) {
        loginModal.style.display = "none";
    }

    if (event.target == registerModal) {
        registerModal.style.display = "none";
    }
}
// 사용자 로그아웃 자동 설정
(function() {
    var autoLogoutTimer;

    function resetTimer() {
        clearTimeout(autoLogoutTimer);
        autoLogoutTimer = setTimeout(logoutUser, 15 * 60 * 1000); // 15분 설정
    }

    function logoutUser() {
        localStorage.removeItem('jwt'); // JWT 토큰 삭제
        window.location.href = '/home'; // 홈 화면으로 자동 리다이렉션
    }

    // 사용자의 활동 감지
    document.onload = resetTimer;
    document.onmousemove = resetTimer;
    // 터치를 동작으로 할 때
    document.onkeypress = resetTimer;
    // 키보드의 동작으로 할 때
    // document.onkeydown = resetTimer();

    // 초기 타이머 설정
    resetTimer();
})();

document.addEventListener('DOMContentLoaded', () => {
    // 로그인 버튼에 이벤트 리스너 추가
    document.querySelector('.login').addEventListener('click', () => {
        // 로그인 페이지로 리다이렉트
        window.location.href = '/login';
    });

    // 회원가입 버튼에 이벤트 리스너 추가
    document.querySelector('.register').addEventListener('click', () => {
        // 회원가입 페이지로 리다이렉트
        window.location.href = '/register';
    });
});
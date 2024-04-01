document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

    const userId = document.getElementById('userId').value;
    const password = document.getElementById('password').value;

    fetch('/v1/user/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userId, password })
    })
        .then(response => {
            if (!response.ok) throw new Error('로그인 실패');
            return response.json();
        })
        .then(({ token }) => {
            localStorage.setItem('jwtToken', token);
            // jwtToken 정보 확인
            console.log(localStorage.getItem('jwtToken'));
            // 로그인 성공 후 리다이렉션
            window.location.href = "/home";
        })
        .catch(error => console.error('로그인 에러:', error));
});

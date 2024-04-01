document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 폼 기본 제출 동작 방지

    const userId = document.getElementById('userId').value;
    const password = document.getElementById('password').value;

    const data = { userId, password };

    fetch('/v1/user/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) throw new Error('로그인 실패');
            return response.json();
        })
        .then(data => {
            // 로그인 성공, JWT 토큰 저장
            localStorage.setItem('jwtToken', data.token);
            console.log(localStorage.getItem('jwtToken'));
            alert('로그인 성공');
            // 추가 동작, 예를 들어 메인 페이지로 리다이렉션
            window.location.href = "/home";
        })
        .catch(error => {
            alert(error.message);
        });
});
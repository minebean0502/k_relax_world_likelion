Kakao.init('7f4c34ed8e44f07b115d63e0f7a0d041');

document.getElementById('logoutButton').addEventListener('click', function () {
    const jwtToken = localStorage.getItem('jwtToken');
    const kakaoAccessToken = localStorage.getItem('kakaoAccessToken');

    // JWT 토큰을 사용하는 경우 로컬 스토리지에서 제거하고 리디렉션
    if (jwtToken) {
        localStorage.removeItem('jwtToken');
    }

    // Kakao 로그인을 사용하는 경우
    if (kakaoAccessToken) {
        console.log(kakaoAccessToken)
        // 카카오 로그아웃 요청
        $.ajax({
            type: "POST",
            url: "https://kapi.kakao.com/v1/user/logout",
            headers: {
                "Authorization": "Bearer " + kakaoAccessToken
            },
            success: function (response) {
                console.log('카카오 로그아웃 완료');
                // 로컬 스토리지에서 카카오 액세스 토큰 제거
                localStorage.removeItem('kakaoAccessToken');
                // 로그아웃 후 /login 페이지로 리다이렉션
                setTimeout(function () {
                    window.location.href = '/login';
                }, 3000);
            },
            error: function (xhr, status, error) {
                console.error('카카오 로그아웃 실패');
                alert('로그아웃에 실패했습니다.');
                // 실패 시에도 토큰 삭제 후 리다이렉션 처리할 수 있도록 선택 가능
                setTimeout(function () {
                    localStorage.removeItem('kakaoAccessToken');
                    window.location.href = '/login';
                }, 2000);
            }
        })
        ;
    } else if (!jwtToken) {
        // JWT 토큰도, 카카오 액세스 토큰도 없는 경우
        window.location.href = '/login';
    }
});
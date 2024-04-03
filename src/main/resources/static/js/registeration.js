document.addEventListener('DOMContentLoaded', function () {
    const signupButton = document.getElementById('signupButton');
    const form = document.getElementById('signupForm');

    signupButton.addEventListener('click', function () {
        // 비밀번호 일치 확인
        const password = document.getElementById('password').value;
        const passwordCheck = document.getElementById('passwordCheck').value;

        if (password !== passwordCheck) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }

        if (form.checkValidity()) {
            const formData = new FormData(form);
            const queryString = new URLSearchParams(formData).toString();

            fetch('/v1/user/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: queryString
            })
                .then(response => {
                    if (response.ok) {
                        // 성공했다면 JSON이 아닌 다른 페이지로 리다이렉션될 가능성이 있습니다.
                        // 따라서 Content-Type 헤더를 검사해 JSON인 경우만 파싱을 시도합니다.
                        const contentType = response.headers.get('Content-Type');
                        if (contentType && contentType.includes('application/json')) {
                            return response.json();
                        } else {
                            // JSON이 아닌 경우 성공으로 간주하고 홈으로 리다이렉트합니다.
                            window.location.href = "/home";
                            return null;
                        }
                    } else {
                        // 응답이 'ok'가 아닐 경우 에러 처리를 합니다.
                        throw new Error('회원가입 실패: ' + response.statusText);
                    }
                })
                .then(data => {
                    if (data) {
                        // JSON 응답을 처리합니다.
                        // 예: data.message 또는 다른 필드에 따라 처리 로직을 추가할 수 있습니다.
                    }
                })
                .catch(error => {
                    alert("회원가입에 실패했습니다: " + error.message); // 에러 메시지 표시
                });
        } else {
            form.reportValidity(); // 브라우저 내장 폼 검증 실행
        }
    });
});

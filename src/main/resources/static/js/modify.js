document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('modifyPasswordForm');

    form.onsubmit = function (e) {
        e.preventDefault(); // 폼 기본 제출을 방지합니다.

        const userId = document.getElementById('userId').value;
        const phoneNumber = document.getElementById('phoneNumber').value;
        const newPassword = document.getElementById('newPassword').value;

        const requestData = {
            userId: userId,
            phoneNumber: phoneNumber,
            newPassword: newPassword
        };

        fetch('/v1/user/idpw/modify', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('네트워크 응답이 정상적이지 않습니다.');
                }
                return response.text(); // 서버가 문자열을 반환한다고 가정
            })
            .then(data => {
                alert(data); // 결과를 alert로 표시
            })
            .catch(error => {
                alert("비밀번호 수정 오류: " + error.message); // 에러 메시지 표시
            });
    };
});

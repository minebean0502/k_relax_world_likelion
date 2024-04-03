document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('pwFindForm');

    form.onsubmit = function (e) {
        e.preventDefault(); // 기본 제출 폼 방지

        const userId = document.getElementById('userId').value;
        const phoneNumber = document.getElementById('phoneNumber').value;

        fetch('/v1/user/idpw/pw/find?userId=' + encodeURIComponent(userId) + '&phone_number=' + encodeURIComponent(phoneNumber), {
            method: 'GET'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('네트워크 응답이 정상적이지 않습니다.');
                }
                return response.text();
            })
            .then(data => {
                if (data === "비밀번호를 재설정해주세요") {
                    window.location.href = '/modify'; // 리디렉트
                } else {
                    alert(data); // 에러 메세지 반환
                }
            })
            .catch(error => {
                alert("오류 발생: " + error.message);
            });
    };
});

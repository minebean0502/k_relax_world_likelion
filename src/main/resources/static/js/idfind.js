document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('idFindForm');

    form.onsubmit = function (e) {
        e.preventDefault(); // 폼 기본 제출을 방지합니다.

        const phoneNumber = document.getElementById('phoneNumber').value;

        fetch('/v1/user/idpw/id/find?phone_number=' + encodeURIComponent(phoneNumber), {
            method: 'GET'
        })
            .then(response => response.text()) // 서버가 문자열을 반환한다고 가정
            .then(data => {
                alert(data); // 결과를 alert로 표시
            })
            .catch(error => {
                alert("오류 발생: " + error.message); // 에러 메시지 표시
            });
    };
});

document.addEventListener('DOMContentLoaded', (event) => {
    document.querySelectorAll('.waste-item').forEach(item => {
        item.addEventListener('click', function() {
            const wasteId = this.getAttribute('data-id');
            // 폼을 동적으로 생성하고 추가하는 로직
            createAndShowForm(wasteId);
        });
    });
});

function createAndShowForm(wasteId) {
    // 폼 생성 및 페이지에 추가하는 코드
    // ...
}

function submitForm(formElement) {
    // 폼 데이터를 서버로 비동기로 보내는 AJAX 요청
    // ...
}
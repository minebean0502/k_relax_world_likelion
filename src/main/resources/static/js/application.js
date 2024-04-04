document.addEventListener('DOMContentLoaded', function() {
    fetchApplications();
    // 한국 시간대에 맞춰 현재 시간을 표시
    setInterval(() => {
        document.getElementById('current-time').textContent = new Date().toLocaleString('ko-KR', { timeZone: 'Asia/Seoul' });
    }, 1000); // 매초마다 업데이트
});

// 백엔드에서 신청 내역을 가져오는 함수
function fetchApplications() {
    const token = localStorage.getItem('jwtToken');
    console.log('Token:', token); // 토큰 확인 로그
    if (!token) {
        console.error('No token found');
        return;
    }

    fetch('/v1/user/waste/show-applications', {
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
        .then(response => {
            console.log('Response:', response); // 응답 확인 로그
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(applications => {
            console.log('Applications:', applications); // 데이터 확인 로그
            const listContainer = document.getElementById('applications-list');
            if (!listContainer) {
                console.error('List container not found'); // 컨테이너 요소 확인 로그
                return;
            }
            listContainer.innerHTML = ''; // 기존 목록 초기화
            applications.forEach(application => {
                const applicationElement = createApplicationElement(application);
                listContainer.appendChild(applicationElement);
            });
        })
        .catch(error => console.error('Error fetching applications:', error));
}

// 신청 내역을 위한 HTML 요소를 생성하는 함수
function createApplicationElement(application) {
    const container = document.createElement('div');
    container.className = 'application';
    container.innerHTML = `
        <div class="application-date">신청 날짜: ${application.applyDate}</div>
        <div class="item-descriptions">신청 품목: ${application.itemDescriptions}</div>
        <div class="quantity">수량: ${application.quantity}</div>
        <div class="total-price">총 금액: ${application.totalPrice}</div>
        <div class="due-date">배출 예정일: ${application.dueDate}</div>
        <div class="location">배출 장소: ${application.location}</div>
        <div class="payment">결제 상태: ${application.payment}</div>
    `;
    return container;
}

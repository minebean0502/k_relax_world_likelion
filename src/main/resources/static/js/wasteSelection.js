document.addEventListener('DOMContentLoaded', function() {

    let tempFormId = null;  // 임시 폼 ID를 저장할 변수
    let totalPrice = 0; // 총액을 저장할 변수

    // 서버에서 폐기물 목록을 가져오는 함수
    function loadWasteItems() {
        fetch('/v1/user/waste/wastes', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken') // JWT 토큰을 헤더에 추가
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        }).then(data => {
            const wasteList = document.getElementById('wasteList');
            wasteList.innerHTML = ''; // 목록 초기화
            data.forEach(waste => {
                const listItem = document.createElement('li');
                listItem.className = 'waste-item';
                listItem.setAttribute('data-id', waste.id);
                listItem.textContent = `${waste.description} - ${waste.price}원`;
                listItem.addEventListener('click', function() {
                    showWasteDetailForm(waste.id, waste.description, waste.price);
                });
                wasteList.appendChild(listItem);
            });
        }).catch(error => console.error('Could not load waste items:', error));
    }

    // 폐기물 상세 정보 입력 폼을 표시하는 함수
    function showWasteDetailForm(wasteId, description, price) {
        const form = document.getElementById('waste-detail-form');
        form.style.display = 'block';
        form.setAttribute('data-selected-waste-id', wasteId);
        form.setAttribute('data-selected-waste-description', description);
        form.setAttribute('data-selected-waste-price', price);
    }

    // 장바구니에 추가하는 함수
    function updateCart(description, quantity, date, price) {
        const cartList = document.getElementById('cart-list');
        const cartItem = document.createElement('li');
        cartItem.textContent = `날짜: ${date}, 상품명: ${description}, 갯수: ${quantity}, 가격: ${price * quantity}원`;
        cartList.appendChild(cartItem);

        totalPrice += price * quantity;
        document.getElementById('total-price').textContent = `총액: ${totalPrice}원`;
    }

    // '담기' 버튼 클릭 이벤트 핸들러
    document.getElementById('add-to-cart').addEventListener('click', function() {
        // 입력 받을 정보들
        const form = document.getElementById('waste-detail-form');
        const selectedWasteId = form.getAttribute('data-selected-waste-id');
        const description = form.getAttribute('data-selected-waste-description');
        const price = parseInt(form.getAttribute('data-selected-waste-price'), 10);

        // 수량 정보
        const quantityInput = document.getElementById('quantity');
        const quantity = parseInt(quantityInput.value, 10); // 수량 검증용

        // 날짜 정보
        const dateInput = document.getElementById('date');
        const date = dateInput.value; // 날짜 검증용

        // 오늘 날짜
        const today = new Date();
        today.setHours(0, 0, 0, 0); // 오늘 날짜의 시간을 00:00:00으로 설정
        const selectedDate = new Date(date); // 사용자가 고른 날짜

        // 너무 먼 미래를 설정하지 않도록 해야함
        const limitMonth = new Date(today);
        limitMonth.setMonth(limitMonth.getMonth() + 6); // 6개월 뒤로 설정하겠음

        // 수량 필드와 날짜 필드의 유효성을 검사합니다.
        if (!quantityInput.checkValidity()) {
            quantityInput.reportValidity(); // 유효하지 않으면 브라우저 기본 에러 메시지 표시
            return; // 함수 종료
        }

        if (!dateInput.checkValidity()) {
            dateInput.reportValidity(); // 유효하지 않으면 브라우저 기본 에러 메시지 표시
            return; // 함수 종료
        }

        // 수량 검증
        if (isNaN(quantity) || quantity < 1) {
            // alert('수량은 1개 이상 입력해주세요.');
            quantityInput.focus();
            return;
        }

        // 날짜 검증
        if (!date) {
            document.getElementById('dateWarning').textContent = '유효한 날짜를 선택해주세요.';
            document.getElementById('dateWarning').style.display = 'inline';
            dateInput.focus();
            return;
        } else if (selectedDate < today) {
            document.getElementById('dateWarning').textContent = '오늘 이후의 날짜만 가능합니다.';
            document.getElementById('dateWarning').style.display = 'inline';
            dateInput.focus();
            return;
        } else if (selectedDate > limitMonth) {
            document.getElementById('dateWarning').textContent = '6개월 이내의 날짜를 선택해주세요.';
            document.getElementById('dateWarning').style.display = 'inline';
            dateInput.focus();
            return;
        } else {
            document.getElementById('dateWarning').style.display = 'none';
        }

        // 모든 검증까지 끝나면, 사용자 신청 정보를 서버로 전송
        addToCart(selectedWasteId, quantity, date, description, price);
    });

    // 서버에 폐기물 정보를 전송하는 함수
    function addToCart(wasteId, quantity, date, description, price) {
        fetch('/v1/user/waste/apply/add-waste-item', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            },
            body: JSON.stringify({
                formId: tempFormId,
                wasteId: wasteId,
                quantity: quantity,
                date: date
            }),
        }).then(response => {
            if (!response.ok) {
                throw new Error('Failed to add waste to cart');
            }
            return response.json();
        }).then(response => {
            updateCart(description, quantity, date, price);
            document.getElementById('waste-detail-form').style.display = 'none';
            alert('폐기물이 장바구니에 추가되었습니다.');
        }).catch(error => {
            console.error('Error adding waste to cart:', error);
            alert('폐기물 추가 중 오류가 발생했습니다.');
        });
    }

    // '취소' 버튼 클릭 이벤트 핸들러
    document.getElementById('cancel').addEventListener('click', function() {
        document.getElementById('waste-detail-form').style.display = 'none';
    });

    // 페이지 로드 시 임시 폼을 생성하고 폐기물 목록을 로드하는 로직
    function createTempForm() {
        fetch('/v1/user/waste/apply', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken'),
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error('Failed to create temp form');
            }
            return response.json();
        }).then(response => {
            tempFormId = response.id;  // 임시 폼 ID 저장
            console.log('Temporary form created with ID:', tempFormId);
            loadWasteItems();  // 폐기물 목록을 불러옵니다.
        }).catch(error => {
            console.error('Error creating temporary form:', error);
        });
    }

    // 탭 버튼 클릭 이벤트 핸들러 설정
    document.querySelectorAll('.tab-button').forEach(button => {
        button.addEventListener('click', function() {
            const tabIndex = this.getAttribute('data-tab-index');
            showItems(tabIndex);
        });
    });

    // 탭 버튼 클릭 이벤트를 처리하는 함수
    function showItems(tabIndex) {
        console.log(tabIndex); // 콘솔에 tabIndex 값 출력하여 확인

        fetch(`/v1/user/waste/${tabIndex}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Failed to load items for tab ${tabIndex}`);
                }
                return response.json();
            })
            .then(items => {
                const wasteList = document.getElementById('wasteList');
                wasteList.innerHTML = ''; // 이전에 로드된 아이템들을 초기화
                items.forEach(item => {
                    const listItem = document.createElement('div');
                    listItem.className = 'waste-item';
                    listItem.innerHTML = `
        <img src="${item.imageUrl || '/images/apartment.jpg'}" alt="${item.description}" />
        <h3>${item.description}</h3>
        <p>${item.price}원</p>
    `;
                    listItem.onclick = () => openSelectionContainer(item);
                    wasteList.appendChild(listItem);
                });
            })
            .catch(error => console.error('Error loading items:', error));
    }

    // 아이템 클릭 시 호출될 함수
    function openSelectionContainer(item) {
        const selectionContainer = document.getElementById('waste-selection-container');
        selectionContainer.innerHTML = `
            <div class="item-options">
                <h2>${item.description}</h2>
                <p>가격: ${item.price}원</p>
                <button onclick="closeSelectionContainer()">닫기</button>
            </div>
        `;
        selectionContainer.style.display = 'block';
        document.getElementById('modal-backdrop').style.display = 'block';
    }

    function closeSelectionContainer() {
        document.getElementById('waste-selection-container').style.display = 'none';
        document.getElementById('modal-backdrop').style.display = 'none';
    }

    // 기본 탭 로드
    // showItems(1);
    // 임시 폼 작성 시작
    createTempForm();
});

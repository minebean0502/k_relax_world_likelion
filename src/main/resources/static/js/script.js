// 로그인 버튼 클릭 이벤트 처리
document.querySelector('.login').addEventListener('click', function(e) {
    e.preventDefault();
    // /login 페이지로 이동
    window.location.href = '/login';
});

// 회원가입 버튼 클릭 이벤트 처리
document.querySelector('.register').addEventListener('click', function(e) {
    e.preventDefault();
    // /register 페이지로 이동
    window.location.href = '/register';
});

document.getElementById('applicationCard').addEventListener('click', function() {
    alert('홈 이동');
    window.location.href = '/waste/applications';
});

document.getElementById('recycleCard').addEventListener('click', function() {
    alert('재활용 이동');
    window.location.href = '/waste/apply';
});
document.getElementById('btnCheckMore').addEventListener('click', function() {
    alert('자세히 보기 클릭됨');
});

document.getElementById('btnNewRequest').addEventListener('click', function() {
    alert('신청하기 클릭됨');
});
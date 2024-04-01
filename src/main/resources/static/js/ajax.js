// ajax 전역 설정
$.ajaxSetup({
    beforeSend: function(xhr) {
        const jwtToken = localStorage.getItem('jwtToken');

        if (localStorage.getItem('jwtToken')) {
            xhr.setRequestHeader('Authorization', `Bearer ${jwtToken}`);
        }
    }
});
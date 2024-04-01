document.getElementById('logoutButton').addEventListener('click', function() {
    localStorage.removeItem('jwtToken');
    window.location.href = "/login";
});
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('signupForm');
    form.onsubmit = function (e) {
        e.preventDefault(); // Prevent the form from submitting the traditional way

        const formData = new FormData(form);
        const jsonData = Object.fromEntries(formData);

        fetch('/v1/user/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(jsonData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('회원가입 실패');
                }
                return response.json();
            })
            .then(data => {
                window.location.href = "/home"; // Redirect to home on success
            })
            .catch(error => {
                window.location.href = "/register"; // Redirect back to register on failure
            });
    };
});
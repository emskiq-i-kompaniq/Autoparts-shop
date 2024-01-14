let loginForm = document.getElementsByClassName("login-form")[0];
loginForm.addEventListener("submit", function (event) {
    event.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const userData = {
        email: email,
        password: password
    };

    fetch('http://localhost:8080/api/v1/auth/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }
            return response.json()
        })
        .then(data => {
            localStorage.setItem('accessToken', data.accessToken);
            window.location.href = "../home/home-page.html"
        })
        .catch(error => {
            console.error('Error during registration:', error);
        });
});
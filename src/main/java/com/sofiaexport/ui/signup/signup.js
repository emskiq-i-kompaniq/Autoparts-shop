let registerForm = document.getElementsByClassName("register-form")[0];
registerForm.addEventListener("submit", function (event) {
    event.preventDefault();
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const userData = {
        username: username,
        email: email,
        password: password
    };

    fetch('http://localhost:8080/api/v1/register', {
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

        })
        .then(data => {
            console.log('Registration successful');
        })
        .catch(error => {
            console.error('Error during registration:', error);
        });
});
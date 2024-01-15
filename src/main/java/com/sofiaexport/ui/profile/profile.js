document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('accessToken');

    fetch(`http://localhost:8080/api/v1/user/${localStorage.getItem('userId')}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }
            return response.json()
        })
        .then(data => {
            document.getElementById('profilePicture').src = 'https://www.gravatar.com/avatar/' + md5(data.email);
            document.getElementById('email').textContent = `Email: ${data.email}`;
            document.getElementById('name').textContent = data.name;
        })
        .catch(error => {
            console.error('Error during registration:', error);
        });
});

function md5(value) {
    const hash = CryptoJS.MD5(value);
    return hash.toString(CryptoJS.enc.Hex);
}
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
            return response.json();
        })
        .then(data => {
            document.getElementById('profilePicture').src = 'https://www.gravatar.com/avatar/' + md5(data.email);
            document.getElementById('email').textContent = `Email: ${data.email}`;
            document.getElementById('name').textContent = data.name;
        })
        .catch(error => {
            console.error('Error during fetching user profile:', error);
        });

    fetch(`http://localhost:8080/api/v1/user/${localStorage.getItem('userId')}/orders`, {
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
            return response.json();
        })
        .then(orderHistory => {
            const ordersList = document.getElementById('ordersList');

            if (orderHistory.length === 0) {
                ordersList.innerHTML = '<p>No order history available.</p>';
            } else {
                orderHistory.forEach(order => {
                    const orderHtml = `
                    <li class="order">
                        <p>Order Sum: ${order.sum}</p>
                        <p>Order Status: ${order.orderStatus}</p>
                    </li>
                `;
                    ordersList.insertAdjacentHTML('beforeend', orderHtml);
                });
            }
        })
        .catch(error => {
            console.error('Error during fetching user order history:', error);
        });
});

function md5(value) {
    const hash = CryptoJS.MD5(value);
    return hash.toString(CryptoJS.enc.Hex);
}

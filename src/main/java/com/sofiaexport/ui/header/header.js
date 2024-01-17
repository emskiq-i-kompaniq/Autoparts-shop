function checkToken() {
    var token = localStorage.getItem('accessToken');
    var loginButton = document.getElementById('login-btn');
    var cartButton = document.getElementById('cart-btn');
    var profileButton = document.getElementById('profile-btn');
    var logoutButton = document.getElementById('logout-btn');

    if (token == null) {
        // User is not logged in, show the login button and hide the profile button
        loginButton.style.display = 'inline-block';
        cartButton.style.display = 'none';
        profileButton.style.display = 'none';
        logoutButton.style.display = 'none';
    } else {
        // User is logged in, hide the login button and show the profile button
        loginButton.style.display = 'none';
        cartButton.style.display = 'inline-block';
        profileButton.style.display = 'inline-block';
        logoutButton.style.display = 'inline-block';
    }

    logoutButton.addEventListener('click', function () {
        localStorage.removeItem('accessToken');
        window.location.href = "../home/home-page.html";
    });
}
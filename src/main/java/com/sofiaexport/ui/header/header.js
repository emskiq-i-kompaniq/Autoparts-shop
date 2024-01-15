function checkToken() {
    var token = localStorage.getItem('accessToken');
    var loginButton = document.getElementById('login-btn');
    var profileButton = document.getElementById('profile-btn');

    if (token == null) {
        // User is not logged in, show the login button and hide the profile button
        loginButton.style.display = 'inline-block';
        profileButton.style.display = 'none';
    } else {
        // User is logged in, hide the login button and show the profile button
        loginButton.style.display = 'none';
        profileButton.style.display = 'inline-block';
    }
}
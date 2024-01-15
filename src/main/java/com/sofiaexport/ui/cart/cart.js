document.addEventListener("DOMContentLoaded", function () {
    var userId = localStorage.getItem("userId");

    if (!userId) {
        console.error("User ID not found in local storage.");
        return;
    }

    var token = localStorage.getItem('accessToken');
    var authToken = `Bearer ${token}`;

    fetch("http://localhost:8080/api/v1/user/" + userId + "/order", {
        headers: {
            'Authorization': authToken
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error fetching shopping cart items");
            }
            return response.json();
        })
        .then(data => {
            var cartItemsContainer = document.getElementById("cartItems");

            if (data.autoparts.length === 0) {
                var emptyCartMessage = `<p>Your cart is empty.</p>`;
                cartItemsContainer.innerHTML = emptyCartMessage;
            } else {
                var sumHtml = `<p class="sum"><strong>Total Sum:</strong> ${data.sum}</p>`;
                cartItemsContainer.insertAdjacentHTML('beforeend', sumHtml);

                data.autoparts.forEach(function (autoPart) {
                    var autoPartHtml = `
                        <li class="autoPart">
                            <div class="autoPartInfo">
                                <p class="autoPartTitle"><strong>${autoPart.brand} - ${autoPart.partType}</strong></p>
                                <p class="autoPartPrice">Price: ${autoPart.price}</p>
                            </div>
                        </li>
                    `;
                    cartItemsContainer.insertAdjacentHTML('beforeend', autoPartHtml);
                });

                var checkoutButtonHtml = `<button id="checkoutButton">Checkout</button>`;
                cartItemsContainer.insertAdjacentHTML('beforeend', checkoutButtonHtml);

                var checkoutButton = document.getElementById('checkoutButton');
                checkoutButton.addEventListener('click', function () {
                    checkoutOrder(data.id);
                });
            }
        })
        .catch(error => {
            console.error(error);
        });

    function checkoutOrder(orderId) {
        fetch(`http://localhost:8080/api/v1/order/${orderId}/checkout`, {
            method: 'PUT',
            headers: {
                'Authorization': authToken
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error during checkout");
                }

                window.location.href = "../home/home-page.html"
            })
            .catch(error => {
                console.error('Error during checkout:', error);
            });
    }
});

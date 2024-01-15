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
        })
        .catch(error => {
            console.error(error);
        });
});

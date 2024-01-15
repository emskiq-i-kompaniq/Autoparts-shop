document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const partId = urlParams.get('id');

    function showCustomConfirm(message, button1Text, button2Text) {
        const confirmationModal = document.createElement('div');
        confirmationModal.innerHTML = `
            <div style="background-color: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); overflow: hidden; width: 300px; max-width: 100%; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%);">
                <div style="padding: 20px;">
                    <p style="margin: 0; font-size: 16px; color: #333;">${message}</p>
                </div>
                <div style="display: flex; justify-content: space-between; padding: 10px; background-color: #f4f4f4;">
                    <button id="confirmButton" style="flex: 1; margin-right: 5px; padding: 10px; background-color: #007bff; color: #fff; border: none; border-radius: 5px; cursor: pointer;">${button1Text}</button>
                    <button id="cancelButton" style="flex: 1; margin-left: 5px; padding: 10px; background-color: #ccc; color: #fff; border: none; border-radius: 5px; cursor: pointer;">${button2Text}</button>
                </div>
            </div>
        `;
        document.body.appendChild(confirmationModal);

        return new Promise((resolve) => {
            document.getElementById('confirmButton').addEventListener('click', () => {
                document.body.removeChild(confirmationModal);
                resolve(true);
            });

            document.getElementById('cancelButton').addEventListener('click', () => {
                document.body.removeChild(confirmationModal);
                resolve(false);
            });
        });
    }

    fetch(`http://localhost:8080/api/v1/autoparts/${partId}`)
        .then(response => response.json())
        .then(data => {
            const imageContainer = document.getElementById('image-container');
            const image = document.createElement('img');
            image.src = 'https://media.istockphoto.com/id/1034249292/photo/set-of-car-parts-isolated-on-white-background-3d.jpg?s=2048x2048&w=is&k=20&c=a2hHU43ASYPEniihEyVGBMFrN6KGqEYWxb2mb6CLDMo=';
            image.alt = `Image of ${data.brand}`;
            imageContainer.appendChild(image);

            document.getElementById('brand').innerText = `${data.brand} ${data.partType}`;
            document.getElementById('description').innerText = `${data.description}`;
            document.getElementById('price').innerText = `Price: ${data.price} €`;
            document.getElementById('compatible-cars').innerText = data.compatibleCarsIds.length > 0 ? `Compatible Cars: ${data.compatibleCarsIds.join(', ')}` : '';

            const countInStockElement = document.getElementById('count-in-stock');
            const addToCartButton = document.getElementById('addToCartButton');

            const isAuthenticated = localStorage.getItem('accessToken') !== null;

            if (isAuthenticated) {
                addToCartButton.innerText = 'Add to Cart';
                addToCartButton.addEventListener('click', function() {
                    addToCartHandler(partId)
                });

                if (data.countInStockItems > 0) {
                    countInStockElement.innerText = `In Stock: ${data.countInStockItems}`;
                    addToCartButton.style.display = 'block';
                } else {
                    countInStockElement.innerText = 'Out of Stock';
                    addToCartButton.style.display = 'none';
                }
            } else {
                addToCartButton.style.display = 'none';
            }
        })
        .catch(error => console.error('Error fetching auto part details:', error));

    function addToCartHandler(autoPartId) {
        const addToCardRequest = {
            userId: localStorage.getItem('userId'),
            autoPartId: autoPartId
        };
        var token = localStorage.getItem('accessToken');
        var authToken = `Bearer ${token}`;

        fetch('http://localhost:8080/api/v1/add-item', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': authToken
            },
            body: JSON.stringify(addToCardRequest)
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }

            return showCustomConfirm('Item added to the cart. What would you like to do next?', 'Proceed to checkout', 'Continue shopping');
        }).then((proceedToCheckout) => {
            if (proceedToCheckout) {
                window.location.href = '../cart/cart.html';
            }
        }).catch(error => {
            console.error('Error while adding item to cart:', error);
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const partId = urlParams.get('id');

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
            addToCartButton.innerText = 'Add to Cart';
            addToCartButton.addEventListener('click', addToCartHandler);

            if (data.countInStockItems > 0) {
                countInStockElement.innerText = `In Stock: ${data.countInStockItems}`;
                addToCartButton.style.display = 'block';
            } else {
                countInStockElement.innerText = 'Out of Stock';
                addToCartButton.style.display = 'none';
            }
        })
        .catch(error => console.error('Error fetching auto part details:', error));

    function addToCartHandler() {
        // Implement the logic to add the item to the cart
        alert('Item added to cart!');
    }
});

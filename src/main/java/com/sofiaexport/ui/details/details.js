document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const partId = urlParams.get('id');

    fetch(`http://localhost:8080/api/v1/autoparts/${partId}`)
        .then(response => response.json())
        .then(data => {
            console.log('Auto Part Data:', data);
            document.getElementById('brand').innerText = `Brand: ${data.brand}`;
            document.getElementById('description').innerText = `Description: ${data.description}`;
            document.getElementById('price').innerText = `Price: ${data.price}`;
        })
        .catch(error => console.error('Error fetching auto part details:', error));
});

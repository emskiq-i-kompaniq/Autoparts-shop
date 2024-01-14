function createPartBox(autoPart) {
    const partBox = document.createElement('div');
    partBox.classList.add('part-box');

    // Customize the content based on your auto part properties
    partBox.innerHTML = `
            <h3>${autoPart.brand}</h3>
            <p>Desc: ${autoPart.description}</p>
            <p>Price: ${autoPart.price}</p>
        `;

    partBox.addEventListener('click', function () {
        window.location.href = `../details/details.html?id=${autoPart.id}`;
    });

    return partBox;
}
document.addEventListener('DOMContentLoaded', function () {
    const partsContainer = document.getElementById('parts-container');

    // Make an HTTP request to your backend to fetch all auto parts
    fetch('http://localhost:8080/api/v1/autoparts')
        .then(response => response.json())
        .then(data => {
            // Iterate through the auto parts and create HTML elements
            data.forEach(autoPart => {
                const partBox = createPartBox(autoPart);
                partsContainer.appendChild(partBox);
            });
        })
        .catch(error => console.error('Error fetching auto parts:', error));

    // Function to create a div element for an auto part


});

function searchParts() {
    const searchInput = document.getElementById('searchInput').value;

    // Make an HTTP request to your backend to fetch auto parts based on the search input
    fetch(`http://localhost:8080/api/v1/autoparts?brand=${searchInput}`)
        .then(response => response.json())
        .then(data => {
            // Clear existing part boxes
            const partsContainer = document.getElementById('parts-container');
            partsContainer.innerHTML = '';

            // Iterate through the auto parts and create HTML elements
            data.forEach(autoPart => {
                const partBox = createPartBox(autoPart);
                partsContainer.appendChild(partBox);
            });
        })
        .catch(error => console.error('Error fetching auto parts:', error));
}
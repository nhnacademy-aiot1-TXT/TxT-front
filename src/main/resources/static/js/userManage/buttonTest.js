// pagination.js

// Function to create pagination buttons
function createPaginationButtons(currentPage, totalPages) {
    const paginationContainer = document.getElementById("buttonTest");

    // Clear existing buttons
    paginationContainer.innerHTML = "";

    // Create and append First Page button
    const firstPageBtn = createButton("First Page", () => loadPage(1));
    paginationContainer.appendChild(firstPageBtn);

    // Create and append Previous Page button
    const prevPageBtn = createButton("Previous Page", () => changePage(-1));
    paginationContainer.appendChild(prevPageBtn);

    // Create and append Next Page button
    const nextPageBtn = createButton("Next Page", () => changePage(1));
    paginationContainer.appendChild(nextPageBtn);

    // Create and append Last Page button
    const lastPageBtn = createButton("Last Page", () => loadPage(totalPages));
    paginationContainer.appendChild(lastPageBtn);
}

// Function to create a button with specified text and click handler
function createButton(text, clickHandler) {
    const button = document.createElement("button");
    button.textContent = text;
    button.addEventListener("click", clickHandler);
    return button;
}

// Function to load a specific page
function loadPage(page) {
    fetch(`http://localhost:8200/api/user/admin/userList/sort/4?page=${page}&size=5`)
        .then(response => response.json())
        .then(data => updateTable(data))
        .catch(error => console.error('Error loading the page:', error));
}

// Function to change page by a specified delta
function changePage(delta) {
    const currentPage = parseInt(document.getElementById("currentPage").textContent);
    const totalPages = parseInt(document.getElementById("totalPages").textContent);

    let newPage = currentPage + delta;
    if (newPage < 1) {
        newPage = 1;
    } else if (newPage > totalPages) {
        newPage = totalPages;
    }
    loadPage(newPage);
}

// Function to update the table with new data
function updateTable(data) {
    const tbody = document.querySelector("#permitUser tbody");
    tbody.innerHTML = ""; // Clear existing rows
    data.forEach(user => {
        const row = document.createElement("tr");
        row.innerHTML = `<td>${user.requestItemId}</td><td>${user.name}</td>`;
        tbody.appendChild(row);
    });
}

// Initially create pagination buttons
const currentPage = parseInt(document.getElementById("currentPage").textContent);
const totalPages = parseInt(document.getElementById("totalPages").textContent);
createPaginationButtons(currentPage, totalPages);

// Initially load the first page
loadPage(currentPage);

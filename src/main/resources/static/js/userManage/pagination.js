document.addEventListener('DOMContentLoaded', function () {
    const apiUrl = '/api/user/admin/userList/sort/4'; // API의 기본 URL
    const pageSize = 5; // 페이지당 표시할 아이템 수
    const container = document.getElementById('pagination-container'); // 페이지네이션을 담을 컨테이너



    function initAndLoadData() {
        fetch(`${apiUrl}?page=1&size=${pageSize}`)
            .then(response => response.json())
            .then(data => {
                if (data && data.totalPages) {
                    createPagination(1, data.totalPages + 1); // 총 페이지 수에 1을 추가하여 버튼 생성
                    updateTable(data.users);
                } else {
                    console.error('No pagination data available');
                }
            })
            .catch(error => console.error('Failed to fetch data', error));
    }

    function updateTable(users) {
        let tableRows = users.map(user => `<tr><td>${user.id}</td><td>${user.name}</td></tr>`).join('');
        document.querySelector('#permitUser tbody').innerHTML = tableRows;
    }

    function createPagination(currentPage, totalPages) {
        container.innerHTML = ''; // 기존 페이지네이션 초기화
        for (let i = 1; i <= totalPages; i++) {
            let button = document.createElement('button');
            button.innerText = i;
            button.className = `page-button ${i === currentPage ? 'active' : ''}`;
            button.addEventListener('click', function () {
                loadPage(i);
            });
            container.appendChild(button);
        }
    }

    function loadPage(pageNumber) {
        fetch(`${apiUrl}?page=${pageNumber}&size=${pageSize}`)
            .then(response => response.json())
            .then(data => {
                if (pageNumber > data.totalPages) {
                    alert("This page does not exist.");
                } else {
                    updateTable(data.users);
                    createPagination(pageNumber, data.totalPages + 1);
                }
            })
            .catch(error => console.error('Failed to fetch data', error));
    }

    initAndLoadData(); // 초기 페이지 데이터 로드
});

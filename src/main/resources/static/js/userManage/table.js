function parseAndInsertUserData(userDataJson) {
    let users;
    try {
        users = JSON.parse(userDataJson);
    } catch (e) {
        console.error('사용자 데이터 파싱 중 오류 발생:', e);
        return;
    }

    const tbody = document.getElementById('permitUser').querySelector('tbody');
    users.forEach(user => {
        const row = tbody.insertRow();

        // 체크박스를 위한 셀 추가
        const checkboxCell = row.insertCell(0);
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.name = 'selectedItems';
        checkbox.value = user.id;
        checkboxCell.appendChild(checkbox);

        // 사용자 ID와 이름 삽입을 위한 셀
        const idCell = row.insertCell(1);
        const nameCell = row.insertCell(2);
        idCell.textContent = user.id;
        nameCell.textContent = user.name;
    });
}


// 데이터 파싱 및 삽입 함수 호출
parseAndInsertUserData(usersData);

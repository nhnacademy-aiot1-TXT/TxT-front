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
        const idCell = row.insertCell(0);
        const nameCell = row.insertCell(1);
        idCell.textContent = user.id;
        nameCell.textContent = user.name;
    });
}

// 데이터 파싱 및 삽입 함수 호출
parseAndInsertUserData(usersData);

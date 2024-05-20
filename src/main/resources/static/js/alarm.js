document.addEventListener("DOMContentLoaded", function() {
    const alarmListContainer = document.getElementById('alarmList');

    async function fetchAndDisplayNotifications() {
        try {



            const option = {
                method : "GET",
                headers : {
                    Authorization: accessToken
                }

            }
            const response = await fetch('http://localhost:8000/api/common/notification', option);



            console.log(accessToken)
            console.log(response);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const notifications = await response.json();

            const add01 = {
                roleId: 1,
                contents: "제발좀 나와줘 이러다 다 죽어",
                time: "2023-05-17T14:30:00" // ISO 8601 형식의 문자열
            };

            const add02 = {
                roleId: 2,
                contents: "Sample content",
                time: "2023-05-17T14:30:00" // ISO 8601 형식의 문자열
            };

            // 올바른 배열 메소드 사용
            notifications.push(add01);
            notifications.push(add02);

            // 시간 기준으로 정렬
            notifications.sort((a, b) => new Date(b.time) - new Date(a.time));

            const alarmListHtml = notifications.map(notification => `
                <li class="mb-2">
                    <a class="dropdown-item border-radius-md">
                        <div class="d-flex py-1">
                            <div class="my-auto">
                                <img src="/img/team-4.jpg" class="avatar avatar-sm me-3">
                            </div>
                            <div class="d-flex flex-column justify-content-center">
                                <h6 class="text-sm font-weight-normal mb-1">
                                    <span class="font-weight-bold">${notification.contents}</span> from vivid
                                </h6>
                                <p class="text-xs text-secondary mb-0">
                                    <i class="fa fa-clock me-1"></i>
                                    ${new Date(notification.time).toLocaleString()}
                                </p>
                            </div>
                        </div>
                    </a>
                </li>
            `).join('');

            alarmListContainer.innerHTML = alarmListHtml;
        } catch (error) {
            console.error('Failed to fetch notifications:', error);
        }
    }

    fetchAndDisplayNotifications();
});

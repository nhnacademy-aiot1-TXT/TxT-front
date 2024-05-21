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

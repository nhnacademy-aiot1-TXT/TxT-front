async function peopleCountCall() {
    const url = "https://contxt.co.kr/api/sensor/people-count";

    const option = {
        method : "GET",
        headers : {
            Authorization : accessToken
        }
    }
    const response= await fetch(url, option);
    return await response.json();
}

async function updatePeopleCount() {
    const data = await peopleCountCall();
    const countElement = document.getElementById('currentCount');
    countElement.textContent = data.count;
}


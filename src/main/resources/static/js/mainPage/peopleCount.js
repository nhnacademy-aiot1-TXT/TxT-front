async function peopleCountCall(accessToken) {
    const url = "http://localhost:8000/api/sensor/people-count";

    const option = {
        method : "GET",
        headers : {
            Authorization : accessToken
        }
    }
    const response= await fetch(url, option);
    return await response.json();
}

async function updatePeopleCount(accessToken) {
    const data = await peopleCountCall(accessToken);
    console.log(data);
    const countElement = document.getElementById('currentCount');
    countElement.textContent = data.count;
}


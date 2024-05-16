async function peopleCountCall(domain) {
    const url = domain + "/api/sensor/people-count";

    const option = {
        method : "GET",
        headers : {
            Authorization : accessToken
        }
    }
    const response= await fetch(url, option);
    return await response.json();
}

async function updatePeopleCount(domain) {
    const data = await peopleCountCall(domain);
    const countElement = document.getElementById('currentCount');
    countElement.textContent = data.count;
}


async function humidityCountCall(accessToken) {
    const url = 'http://localhost:8000/api/sensor/humidity'; // Ensure the endpoint is correct and accessible

    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);
    return await response.json();
}

async function updateHumidity(accessToken) {
    const data = await humidityCountCall(accessToken);
    console.log(data);
    const gaugeElement = document.getElementById('humidity');
    gaugeElement.setAttribute("data-used", data.value);
    gaugeElement.setAttribute("data-text", data.value);
}


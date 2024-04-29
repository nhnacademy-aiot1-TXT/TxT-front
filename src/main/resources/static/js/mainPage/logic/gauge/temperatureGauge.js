async function temperatureCountCall(accessToken) {
    const url = 'http://localhost:8000/api/sensor/temperature'; // Ensure the endpoint is correct and accessible

    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);
    return await response.json();
}

async function updateTemperature(accessToken) {
    const data = await temperatureCountCall(accessToken);
    console.log(data);
    const gaugeElement = document.getElementById('temperature');
    gaugeElement.setAttribute("data-used", data.value);
    gaugeElement.setAttribute("data-text", data.value);
}


async function co2CountCall(accessToken) {
    const url = 'http://localhost:8000/api/sensor/co2'; // Ensure the endpoint is correct and accessible

    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);
    return await response.json();
}

async function updateCo2(accessToken) {
    const data = await co2CountCall(accessToken);
    console.log(data);
    const gaugeElement = document.getElementById('co2');
    gaugeElement.setAttribute("data-used", data.value);
    gaugeElement.setAttribute("data-text", data.value);
}


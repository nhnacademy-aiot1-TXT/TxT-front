async function illuminationCountCall(accessToken) {
    const url = 'http://localhost:8000/api/sensor/illumination'; // Ensure the endpoint is correct and accessible

    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);
    return await response.json();
}

async function updateIllumination(accessToken) {
    const data = await illuminationCountCall(accessToken);
    console.log(data);
    const gaugeElement = document.getElementById('illumination');
    gaugeElement.setAttribute("data-used", data.value);
    gaugeElement.setAttribute("data-text", data.value);
}


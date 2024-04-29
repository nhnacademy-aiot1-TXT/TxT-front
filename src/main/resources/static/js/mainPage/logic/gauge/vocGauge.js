async function vocCountCall(accessToken) {
    const url = 'http://localhost:8000/api/sensor/voc'; // Ensure the endpoint is correct and accessible

    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);
    return await response.json();
}

async function updateVoc(accessToken) {
    const data = await vocCountCall(accessToken);
    console.log(data);
    const gaugeElement = document.getElementById('voc');
    gaugeElement.setAttribute("data-used", data.value);
    gaugeElement.setAttribute("data-text", data.value);
}


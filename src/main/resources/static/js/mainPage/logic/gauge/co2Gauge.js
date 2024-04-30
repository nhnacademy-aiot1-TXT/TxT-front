async function co2CountCall() {
    const url = 'http://133.186.151.22:8000/api/sensor/co2'; // Ensure the endpoint is correct and accessible

    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);
    return await response.json();
}

async function updateCo2() {
    const data = await co2CountCall();
    console.log(data);
    const gaugeElement = document.getElementById('co2');
    gaugeElement.setAttribute("data-used", data.value);
    gaugeElement.setAttribute("data-text", data.value);

    console.log(gaugeElement.getAttribute("data-used"));
    console.log(gaugeElement.getAttribute("data-text"));

    $(gaugeElement).gaugeMeter();

}


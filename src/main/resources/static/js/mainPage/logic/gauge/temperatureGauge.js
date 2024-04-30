async function temperatureCountCall() {
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

async function updateTemperature() {
    const data = await temperatureCountCall();
    const value = data.value;
    console.log(data);
    const gaugeElement = document.getElementById('temperature');
    $(gaugeElement).data("used", Math.round(value)).data("text", value);
    $(gaugeElement).gaugeMeter();
}



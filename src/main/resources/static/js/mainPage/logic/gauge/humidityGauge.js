async function humidityCountCall() {
    const url = 'https://contxt.co.kr/api/sensor/humidity'; // Ensure the endpoint is correct and accessible

    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);
    return await response.json();
}

async function updateHumidity() {
    const data = await humidityCountCall();
    const value = data.value;
    const gaugeElement = document.getElementById('humidity');
    $(gaugeElement).data("used", Math.round(value)).data("text", value);
    $(gaugeElement).gaugeMeter();
}


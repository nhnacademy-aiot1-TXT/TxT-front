async function gaugeCountCall(sensorName) {
    const url = 'https://contxt.co.kr/api/sensor/' + sensorName;

    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);

    const data = await response.json();
    const value = data.value;
    const gaugeElement = document.getElementById(sensorName);
    $(gaugeElement).data("used", Math.round(value)).data("text", value);
    $(gaugeElement).gaugeMeter();
}

function gaugeUpdate() {
    gaugeCountCall('co2');
    gaugeCountCall('humidity');
    gaugeCountCall('illumination');
    gaugeCountCall('temperature');
    gaugeCountCall('voc');
}
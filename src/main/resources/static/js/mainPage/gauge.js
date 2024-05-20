async function gaugeCountCall(domain, sensorName, place) {
    const url = domain + '/api/sensor/' + sensorName + '/?place=' + place;

    const option = {
        method: "GET",
        headers: {
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

function gaugeUpdate(domain, place) {
    gaugeCountCall(domain, 'co2', place);
    gaugeCountCall(domain, 'humidity', place);
    gaugeCountCall(domain, 'illumination', place);
    gaugeCountCall(domain, 'temperature', place);
    gaugeCountCall(domain, 'voc', place);
}

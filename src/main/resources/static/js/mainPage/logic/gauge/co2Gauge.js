async function co2CountCall() {
    const url = 'https://contxt.co.kr/api/sensor/co2'; // Ensure the endpoint is correct and accessible

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
    const value = data.value;
    const gaugeElement = document.getElementById('co2');
    $(gaugeElement).data("used", value).data("text", value);
    $(gaugeElement).gaugeMeter();

}


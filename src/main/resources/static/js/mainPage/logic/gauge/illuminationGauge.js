async function illuminationCountCall() {
    const url = 'https://contxt.co.kr/api/sensor/illumination'; // Ensure the endpoint is correct and accessible

    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);
    return await response.json();
}

async function updateIllumination() {
    const data = await illuminationCountCall();
    const value = data.value;
    const gaugeElement = document.getElementById('illumination');
    $(gaugeElement).data("used", value).data("text", value);
    $(gaugeElement).gaugeMeter();
}


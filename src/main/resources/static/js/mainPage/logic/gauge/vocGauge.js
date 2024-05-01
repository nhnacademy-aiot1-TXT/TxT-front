async function vocCountCall() {
    const url = 'https://contxt.co.kr/api/sensor/voc'; // Ensure the endpoint is correct and accessible

    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);
    return await response.json();
}

async function updateVoc() {
    const data = await vocCountCall();
    const value = data.value;
    console.log(data);
    const gaugeElement = document.getElementById('voc');
    $(gaugeElement).data("used", value).data("text", value);
    $(gaugeElement).gaugeMeter();
}


async function weatherCall(domain) {
    const url = domain + "/api/common/weather";

    const option = {
        method: "GET",
        headers: {
            Authorization: accessToken
        }
    }
    const response = await fetch(url, option);
    return await response.json();
}

async function weather(domain) {
    const data = await weatherCall(domain);
    const weatherSky = document.getElementById('weatherSky');
    const weatherTemperature = document.getElementById('weatherTemperature');
    const weatherImage = document.getElementById('weatherImage');

    weatherSky.textContent = data.sky;
    weatherTemperature.textContent = data.temperature;

    switch (weatherSky.textContent) {
        case '맑음':
            weatherImage.src = "/img/sun.png";
            weatherImage.width = 150;
            weatherImage.height = 150;
            break;
        case '구름 많음':
            weatherImage.src = "/img/rain.png";
            weatherImage.width = 150;
            weatherImage.height = 150;
            break;
        case '흐림':
            weatherImage.src = "/img/cloud.png";
            weatherImage.width = 150;
            weatherImage.height = 150;
            break;
    }
}
async function weatherCall() {
    const url = "https://contxt.co.kr/api/common/weather";

    const option = {
        method : "GET",
        headers : {
            Authorization : accessToken
        }
    }
    const response= await fetch(url, option);
    return await response.json();
}

async function weather() {
    const data = await weatherCall();
    const weatherSky = document.getElementById('weatherSky');
    const weatherTemperature = document.getElementById('weatherTemperature');
    const weatherImage = document.getElementById('weatherImage');

    weatherSky.textContent = data.sky;
    weatherTemperature.textContent = data.temperature;

    switch (weatherSky.textContent) {
        case '맑음':
            weatherImage.src = "/img/sun.png";
            break;
        case '구름 많음':
            weatherImage.src = "/img/rain.png";
            break;
        case '흐림':
            weatherImage.src = "/img/cloud.png";
            break;
    }
}
document.addEventListener('DOMContentLoaded', function () {
    let lightSwitch = document.getElementById('light');
    lightSwitch.addEventListener('click', function () {
        let isOn = lightSwitch.checked; // checkbox의 상태를 확인합니다.

        fetch(`/control/light?placeCode=${placeCode}&isOn=${isOn}`, {
            method: 'GET',
        }).then(function (response) {
            console.log('light control success');
        }).catch(function (error) {
            console.error('Error:', error);
        });
    });

    let airconditionerSwitch = document.getElementById('airconditioner');
    airconditionerSwitch.addEventListener('click', function () {
        let isOn = airconditionerSwitch.checked; // checkbox의 상태를 확인합니다.

        fetch(`/control/air-conditioner?placeCode=${placeCode}&isOn=${isOn}`, {
            method: 'GET',
        }).then(function (response) {
            console.log('airconditioner control success');
        }).catch(function (error) {
            console.error('Error:', error);
        });
    });

    let aircleanerSwitch = document.getElementById('aircleaner');
    aircleanerSwitch.addEventListener('click', function () {
        let isOn = aircleanerSwitch.checked; // checkbox의 상태를 확인합니다.

        fetch(`/control/air-cleaner?placeCode=${placeCode}&isOn=${isOn}`, {
            method: 'GET',
        }).then(function (response) {
            console.log('aircleaner control success');
        }).catch(function (error) {
            console.error('Error:', error);
        });
    });

    let customModeAirconditioner = document.getElementById('airconditioner-custom-mode');
    let customModeAircleaner = document.getElementById('aircleaner-custom-mode');
    let customModeLight = document.getElementById('light-custom-mode');
    let aiModeSwitch = document.getElementById('ai-mode');
    let aiResultButton = document.getElementById('ai-result');

    customModeAirconditioner.addEventListener('click', function () {
        let isOn = customModeAirconditioner.checked;

        fetch(`/control/custom-mode?deviceName=airconditioner&placeCode=${placeCode}&isOn=${isOn}`, {
            method: 'GET',
        }).then(function (response) {
            console.log('mode control success');
        }).catch(function (error) {
            console.error('Error:', error);
        });

        airconditionerSwitch.disabled = isOn;
    });

    customModeAircleaner.addEventListener('click', function () {
        let isOn = customModeAircleaner.checked;

        fetch(`/control/custom-mode?deviceName=aircleaner&placeCode=${placeCode}&isOn=${isOn}`, {
            method: 'GET',
        }).then(function (response) {
            console.log('mode control success');
        }).catch(function (error) {
            console.error('Error:', error);
        });

        aircleanerSwitch.disabled = isOn;
    });

    customModeLight.addEventListener('click', function () {
        let isOn = customModeLight.checked;

        fetch(`/control/custom-mode?deviceName=light&placeCode=${placeCode}&isOn=${isOn}`, {
            method: 'GET',
        }).then(function (response) {
            console.log('mode control success');
        }).catch(function (error) {
            console.error('Error:', error);
        });

        lightSwitch.disabled = isOn;
    });

    aiModeSwitch.addEventListener('click', function () {
        let isOn = aiModeSwitch.checked; // checkbox의 상태를 확인합니다.

        fetch(`/control/ai-mode?placeCode=${placeCode}&isOn=${isOn}`, {
            method: 'GET',
        }).then(function (response) {
            console.log('mode control success');
        }).catch(function (error) {
            console.error('Error:', error);
        });

        if (isOn)
            customModeAirconditioner.checked = false;

        aiResultButton.hidden = !isOn;
        customModeAirconditioner.disabled = isOn;
        airconditionerSwitch.disabled = isOn;
    });

    aiResultButton.addEventListener('click', async function () {
        let deviceName = document.getElementById('deviceName');
        let time = document.getElementById('time');
        let indoorTemperature = document.getElementById('indoorTemperature');
        let indoorHumidity = document.getElementById('indoorHumidity');
        let outdoorTemperature = document.getElementById('outdoorTemperature');
        let outdoorHumidity = document.getElementById('outdoorHumidity');
        let totalPeopleCount = document.getElementById('totalPeopleCount');
        let result = document.getElementById('result');

        const url = '/control/ai-result';

        const option = {
            method: "GET",
        }
        const response = await fetch(url, option);
        const data = await response.json();

        const errorMessage = '데이터를 찾을 수 없습니다.'
        deviceName.innerText = data.deviceName == null ? errorMessage : data.deviceName;
        time.innerText = data.time == null ? errorMessage : data.time;
        indoorTemperature.innerText = data.indoorTemperature == null ? errorMessage : data.indoorTemperature;
        indoorHumidity.innerText = data.indoorHumidity == null ? errorMessage : data.indoorHumidity;
        outdoorTemperature.innerText = data.outdoorTemperature == null ? errorMessage : data.outdoorTemperature;
        outdoorHumidity.innerText = data.outdoorHumidity == null ? errorMessage : data.outdoorHumidity;
        totalPeopleCount.innerText = data.totalPeopleCount == null ? errorMessage : data.totalPeopleCount;
        result.innerText = data.result == null ? errorMessage : data.result;
    })
});
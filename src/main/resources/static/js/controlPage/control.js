document.addEventListener('DOMContentLoaded', function () {
    let lightSwitch = document.getElementById('light');
    lightSwitch.addEventListener('click', function () {
        let isOn = lightSwitch.checked; // checkbox의 상태를 확인합니다.
        console.log(isOn);
        fetch(`/control/light?isOn=${isOn}`, {
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
        console.log(isOn);
        fetch(`/control/air-conditioner?isOn=${isOn}`, {
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
        console.log(isOn);
        fetch(`/control/air-cleaner?isOn=${isOn}`, {
            method: 'GET',
        }).then(function (response) {
            console.log('aircleaner control success');
        }).catch(function (error) {
            console.error('Error:', error);
        });
    });

    let modeSwitch = document.getElementById('mode');
    modeSwitch.addEventListener('click', function () {
        let isOn = modeSwitch.checked; // checkbox의 상태를 확인합니다.
        console.log(isOn);
        fetch(`/control/ai-mode?isOn=${isOn}`, {
            method: 'GET',
        }).then(function (response) {
            console.log('mode control success');
        }).catch(function (error) {
            console.error('Error:', error);
        });

        lightSwitch.disabled = isOn;
        airconditionerSwitch.disabled = isOn;
        aircleanerSwitch.disabled = isOn;
    });
});
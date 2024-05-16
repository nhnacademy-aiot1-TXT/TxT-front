async function chartInit(domain, name, date, place) {
    const url = domain + '/api/sensor/' + name + '/' + date + '/?place=' + place;
    const option = {
        method: "GET",
        headers: {
            Authorization: accessToken
        }
    }

    let data = await (await fetch(url, option)).json();
    let ctx = document.getElementById(date + '-' + name).getContext("2d");
    let chart = chatDraw(ctx);

    let maxValue = 'max' + name.charAt(0).toUpperCase() + name.slice(1);
    let minValue = 'min' + name.charAt(0).toUpperCase() + name.slice(1);

    if (date === "day") {
        chart.data.labels = data.map(item => new Date(item.time).toLocaleTimeString("en-US", {
            hour: "numeric",
            minute: "numeric",
            hour12: true,
            timeZone: "UTC"
        }));
    } else {
        chart.data.labels = data.map(item => new Date(item.time).toLocaleDateString("en-US", {
            month: "numeric",
            day: "numeric",
            timeZone: "UTC"
        }));
    }
    chart.data.datasets[0].data = data.map(item => item[maxValue]);
    chart.data.datasets[1].data = data.map(item => item[minValue]);
    chart.update();
}

function applyChart(domain, place) {
    chartInit(domain, "co2", "day", place);
    chartInit(domain, "humidity", "day", place);
    chartInit(domain, "temperature", "day", place);
    chartInit(domain, "illumination", "day", place);

    chartInit(domain, "co2", "week", place);
    chartInit(domain, "humidity", "week", place);
    chartInit(domain, "temperature", "week", place);
    chartInit(domain, "illumination", "week", place);

    chartInit(domain, "co2", "month", place);
    chartInit(domain, "humidity", "month", place);
    chartInit(domain, "temperature", "month", place);
    chartInit(domain, "illumination", "month", place);
}
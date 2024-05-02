async function commonInit(name, date) {
    const url = 'https://contxt.co.kr/api/sensor/' + name + '/' + date;
    const option = {
        method : "GET",
        headers : {
            Authorization: accessToken
        }
    }

    let data = await (await fetch(url, option)).json();
    let ctx = document.getElementById(date + '-' + name).getContext("2d");
    let chart = drawChart(ctx);

    let maxValue = 'max' + name.charAt(0).toUpperCase() + name.slice(1);
    let minValue = 'min' + name.charAt(0).toUpperCase() + name.slice(1);

    if (date === "day") {
        chart.data.labels = data.map(item => new Date(item.time).toLocaleTimeString("en-US", { timeZone: "UTC" }));
    } else {
        chart.data.labels = data.map(item => new Date(item.time).toLocaleDateString("ko-KR"));
    }
    chart.data.datasets[0].data = data.map(item => item[maxValue]);
    chart.data.datasets[1].data = data.map(item => item[minValue]);
    chart.update();
}
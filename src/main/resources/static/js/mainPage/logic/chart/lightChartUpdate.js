
var jsonData2 = [
    {
        "time": "2024-04-12T08:00:00Z",
        "maxBrightness": 1000,
        "minBrightness": 750
    },
    {
        "time": "2024-04-12T09:00:00Z",
        "maxBrightness": 900,
        "minBrightness": 560
    },
    {
        "time": "2024-04-12T10:00:00Z",
        "maxBrightness": 1100,
        "minBrightness": 270
    },
    {
        "time": "2024-04-12T11:00:00Z",
        "maxBrightness": 950,
        "minBrightness": 355
    },
    {
        "time": "2024-04-12T12:00:00Z",
        "maxBrightness": 1050,
        "minBrightness": 455
    },
    {
        "time": "2024-04-12T13:00:00Z",
        "maxBrightness": 980,
        "minBrightness": 572
    },
    {
        "time": "2024-04-12T14:00:00Z",
        "maxBrightness": 1020,
        "minBrightness": 480
    },
    {
        "time": "2024-04-12T15:00:00Z",
        "maxBrightness": 970,
        "minBrightness": 472
    },
    {
        "time": "2024-04-12T16:00:00Z",
        "maxBrightness": 1030,
        "minBrightness": 534
    }
];


function updateLightChart(jsonData) {
    var maxBrightnesses = jsonData2.map(item => item.maxBrightness);
    var minBrightnesses = jsonData2.map(item => item.minBrightness);
    var labels = jsonData2.map(item => new Date(item.time).toLocaleTimeString());

    window.lightChart.data.labels = labels;
    window.lightChart.data.datasets[0].data = maxBrightnesses;
    window.lightChart.data.datasets[1].data = minBrightnesses;
    window.lightChart.update();
}


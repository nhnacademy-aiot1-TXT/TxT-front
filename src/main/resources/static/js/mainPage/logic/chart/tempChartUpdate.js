
var jsonData = [
    {
        "time": "2024-04-12T08:00:00Z",
        "maxTemperature": 23.900000000000002,
        "minTemperature": 21.700000000000003
    },
    {
        "time": "2024-04-12T09:00:00Z",
        "maxTemperature": 24.3,
        "minTemperature": 10.2
    },
    {
        "time": "2024-04-12T10:00:00Z",
        "maxTemperature": 27.400000000000002,
        "minTemperature": 10.8
    },
    {
        "time": "2024-04-12T11:00:00Z",
        "maxTemperature": 25.0,
        "minTemperature": 20.2
    },
    {
        "time": "2024-04-12T12:00:00Z",
        "maxTemperature": 25.400000000000002,
        "minTemperature": 23.0
    },
    {
        "time": "2024-04-12T13:00:00Z",
        "maxTemperature": 25.5,
        "minTemperature": 8.8
    },
    {
        "time": "2024-04-12T14:00:00Z",
        "maxTemperature": 25.5,
        "minTemperature": 11.2
    },
    {
        "time": "2024-04-12T15:00:00Z",
        "maxTemperature": 25.6,
        "minTemperature": 5.8
    },
    {
        "time": "2024-04-12T16:00:00Z",
        "maxTemperature": 25.8,
        "minTemperature": 15.2
    }
];


function updateTempChart(jsonData) {
    var maxTemperatures = jsonData.map(item => item.maxTemperature);
    var minTemperatures = jsonData.map(item => item.minTemperature);
    var labels = jsonData.map(item => new Date(item.time).toLocaleTimeString());

    window.tempChart.data.labels = labels;
    window.tempChart.data.datasets[0].data = maxTemperatures;
    window.tempChart.data.datasets[1].data = minTemperatures;
    window.tempChart.update();
}


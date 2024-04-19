var jsonData3 = [
    {
        "time": "2024-04-12T08:00:00Z",
        "maxMoist": 23.9,
        "minMoist": 21.7
    },
    {
        "time": "2024-04-12T09:00:00Z",
        "maxMoist": 24.3,
        "minMoist": 22.1
    },
    {
        "time": "2024-04-12T10:00:00Z",
        "maxMoist": 27.4,
        "minMoist": 22.8
    },
    {
        "time": "2024-04-12T11:00:00Z",
        "maxMoist": 25.0,
        "minMoist": 21.9
    },
    {
        "time": "2024-04-12T12:00:00Z",
        "maxMoist": 25.4,
        "minMoist": 23.3
    },
    {
        "time": "2024-04-12T13:00:00Z",
        "maxMoist": 25.5,
        "minMoist": 23.0
    },
    {
        "time": "2024-04-12T14:00:00Z",
        "maxMoist": 25.5,
        "minMoist": 22.5
    },
    {
        "time": "2024-04-12T15:00:00Z",
        "maxMoist": 25.6,
        "minMoist": 22.8
    },
    {
        "time": "2024-04-12T16:00:00Z",
        "maxMoist": 25.8,
        "minMoist": 22.2
    }
];



function updateMoistChart(jsonData3) {
    var maxMoists = jsonData3.map(item => item.maxMoist);
    var minMoists = jsonData3.map(item => item.minMoist);
    var labels = jsonData3.map(item => new Date(item.time).toLocaleTimeString());

    window.MoistChart.data.labels = labels;
    window.MoistChart.data.datasets[0].data = maxMoists;
    window.MoistChart.data.datasets[1].data = minMoists;
    window.MoistChart.update();
}






var jsonData = [
    {
        "time": "2024-04-12T08:00:00Z",
        "maxTemperature": 23.900000000000002,
        "minTemperature": 21.700000000000003
    },
    {
        "time": "2024-04-12T09:00:00Z",
        "maxTemperature": 24.3,
        "minTemperature": 0.2
    },
    {
        "time": "2024-04-12T10:00:00Z",
        "maxTemperature": 27.400000000000002,
        "minTemperature": 0.8
    },
    {
        "time": "2024-04-12T11:00:00Z",
        "maxTemperature": 25.0,
        "minTemperature": 0.2
    },
    {
        "time": "2024-04-12T12:00:00Z",
        "maxTemperature": 25.400000000000002,
        "minTemperature": 23.0
    },
    {
        "time": "2024-04-12T13:00:00Z",
        "maxTemperature": 25.5,
        "minTemperature": 0.8
    },
    {
        "time": "2024-04-12T14:00:00Z",
        "maxTemperature": 25.5,
        "minTemperature": 0.2
    },
    {
        "time": "2024-04-12T15:00:00Z",
        "maxTemperature": 25.6,
        "minTemperature": 0.8
    },
    {
        "time": "2024-04-12T16:00:00Z",
        "maxTemperature": 25.8,
        "minTemperature": 0.2
    }
];

var currentIndex = 0;

function updateTemperature2() {
    // Get the current data point
    var currentData = jsonData[currentIndex];

    // Assuming you have elements in your HTML to display max and min temperatures
    // Replace these selectors with your actual HTML element IDs or classes
    var maxTempElement = document.getElementById('max-temperature');
    var minTempElement = document.getElementById('min-temperature');

    // Update the display with the current max and min temperatures
    maxTempElement.textContent = "Max Temperature: " + currentData.maxTemperature + "°C";
    minTempElement.textContent = "Min Temperature: " + currentData.minTemperature + "°C";

    // Increment the index for the next data point
    currentIndex = (currentIndex + 1) % jsonData.length;
}

document.addEventListener('DOMContentLoaded', function() {
    updateTemperature2();
    setInterval(updateTemperature2, 1000);
});

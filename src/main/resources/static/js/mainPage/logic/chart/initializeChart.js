async function initializeCO2Chart() {
    commonInit("co2","day");
    commonInit("co2", "week");
    commonInit("co2", "month");
}
async function initializeHumidityChart() {
    commonInit("humidity","day");
    commonInit("humidity", "week");
    commonInit("humidity", "month");
}
async function initializeTempChart() {
    commonInit("temperature", "day");
    commonInit("temperature", "week");
    commonInit("temperature", "month");
}
async function initializeIlluminationChart() {
    commonInit("illumination", "day");
    commonInit("illumination", "week");
    commonInit("illumination", "month");
}

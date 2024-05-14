async function initializeDayChart() {
    commonInit("co2","day");
    commonInit("humidity","day");
    commonInit("temperature", "day");
    commonInit("illumination", "day");

}
async function initializeWeekChart() {
    commonInit("co2", "week");
    commonInit("humidity", "week");
    commonInit("temperature", "week");
    commonInit("illumination", "week");
}
async function initializeMonthChart() {
    commonInit("co2", "month");
    commonInit("humidity", "month");
    commonInit("temperature", "month");
    commonInit("illumination", "month");
}
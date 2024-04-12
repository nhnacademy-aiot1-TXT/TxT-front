// tempChart.js
function initializeTempChart() {

    var ctx = document.getElementById("new-chart").getContext("2d");



    var gradientStroke = ctx.createLinearGradient(0, 230, 0, 50);
    gradientStroke.addColorStop(1, 'rgba(255, 99, 132, 0.2)');
    gradientStroke.addColorStop(0.2, 'rgba(255, 99, 132, 0.0)');
    gradientStroke.addColorStop(0, 'rgba(255, 99, 132, 0)');
    new Chart(ctx, {
        type: "line",
        data: {
            labels: ["Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            datasets: [{
                label: "Temprature",
                fill: true,
                backgroundColor: "rgba(255, 99, 132, 0.2)",
                borderColor: "rgb(255, 99, 132)",
                pointBorderColor: "#fff",
                pointBackgroundColor: "rgb(255, 99, 132)",
                pointBorderWidth: 2,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgb(255, 99, 132)",
                pointHoverBorderColor: "rgba(220, 220, 220, 1)",
                pointHoverBorderWidth: 2,
                pointRadius: 3,
                pointHitRadius: 10,
                data: [28, 48, 40, 19, 86, 27, 90],
            }]
        },
        options: {
            maintainAspectRatio: false,
            scales: {
                y: {
                    grid: {
                        drawBorder: false,
                        display: true,
                        drawOnChartArea: true,
                        drawTicks: false,
                        borderDash: [5, 5]
                    },
                    ticks: {
                        display: true,
                        padding: 20,
                        // color: '#fbfbfb', //글자색
                        font: {
                            size: 11,
                            family: "Open Sans",
                            style: 'normal',
                            lineHeight: 2
                        },
                    }
                },
                x: {
                    grid: {
                        drawBorder: false,
                        display: false,
                        drawOnChartArea: false,
                        drawTicks: false,
                        borderDash: [5, 5]
                    },
                    ticks: {
                        display: true,
                        color: '#ccc',
                        padding: 20,
                        font: {
                            size: 11,
                            family: "Open Sans",
                            style: 'normal',
                            lineHeight: 2
                        },
                    }
                },
            },
        }
    });
}


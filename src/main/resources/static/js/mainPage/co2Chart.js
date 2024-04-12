// lightChart.js
function initializeCO2Chart() {



    var ctx = document.getElementById("new-chart2").getContext("2d");

    // ctx.canvas.style.backgroundColor = 'rgba(0, 0, 0, 1)'; // 캔버스 배경을 검정색으로 설정


    var gradientStroke = ctx.createLinearGradient(0, 230, 0, 50);
    gradientStroke.addColorStop(1, 'rgba(94, 114, 228, 0.2)');
    gradientStroke.addColorStop(0.2, 'rgba(94, 114, 228, 0.0)');
    gradientStroke.addColorStop(0, 'rgba(94, 114, 228, 0)');



    new Chart(ctx, {
        type: "line",
        data: {
            labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"],
            datasets: [{
                label: "CO2",
                fill: true,
                backgroundColor: "rgba(78, 115, 223, 0.05)",
                borderColor: "rgba(78, 115, 223, 1)",
                pointBorderColor: "#FFF",
                pointBackgroundColor: "rgba(78, 115, 223, 1)",
                pointBorderWidth: 2,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
                pointHoverBorderColor: "rgba(220, 220, 220, 1)",
                pointHoverBorderWidth: 2,
                pointRadius: 3,
                pointHitRadius: 10,
                data: [65, 59, 80, 81, 56, 55, 40],
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
                        padding: 10,
                        // color: '#fbfbfb',
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


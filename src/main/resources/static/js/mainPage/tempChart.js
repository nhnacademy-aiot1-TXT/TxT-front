// chartSetup.js

function initializeTempChart() {
    var ctx = document.getElementById("new-chart").getContext("2d");
    var gradientStroke = ctx.createLinearGradient(0, 230, 0, 50);
    gradientStroke.addColorStop(1, 'rgba(255, 99, 132, 0.2)');
    gradientStroke.addColorStop(0.2, 'rgba(255, 99, 132, 0.0)');
    gradientStroke.addColorStop(0, 'rgba(255, 99, 132, 0)');

    var tempChart = new Chart(ctx, {
        type: "line",
        data: {
            labels: [],
            datasets: [{
                label: "Maximum",
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
                data: []
            }, {
                label: "Minimum",
                fill: true,
                backgroundColor: "rgba(99, 104, 255, 0.2)",
                borderColor: "rgb(99, 104, 255)",
                pointBorderColor: "#fff",
                pointBackgroundColor: "rgb(99, 104, 255)",
                pointBorderWidth: 2,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgb(99, 104, 255)",
                pointHoverBorderColor: "rgba(220, 220, 220, 1)",
                pointHoverBorderWidth: 2,
                pointRadius: 3,
                pointHitRadius: 10,
                data: []
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
                        // 글씨색깔
                        color: '#333',
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

    // 데이터 업데이트 함수를 전역 변수로 설정하여 외부 파일에서 접근 가능하게 함
    window.tempChart = tempChart;
}

document.addEventListener('DOMContentLoaded', function() {

    initializeTempChart();
    // setTimeout을 사용하여 차트가 초기화된 후 데이터를 업데이트함
    setTimeout(function() {
        // jsonData는 실제 사용하는 데이터 변수
        updateTempChart(jsonData);
    });
});

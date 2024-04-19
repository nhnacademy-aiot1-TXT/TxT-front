

function initializeLightChart() {

    var ctx1 = document.getElementById("chart-line").getContext("2d");

    var gradientStroke1 = ctx1.createLinearGradient(0, 230, 0, 50);

    gradientStroke1.addColorStop(1, 'rgba(94, 114, 228, 0.2)');
    gradientStroke1.addColorStop(0.2, 'rgba(94, 114, 228, 0.0)');
    gradientStroke1.addColorStop(0, 'rgba(94, 114, 228, 0)');



    var lightChart = new Chart(ctx1, {
        type: "line",
        data: {
            labels: [],
            datasets: [{
                label: "Maximum",
                tension: 0.4,
                borderWidth: 0,
                pointRadius: 0,
                borderColor: "#e248cd",
                backgroundColor: "rgba(255, 99, 132, 0.2)",
                //backgroundColor: gradientStroke1,
                borderWidth: 3,
                fill: true,
                maxBarThickness: 6,
                data: []

            }, {
                label: "Minimum",
                tension: 0.4,
                borderWidth: 0,
                pointRadius: 0,
                borderColor: "#5e72e4",
                backgroundColor: "rgba(99, 104, 255, 0.3)",
                //backgroundColor: gradientStroke1,
                borderWidth: 3,
                fill: true,
                data: [],
                maxBarThickness: 6

            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false,
                }
            },
            interaction: {
                intersect: false,
                mode: 'index',
            },

            // 차트 마진 값
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
                        color: '#333',
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
        },
    });

    // 데이터 업데이트 함수를 전역 변수로 설정하여 외부 파일에서 접근 가능하게 함
    window.lightChart = lightChart;
}


document.addEventListener('DOMContentLoaded', function() {

    initializeLightChart();
    // setTimeout을 사용하여 차트가 초기화된 후 데이터를 업데이트함
    setTimeout(function() {
        // jsonData는 실제 사용하는 데이터 변수
        updateLightChart(jsonData);
    });
});

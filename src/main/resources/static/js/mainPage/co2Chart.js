// lightChart.js
function initializeCO2Chart() {



    var ctx = document.getElementById("new-chart2").getContext("2d");

    // ctx.canvas.style.backgroundColor = 'rgba(0, 0, 0, 1)'; // 캔버스 배경을 검정색으로 설정


    var gradientStroke = ctx.createLinearGradient(0, 230, 0, 50);
    gradientStroke.addColorStop(1, 'rgba(94, 114, 228, 0.2)');
    gradientStroke.addColorStop(0.2, 'rgba(94, 114, 228, 0.0)');
    gradientStroke.addColorStop(0, 'rgba(94, 114, 228, 0)');



    var CO2Chart = new Chart(ctx, {
        type: "line",
        data: {
            labels: [],
            datasets: [{
                label: "Maximum",
                fill: true,
                backgroundColor: "rgb(226,72,205,0.2)",
                borderColor: "rgb(226,72,205)",
                pointBorderColor: "#FFF",
                pointBackgroundColor: "rgb(226,72,205)",
                pointBorderWidth: 2,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgb(226,72,205)",
                pointHoverBorderColor: "rgba(220, 220, 220, 1)",
                pointHoverBorderWidth: 2,
                pointRadius: 3,
                pointHitRadius: 10,
                data: [],
            }, {
                label: "Minimum",
                fill: true,
                backgroundColor: "rgba(78, 115, 223, 0.3)",
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
                data: [],
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


    // 데이터 업데이트 함수를 전역 변수로 설정하여 외부 파일에서 접근 가능하게 함
    window.CO2Chart = CO2Chart;

}

document.addEventListener('DOMContentLoaded', function() {

    //initializeTempChart();
    initializeCO2Chart();

    // setTimeout을 사용하여 차트가 초기화된 후 데이터를 업데이트함
    setTimeout(function() {
        // jsonData는 실제 사용하는 데이터 변수
        updateCO2Chart(jsonData);
    }); // 1초 후 데이터 업데이트
});

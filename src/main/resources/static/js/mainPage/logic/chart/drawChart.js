function drawChart(ctx) {

    let GradientStroke = ctx.createLinearGradient(0, 230, 0, 50);
    GradientStroke.addColorStop(1, 'rgba(255, 99, 132, 0.2)');
    GradientStroke.addColorStop(0.2, 'rgba(255, 99, 132, 0.0)');
    GradientStroke.addColorStop(0, 'rgba(255, 99, 132, 0)');

    return new Chart(ctx, {
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
                borderWidth: 3,
                fill: true,
                maxBarThickness: 6,
                data: []
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
}
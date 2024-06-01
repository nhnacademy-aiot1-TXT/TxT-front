function chatDraw(ctx) {

    let gradientStroke1 = ctx.createLinearGradient(0, 300, 0, 0);

    gradientStroke1.addColorStop(1, 'rgba(236, 95, 92, 0.3)');
    gradientStroke1.addColorStop(0.1, 'rgba(244, 136, 117, 0)');
    gradientStroke1.addColorStop(0, 'rgba(254, 181, 145, 0)');

    let gradientStroke2 = ctx.createLinearGradient(0, 400, 0, 0);

    gradientStroke2.addColorStop(1, 'rgba(7, 151, 255,0.3)');
    gradientStroke2.addColorStop(0.1, 'rgba(87, 185, 255, 0)');
    gradientStroke2.addColorStop(0, 'rgba(163, 217, 255, 0)');

    return new Chart(ctx, {
        type: "line",
        data: {
            labels: [],
            datasets: [{
                label: "Maximum",
                tension: 0.4,
                pointRadius: 0,
                borderColor: "#fd6a31",
                backgroundColor: gradientStroke1,
                borderWidth: 2,
                fill: true,
                maxBarThickness: 6,
                data: []
            }, {
                label: "Minimum",
                tension: 0.4,
                pointRadius: 0,
                borderColor: "#25a2fc",
                backgroundColor: gradientStroke2,
                borderWidth: 2,
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
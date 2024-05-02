document.addEventListener('DOMContentLoaded', function() {
    var chartLine2 = document.getElementById('chart-line2');
    var chartLine3 = document.getElementById('chart-line3');

    // chart-line2에 클릭 이벤트 리스너 추가
    if (chartLine2) {
        chartLine2.addEventListener('click', function() {
            window.location.href = 'https://example.com/url-for-chart-line2'; // 여기에 첫 번째 캔버스 클릭 시 이동할 URL 입력
        });
    }

    // chart-line3에 클릭 이벤트 리스너 추가
    if (chartLine3) {
        chartLine3.addEventListener('click', function() {
            window.location.href = 'https://example.com/url-for-chart-line3'; // 여기에 두 번째 캔버스 클릭 시 이동할 URL 입력
        });
    }
});

function updatelight() {
    var xhr = new XMLHttpRequest();
    var url = 'https://133.186.151.22:8000/api/sensor/illumination'; // Ensure the endpoint is correct and accessible

    xhr.open("GET", url);
    xhr.onload = function() {
        if (xhr.status === 200) {
            var data = JSON.parse(xhr.responseText);
            var value = data.value;
            console.log("light value:", value);

            var gaugeElement = document.getElementById('light');

            var min = parseInt(gaugeElement.getAttribute('data-min'), 10);
            var total = parseInt(gaugeElement.getAttribute('data-total'), 10);

            var percent = calculatePercent(value, min, total);

            // 업데이트할 게이지 속성 설정
            gaugeElement.setAttribute('data-percent', percent);
            gaugeElement.setAttribute('data-text',value);

            console.log("Updated gauge values - percent:", gaugeElement.getAttribute('data-percent'),
                "Text:", gaugeElement.getAttribute('data-text'));


            updateGauge(gaugeElement, percent, value);

        } else {
            console.error("Failed to fetch light data. Status:", xhr.status);
        }
    };
    xhr.onerror = function() {
        console.error("An error occurred during the request to fetch light data.");
    };
    xhr.send();
}

function updateGauge(gaugeElement, percent, text) {
    // 데이터 속성 업데이트
    $(gaugeElement).data('percent', percent).data('text', text);

    // 플러그인 재초기화
    $(gaugeElement).gaugeMeter({
        percent: percent,
        text: text
    });

    // 게이지 업데이트를 위해 플러그인을 재초기화하는 것만으로 충분하므로,
    // 직접적인 drawGauge 호출은 제거합니다.
    // 이로써 사용자는 플러그인의 공식 API를 통해 게이지를 관리할 수 있습니다.
}


function calculatePercent(value, min, total) {
    // 입력 값이 최소값보다 작은 경우
    if (value < min) {
        console.error("Error: Value is below the minimum limit.");
        return 0;  // 게이지 퍼센트를 0으로 설정
    }
    // 입력 값이 최대값보다 큰 경우
    if (value > total) {
        console.error("Error: Value exceeds the maximum limit.");
        return 100;  // 게이지 퍼센트를 100으로 설정
    }
    // 퍼센트 계산
    const percent = ((value - min) / (total - min)) * 100;
    return percent;
}




// Initialization and interval setting
document.addEventListener('DOMContentLoaded', function() {
    updatelight();
    setInterval(updatelight, 5000); // Update light every 1 second
});

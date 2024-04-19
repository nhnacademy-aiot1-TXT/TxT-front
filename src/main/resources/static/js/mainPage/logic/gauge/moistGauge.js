function updatehumidity() {
    var xhr = new XMLHttpRequest();
    var url = 'http://133.186.134.250:8300/api/sensor/humidity'; // Ensure the endpoint is correct and accessible

    xhr.open("GET", url);
    xhr.onload = function() {
        if (xhr.status === 200) {
            var data = JSON.parse(xhr.responseText);
            var value = data.value;
            console.log("humidity value:", value);

            var gaugeElement = document.getElementById('moist');

            var min = parseInt(gaugeElement.getAttribute('data-min'), 10);
            var total = parseInt(gaugeElement.getAttribute('data-total'), 10);


            // 업데이트할 게이지 속성 설정
            gaugeElement.setAttribute('data-percent', value);
            gaugeElement.setAttribute('data-text',value);

            console.log("Updated gauge values - percent:", gaugeElement.getAttribute('data-percent'),
                "Text:", gaugeElement.getAttribute('data-text'));


            updateGauge(gaugeElement, value, value);

        } else {
            console.error("Failed to fetch humidity data. Status:", xhr.status);
        }
    };
    xhr.onerror = function() {
        console.error("An error occurred during the request to fetch humidity data.");
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






// Initialization and interval setting
document.addEventListener('DOMContentLoaded', function() {
    updatehumidity();
    setInterval(updatehumidity, 5000); // Update humidity every 1 second
});

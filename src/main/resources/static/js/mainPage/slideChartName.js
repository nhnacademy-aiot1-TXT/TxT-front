

function changeSlideTitle(direction) {
    var titles = ['Temperature', 'CO2', 'Moist']; // 슬라이드별 제목
    var carousel = document.querySelector('#carouselExampleCaptions');

    carousel.addEventListener('slide.bs.carousel', function (event) {
        var nextSlideIndex = event.to; // 다음 슬라이드의 인덱스
        var newTitle = titles[nextSlideIndex]; // 새 제목
        document.getElementById('slide-title').innerText = newTitle;
    });
}

function changeSlideTitle() {
    var titles = ['Temperature', 'CO2', 'Moist']; // 슬라이드별 제목
    var carousel = document.querySelector('#carouselExampleCaptions');

    // Carousel 요소가 존재하는지 확인
    if (!carousel) {
        console.error('Carousel element not found');
        return;
    }

    carousel.addEventListener('slide.bs.carousel', function (event) {
        var nextSlideIndex = event.to; // 다음 슬라이드의 인덱스
        var newTitle = titles[nextSlideIndex % titles.length]; // 새 제목, 배열 범위 초과 방지
        document.getElementById('slide-title').innerText = newTitle;
    });
}

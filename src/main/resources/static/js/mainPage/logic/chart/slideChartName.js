function changeSlideTitle() {
    let temperatureTitles = ['온도(일)', '온도(주)', '온도(월)'];
    let humidityTitles = ['습도(일)', '습도(주)', '습도(월)'];
    let illuminationTitles = ['조도(일)', '조도(주)', '조도(월)'];
    let co2Titles = ['Co2(일)', 'Co2(주)', 'Co2(월)'];

    let temperatureCarousel = document.querySelector('#temperatureCarouselCaptions');
    let humidityCarousel = document.querySelector('#humidityCarouselCaptions');
    let illuminationCarousel = document.querySelector('#illuminationCarouselCaptions');
    let co2Carousel = document.querySelector('#co2CarouselCaptions');

    // Carousel 요소가 존재하는지 확인
    if (!temperatureCarousel || !humidityCarousel || !illuminationCarousel || !co2Carousel) {
        console.error('Carousel element not found');
        return;
    }

    temperatureCarousel.addEventListener('slide.bs.carousel', function (event) {
        let nextSlideIndex = event.to; // 다음 슬라이드의 인덱스
        document.getElementById('temperature-title').innerText = temperatureTitles[nextSlideIndex % temperatureTitles.length];
    });
    humidityCarousel.addEventListener('slide.bs.carousel', function (event) {
        let nextSlideIndex = event.to; // 다음 슬라이드의 인덱스
        document.getElementById('humidity-title').innerText = humidityTitles[nextSlideIndex % humidityTitles.length];
    });
    illuminationCarousel.addEventListener('slide.bs.carousel', function (event) {
        let nextSlideIndex = event.to; // 다음 슬라이드의 인덱스
        document.getElementById('illumination-title').innerText = illuminationTitles[nextSlideIndex % illuminationTitles.length];
    });
    co2Carousel.addEventListener('slide.bs.carousel', function (event) {
        let nextSlideIndex = event.to; // 다음 슬라이드의 인덱스
        document.getElementById('co2-title').innerText = co2Titles[nextSlideIndex % co2Titles.length];
    });
}

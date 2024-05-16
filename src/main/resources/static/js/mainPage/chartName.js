function changeSlideTitle() {
    let dayTitles = ['온도(일)', '습도(일)', '조도(일)', 'Co2(일)'];
    let weekTitles = ['온도(주)','습도(주)','조도(주)' ,'Co2(주)'];
    let monthTitles = ['온도(월)','습도(월)' ,'조도(월)', 'Co2(월)'];

    let dayCarousel = document.querySelector('#dayCarouselCaptions');
    let weekCarousel = document.querySelector('#weekCarouselCaptions');
    let monthCarousel = document.querySelector('#monthCarouselCaptions');

    // Carousel 요소가 존재하는지 확인
    if (!dayCarousel || !weekCarousel || !monthCarousel) {
        console.error('Carousel element not found');
        return;
    }

    dayCarousel.addEventListener('slide.bs.carousel', function (event) {
        let nextSlideIndex = event.to; // 다음 슬라이드의 인덱스
        document.getElementById('day-title').innerText = dayTitles[nextSlideIndex % dayTitles.length];
    });
    weekCarousel.addEventListener('slide.bs.carousel', function (event) {
        let nextSlideIndex = event.to;
        document.getElementById('week-title').innerText = weekTitles[nextSlideIndex % weekTitles.length];
    });
    monthCarousel.addEventListener('slide.bs.carousel', function (event) {
        let nextSlideIndex = event.to;
        document.getElementById('month-title').innerText = monthTitles[nextSlideIndex % monthTitles.length];
    });
}

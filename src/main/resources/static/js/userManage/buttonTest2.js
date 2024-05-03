
document.addEventListener('DOMContentLoaded', function () {

    // const rowsPerPage = 3; // size
    //
    // const rows = document.querySelectorAll('#permitUser tbody tr')
    //
    // const rowsCount = rows.length; // length/size + 올림
    //
    // const pageCount = Math.ceil(rowsCount/rowsPerPage);

    const numbers = document.querySelector('#numbers');


//페이지네이션 생성

    /*
    대상.innetHTMK = 코드

    for 반복문 사용
    for(초기값; 조건; 증감)
    {
    }
     */


    for(let i = 1; i <= totalPages ; i++) {

        // <li><a href="" > '+i+' </a></li>

        // numbers.innerHTML = numbers.innerHTML + `<li><a href=""> ${i} </a></li>`;
        //numbers.innerHTML +=`<li><a href=""> ${i} </a></li>`;
        numbers.innerHTML += `<li><a href="?page=${i - 1}&size=${rowsPerPage}"> ${i} </a></li>`;


    }





});

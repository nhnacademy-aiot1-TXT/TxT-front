


    //
    // const rows = document.querySelectorAll('#permitUser tbody tr')
    //
    // const rowsCount = rows.length; // length/size + 올림
    //
    // const pageCount = Math.ceil(rowsCount/rowsPerPage);

    const rowsPerPage = 7; // size
    const numbers2 = document.querySelector('#numbers');


//페이지네이션 생성

    /*
    대상.innetHTMK = 코드

    for 반복문 사용
    for(초기값; 조건; 증감)
    {
    }
     */

    //
    numbers2.innerHTML += `<li  class="page-item"><a href="?page=${0}&size=${rowsPerPage}"> << </a></li>`;

    for(let i = 1; i <= totalPages ; i++) {

        // <li><a href="" > '+i+' </a></li>
        // numbers.innerHTML = numbers.innerHTML + `<li><a href=""> ${i} </a></li>`;
        //numbers.innerHTML +=`<li><a href=""> ${i} </a></li>`;
        numbers2.innerHTML += `<li><a href="?page=${i - 1}&size=${rowsPerPage}"> ${i} </a></li>`;
    }

    numbers2.innerHTML += `<li  class = "last"><a href="?page=${totalPages - 1 }&size=${rowsPerPage}"> >> </a></li>`;



    const numberBtn = numbers2.querySelectorAll('a');
    numberBtn.log(numberBtn);






    numberBtn.forEach((item,idx) => {
            item.addEventListener('click', ()=>{
                for(let nb of numberBtn){
                    nb.classlist.remove('active');
                }

            })
    })






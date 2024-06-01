




//해야 하는 것
//유저 리스트의 버튼을 클릭할때


// signupApproval.js

document.addEventListener('DOMContentLoaded', function () {
    const approveButton = document.querySelector('.btn-signup-approve');

    if (approveButton) {
        approveButton.addEventListener('click', function (event) {
            event.preventDefault();

            // 회원가입 승인 로직을 여기에 작성하세요.
            // 예: 서버로 승인 요청을 보내는 AJAX 호출 등


        });
    }
});

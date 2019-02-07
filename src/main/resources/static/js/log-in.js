var passwordInput = document.getElementById("passwordInput");
var usernameInput = document.getElementById("usernameInput");
var submitBtn = document.getElementById("submitBtn");


if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

console.log("hello")

}

submitBtn.addEventListener("click", ()=>{

//    let loginReqDto = {username:usernameInput.value, password:usernameInput.value}
//
//    submitLogin(loginReqDto);

})

function submitLogin(data){

let url = 'http://localhost:8080/login';

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers:{
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then((data) => {
            console.log("DATA FROM CREATING NEW USER REQUEST");
            console.log(data);

        })
}
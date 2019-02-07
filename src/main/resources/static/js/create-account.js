var usernameInput = document.getElementById("usernameInput");
var passwordInput = document.getElementById("passwordInput");
var confirmPasswordInput = document.getElementById("confirmPasswordInput");
var submitButton = document.getElementById("submitButton");


if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

}

submitButton.addEventListener("click", (event)=>{
    event.preventDefault();
    let newUserObj;
    console.log(usernameInput.value);
    console.log(passwordInput.value);
    console.log(confirmPasswordInput.value);
    console.log(checkNoEmptyFields())
    if(checkNoEmptyFields() == true || checkBothPasswordsMatch() == false){
        //some error message
        console.log("error")
    } else {
        newUserObj = {username:usernameInput.value, password:passwordInput.value, confirmPassword:confirmPasswordInput.value}
        console.log("creating user")
        createNewUser(newUserObj);
    }
})

function checkNoEmptyFields(){
    return passwordInput.value.length == 0 || confirmPasswordInput.value.length == 0 || usernameInput.value.length == 0;
}

function checkBothPasswordsMatch(){
    return passwordInput.value == confirmPasswordInput.value;
}

function createNewUser(data){

console.log("CONFIRMING CREDENTIALS");

    let url = 'http://localhost:8080/create-user';

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

        href.location = "http://localhost:8080/login"

        })


}
var userNameOfPlayer, inputUsernameLogin, inputPasswordLogin, submitButtonLogin, userData,
    inputUsernameCreate, inputPasswordCreate, inputConfirmPassword, submitButtonCreate, trait,
    agilityTraitBtn, strengthTraitBtn, intellectTraitBtn, agilityCard, intellectCard, strengthCard,
    dataForAccountCreation, password1, password2, btnDiv, warningDiv, loginFormDiv, loginBtn, createAccountBtn;

trait = "";
inputUsernameLogin = document.getElementById("inputUsernameLogin");
inputPasswordLogin = document.getElementById("inputPasswordLogin");
submitButtonLogin = document.getElementById("submitButtonLogin");
inputUsernameCreate = document.getElementById("inputUsernameCreate");
inputPasswordCreate = document.getElementById("inputPasswordCreate");
inputConfirmPassword = document.getElementById("inputConfirmPassword");
submitButtonCreate = document.getElementById("submitButtonCreate");
agilityTraitBtn = document.getElementById("agilityTrait");
strengthTraitBtn = document.getElementById("strengthTrait");
intellectTraitBtn = document.getElementById("intellectTrait");
agilityCard = document.getElementById("agilityCard");
intellectCard = document.getElementById("intellectCard");
strengthCard = document.getElementById("strengthCard");
btnDiv = document.getElementById("btnDiv");
warningDiv = document.getElementById("warningDiv");
loginFormDiv = document.getElementById("loginFormDiv");
createFormDiv = document.getElementById("createFormDiv");
createAccountBtn = document.getElementById("createAccountBtn");
loginBtn = document.getElementById("loginBtn");

warningDiv.style.display = "none";
loginFormDiv.style.display = "none";
createFormDiv.style.display = "none";

if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {


}

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////OPTIONS/////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


createAccountBtn.addEventListener("click", (event)=>{

    warningDiv.style.display = "none";

    loginFormDiv.style.display = "none";

    createFormDiv.style.display = "block";

});

loginBtn.addEventListener("click", (event)=>{

    warningDiv.style.display = "none";

    loginFormDiv.style.display = "block";

    createFormDiv.style.display = "none";

});



////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////LOGIN///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


submitButtonLogin.addEventListener("click", (event)=>{

    warningDiv.style.display = "none";

    event.preventDefault();//PREVENTS SUBMIT BUTTON FROM TRYING TO SEND REQUEST

    if (inputUsernameLogin.value == "" || inputPasswordLogin.value == "") {

        warningDiv.innerHTML = "<strong>YOU MUST FILL IN ALL FIELDS</strong>";

        warningDiv.style.width = "30%";

        warningDiv.style.margin = "auto";

        warningDiv.style.display = "block";

        console.log("USER LEFT A FIELD BLANKs")

    } else {

        formingUserForLoginData();

        confirmUserCredentials(userData);

    }
});


function formingUserForLoginData(){

    console.log("FORMING USER DATA");

    userData = {userName: inputUsernameLogin.value, password: inputPasswordLogin.value};

    userNameOfPlayer = inputUsernameLogin.value;

    console.log(userData)

}


function confirmUserCredentials(data) {

    console.log("CONFIRMING CREDENTIALS");

    let url = 'http://localhost:8080/userlogin';

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

            inputUsernameLogin.value = "";

            inputPasswordLogin.value = "";

            console.log("DATA FROM CONFIRMING CREDENTIALS REQUEST " + data);

            handleDataFromConfirmRequest(data);

        })

}

function handleDataFromConfirmRequest(data) {

    console.log(data);

    if (data.success) {

        sessionStorage.setItem("username", JSON.stringify(userNameOfPlayer));

        sessionStorage.setItem("token", JSON.stringify(data.token.tokie));

        location.href = "http://localhost:8080/hub";


    } else {

        console.log("Credentials were wrong try again");

        warningDiv.style.display = "block";

    }
}




////////////////////////////////////////////////////////////////////////////////
///////////////////////////////ACCOUNT CREATION/////////////////////////////////
////////////////////////////////////////////////////////////////////////////////



agilityTraitBtn.addEventListener("click", (event)=>{

    event.preventDefault();//PREVENTS SUBMIT BUTTON FROM TRYING TO SEND REQUEST

    trait = "AGILITY";

    agilityCard.style.border = "thick solid #32CD32";

    strengthCard.style.border = "thick solid #000000";

    intellectCard.style.border = "thick solid #000000";

});

strengthTraitBtn.addEventListener("click", (event)=>{

    event.preventDefault();//PREVENTS SUBMIT BUTTON FROM TRYING TO SEND REQUEST

    trait = "STRENGTH";

    strengthCard.style.border = "thick solid #32CD32";

    agilityCard.style.border = "thick solid #000000";

    intellectCard.style.border = "thick solid #000000";

});


intellectTraitBtn.addEventListener("click", (event)=>{

    event.preventDefault();//PREVENTS SUBMIT BUTTON FROM TRYING TO SEND REQUEST

    trait = "INTELLECT";

    intellectCard.style.border = "thick solid #32CD32";

    agilityCard.style.border = "thick solid #000000";

    strengthCard.style.border = "thick solid #000000";

});

submitButtonCreate.addEventListener("click", (event)=>{

    warningDiv.style.display = "none";

    event.preventDefault();//PREVENTS SUBMIT BUTTON FROM TRYING TO SEND REQUEST

    userNameOfPlayer = inputUsernameCreate.value;

    password1 = inputPasswordCreate.value;

    password2 = inputConfirmPassword.value;

    if (trait.length == 0 || userNameOfPlayer.length == 0 || password1.length == 0 || password2.length == 0) {

        warningDiv.innerHTML = "<strong>YOU MUST FILL IN ALL FIELDS</strong>";

        warningDiv.style.margin = "auto";

        warningDiv.style.display = "block";

        console.log("FIELDS HAVE BEEN LEFT BLANK")
    }
    else {

        if (checkPasswordsEqual(password1, password2)) {

            formDataForAccountCreation();

            sendAccountCreationDataToServer(dataForAccountCreation);

        } else {

            warningDiv.innerHTML = "<strong>YOUR PASSWORDS MUST MATCH</strong>";

            warningDiv.style.width = "30%";

            warningDiv.style.margin = "auto";

            warningDiv.style.display = "block";

            console.log("YOUR PASSWORDS DO NOT MATCH");

        }
    }
});

function checkPasswordsEqual(firstPassword, secondPassword) {

    if (firstPassword == secondPassword) {

        return true;

    } else {

        return false;
    }
}

function formDataForAccountCreation(){

    dataForAccountCreation = {username: userNameOfPlayer, password: password1, traitType: trait};

    console.log(dataForAccountCreation);

}

function sendAccountCreationDataToServer(data) {

    let url = 'http://localhost:8080/createAccount';

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
            console.log("DATA FROM ACCOUNT CREATION REQUEST");
            console.log(data);
            handleDataFromAccountCreationRequest(data);

        })

}

function handleDataFromAccountCreationRequest(data) {

    if (data.success) {

        sessionStorage.setItem("username", JSON.stringify(userNameOfPlayer));

        sessionStorage.setItem("token", JSON.stringify(data.token.tokie));

        location.href = "http://localhost:8080/hub";


    } else {

        warningDiv.style.width = "30%";

        warningDiv.style.margin = "auto";

        warningDiv.innerHTML = "<strong>YOUR CREDENTIALS WERE NOT GOOD ENOUGH</strong>";

        warningDiv.style.display = "block";

    }
}
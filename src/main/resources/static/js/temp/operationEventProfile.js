var operationData, playerData, playerUserName;


if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}


function ready () {

//    checkAndInitializeDataFromSessionStorage();

//    setInterval(checkWhenUserWasLastActive, 300000)//5 minutes

    playerData = JSON.parse(sessionStorage.getItem("playerData"));

    operationData = JSON.parse(sessionStorage.getItem("operationData"));

    playerUserName = playerData.userName;

    populateOperationProfile(operationData);

    tokenX = JSON.parse(sessionStorage.getItem("token"))

}


function populateOperationProfile(data) {

    console.log("POPULATE OPERATION PROFILE");

    let nameHolder = document.getElementById("nameHolder");
    let levelHolder = document.getElementById("levelHolder");
    let durationHolder = document.getElementById("durationHolder");
    let imageHolder = document.getElementById("imageHolder");
    let experienceHolder = document.getElementById("experienceHolder");
    let moneyHolder = document.getElementById("moneyHolder");

    nameHolder.innerHTML = "Operation: " + data.name;
    levelHolder.innerHTML = "Level: " + data.level;
    durationHolder.innerHTML = "Duration: " + data.duration + " minutes";
    imageHolder.src = data.imageUrl;
    experienceHolder.innerHTML = "Experience: " + data.experience;
    moneyHolder.innerHTML = "Money: £" + data.money;
    checkIfBagIsFull();
}


//function logout() {
//
//    console.log("LOGGING OUT");
//
//    playerData.isOnline = false;
//
//    saveUserDetails(playerData);
//
//    sessionStorage.removeItem("playerData");
//
//    sessionStorage.removeItem("otherUser");
//
//    location.href = './index.html'
//}


function direction(data) {

    if (data.success) {

        sessionStorage.setItem('playerData', JSON.stringify(data.avatar));

        return location.href = "http://localhost:8080/hub";

    }
}

///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////REQUESTS///////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////


function goOnOperation(data) {

    let url = "http://localhost:8080/createEvent";

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
            console.log("DATA FROM CREATING OPERATION EVENT");
            direction(data);
        })
}


//function saveUserDetails(data) {
//
//    let url = 'http://localhost:8080/api/update/player';
//
//    fetch(url, {
//        method:'PUT',
//        body: JSON.stringify(data),
//        headers:{
//            'Content-Type': 'application/json'
//        }
//    })
//        .then(res => res.json())
//        .catch(error => console.error('Error:', error))
//        .then((data) => {
//
//        })
//}


///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////BUTTON EVENT LISTENERS//////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////


//document.getElementById("logoutBtn").addEventListener("click",() => {
//
//    logout();
//
//});


document.getElementById("goOnOperationBtn").addEventListener("click",() => {

    let dataX = {sender: playerUserName, content: "createOperationEvent", option: "", event: operationData};

    goOnOperation(dataX);

    //need to send request to mission  end point
    //player field isOnMission needsto be set to true
    //this will stop him from being able to engage in any activities like battles or hospital

});


//document.getElementById("backToUserProfileBtn").addEventListener("click",() => {
//
//     location.href ="./UserAccountTemplate.html"
//
// });

////////////////////////////////////////////////////////////////////////////////////////
///////////////////FUNCTION TO CHECK SESSION STORAGE HAS USER INFO//////////////////////
///////////////////////////////////////////////////////////////////////////////////////


//function checkAndInitializeDataFromSessionStorage() {
//
//    console.log("checking session storage");
//
//    playerData = JSON.parse(sessionStorage.getItem("playerData"));
//
//    if (playerData != null) {
//
//        operationData = JSON.parse(sessionStorage.getItem("operationData"));
//
//        console.log(playerData);
//
//        console.log(operationData);
//
//        populateOperationProfile(operationData);
//
//    } else {
//
//        location.href ="./index.html";
//
//    }
//}


function checkIfBagIsFull() {

    if (playerData.isBagFull) {

        confirm("BAG IS FULL, IF YOU GO ON ANOTHER OPERATION YOU WILL NOT RECEIVE ANY ITEMS, SELL ITEMS IN YOUR BAG TO MAKE SPACE");

    }
}


////////////////////////////////////////////////////////////////////////////////////////
//////FUNCTION TO CHECK USERS ACTIVITY IF MORE THAN 15 MINUTES INACTIVE LOG OUT/////////
///////////////////////////////////////////////////////////////////////////////////////


//function checkWhenUserWasLastActive() {
//
//    if ((new Date().getTime() - lastActive) >= 900000) {//15 minutes 900000
//
//        logout();
//
//    }
//}
//
//function setLastActive() {
//
//    lastActive = new Date().getTime();
//
//    console.log((new Date(lastActive)).toDateString());
//
//    console.log(lastActive);
//
//}


////////////////////////////////////////////////////////////////////////////////////////
//////FUNCTION TO SET WHEN USER IS LAST ACTIVE WHEN EVER USER CLICKS ON SCREEN/////////
///////////////////////////////////////////////////////////////////////////////////////


//document.onclick = function(event) {
//
//    setLastActive();
//
//};

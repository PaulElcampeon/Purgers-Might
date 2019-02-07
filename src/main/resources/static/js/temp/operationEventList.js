var playerData;

if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);

}

function ready () {

    playerData = JSON.parse(sessionStorage.getItem("playerData"));

    //setInterval(checkWhenUserWasLastActive, 300000)//5 minutes

    let dataX = {name: playerData.userName , playerLevel: playerData.level, numberOfOperations: 10};

    getOperationList(dataX);

}

function populatePage(data){

    let listOfButtons = document.getElementsByClassName("operationInfo");

    for(let i = 0; i < data.length; i++){

        listOfButtons[i].innerHTML = data[i].name+"<br> Level: " + data[i].level + "<br> Experience: " + data[i].experience+"" +

        "<br> Duration: " + data[i].duration + " minutes<br> Money: £" + data[i].money + "<br> Prize type: " + data[i].prizeType + "" +

            "<br> Chance Of Success: "+data[i].chanceOfSuccess+" (If same level as operation)";

        listOfButtons[i].addEventListener("click",()=>{

            sessionStorage.setItem('operationData', JSON.stringify(data[i]));

            location.href = "http://localhost:8080/operationprofile";

        })
    }

    for(let i = data.length; i < 10; i++){

        listOfButtons[i].style.display = "none";

     }
}

function next10() {

    let dataX = {name: playerData.userName , playerLevel: playerData.level, numberOfOperations: 10};

    getOperationList(dataX);
}


//function logoutPlayerAction(){
//
//    sessionStorage.removeItem("playerData");
//
//    sessionStorage.removeItem("token");
//
//    sessionStorage.removeItem("playerUsername");
//
//    location.href = "http://localhost:8080/home";;
//
//}
//
//
//function logout(){
//
//    console.log("LOGGING OUT");
//
//    logoutPlayer(playerData);
//
//}

///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////REQUESTS///////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////

function getOperationList(data){

    let url = "http://localhost:8080/generateOperationEvents";

        fetch(url, {
            method:'PUT',
            body: JSON.stringify(data),
            headers:{
                'Content-Type': 'application/json'
            }
        })
            .then(res => res.json())
            .catch(error => console.error('Error:', error))
            .then((data) => {
                console.log(data);
                populatePage(data.operationEvents);
            })
}

//function logoutPlayer(data) {
//
//    let url = 'http://localhost:8080/api/logout/player';
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
//            console.log(data);
//            logoutPlayerAction();
//
//
//        })
//}



 ///////////////////////////////////////////////////////////////////////////////////////
 ///////////////////////////BUTTON EVENT LISTENERS//////////////////////////////////////
 ///////////////////////////////////////////////////////////////////////////////////////

//document.getElementById("backToUserProfileBtn").addEventListener("click",()=>{
//
//     location.href ="./UserAccountTemplate.html"
//
//})
//
//document.getElementById("logoutBtn").addEventListener("click",()=>{
//
//    logout();
//
//})

document.getElementById("next10").addEventListener("click",()=>{

    next10();

})


 ////////////////////////////////////////////////////////////////////////////////////////
 //////FUNCTION TO CHECK USERS ACTIVITY IF MORE THAN 10 MINUTES INACTIVE LOG OUT/////////
 ///////////////////////////////////////////////////////////////////////////////////////

//function checkWhenUserWasLastActive() {
//
//     if ((new Date().getTime() - lastActive) >= 600000) {//10 minutes 900000
//
//        logout();
//
//     }
//}
//
//function setLastActive() {
//
//     lastActive = new Date().getTime();
//
//     console.log((new Date(lastActive)).toDateString())
//
//     console.log(lastActive);
//}


////////////////////////////////////////////////////////////////////////////////////////
//////FUNCTION TO SET WHEN USER IS LAST ACTIVE WHEN EVER USER CLICKS ON SCREEN/////////
///////////////////////////////////////////////////////////////////////////////////////


//document.onclick = function(event) {
//
//    setLastActive();
//
//};
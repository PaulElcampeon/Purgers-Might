var playerUserName, widgetRow, playerRows, recipient, waitingForResponse,
    waitingForResponseTimeout, waitingForResponseAnimationInterval, waitingResponseDiv,
    waitingResponseCounter, playerData;

var stompClient = null;

waitingForResponse = false;
waitingResponseCounter = 20;
widgetRow = document.getElementById("playerRows");
playerRows = document.getElementById("playerRows");
waitingResponseDiv = document.getElementById("waitingResponseDiv");

if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

    playerUserName = JSON.parse(sessionStorage.getItem("username"));

    let tempDataForPlayerList = {sender: playerUserName, content: "playerList", option: ""};

    getPossiblePlayersForPvp(tempDataForPlayerList);

    connect();

}


function getPossiblePlayersForPvp(data) {


    let url = "http://localhost:8080/eventfreeplayers";

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

            console.log("DATA FROM GETTING PVP PLAYERS REQUEST");

            handleResponse(data);
        })
}

function handleResponse(data) {

    if (data.success) {

        populateEventFreePlayers(data.players)

    } else {

        console.log("SOMETHING WENT WRONG");

    }
}

function populateEventFreePlayers(data) {

    for (let x = 0; x < data.length; x++) {

        let tempDiv = document.createElement("div");
        let tempImg = document.createElement("img");
        let tempH6 = document.createElement("h6");
        let tempBtn = document.createElement("button");

        tempH6.innerHTML = "Name: " + data[x].userName + "<br> Level: " + data[x].level + " <br> Trait: " + data[x].traitType;
        tempImg.src = data[x].imgUrl;
        tempImg.style.height = "60px";
        tempImg.style.width = "60px";
        tempDiv.classList.add('widgetItem', 'col-sm-3');
        tempBtn.innerHTML = "SEND BATTLE INVITE";
        tempBtn.addEventListener("click", ()=> {

            if (waitingForResponse) {

            } else {

                let dataX = {sender: playerUserName, content: "battleEvent", option: ""};

                let fightersNames = {attacker: playerUserName, defender: data[x].userName};

                recipient = data[x].userName;

                sessionStorage.setItem("fighters", JSON.stringify(fightersNames));

                eventInvite(dataX);

                waitingForResponse = true;

                initializeWaitingResponseAnimation();

            }

        });

        tempDiv.appendChild(tempImg);
        tempDiv.appendChild(tempH6);
        tempDiv.appendChild(tempBtn);

        playerRows.appendChild(tempDiv);

    }

}

function initializeWaitingResponseAnimation() {

    waitingForResponseAnimationInterval = setInterval(startWaitingResponseAnimation, 1000);

    waitingResponseDiv.style.display = "block";

    waitingForResponseTimeout = setTimeout(hideWaitingResponse, 20000)//20 seconds then we reset waitingResponse

}

function startWaitingResponseAnimation() {

    waitingResponseDiv.innerHTML = "WAITING ON PLAYER RESPONSE " + waitingResponseCounter;

    waitingResponseCounter--;

}

function hideWaitingResponse() {

    clearInterval(waitingForResponseAnimationInterval);

    clearTimeout(waitingForResponseTimeout);

    waitingResponseDiv.style.display = "none";

    waitingForResponse = false;
}


//////////////////////////////WEBSOCKET STUFF/////////////////////////////////////



function connect() {
    var socket = new SockJS('/gs');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        stompClient.subscribe('/topic/eventInvite/'+playerUserName, function (data) {
            console.log(JSON.parse(data.body));
            checkEventInvite(JSON.parse(data.body));
        });

    });
}

function checkEventInvite(data) {


    if (data.content == "battleEvent" && data.option.length > 1) {

        respondToBattleEvent(data);

    } else {

        console.log("WE HAVE JUST REFUSED A EVENT")
    }


}


function respondToBattleEvent(data) {

    if (data.option == "accept") {

        hideWaitingResponse();

        let dataForBattleEventCreation = {sender:playerUserName, content: "createBattleEvent", option:data.sender};

        createBattleEvent(dataForBattleEventCreation);

    }

    if (data.option == "refuse") {

        hideWaitingResponse();

    }

}

function createBattleEvent(data) {

    console.log("SENDING REQUEST FOR BATTLE");

    let url = "http://localhost:8080/createEvent";

    fetch(url, {
        method:'POST',
        body: JSON.stringify(data),
        headers:{
            'Content-Type': 'application/json'
        }})
        .then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then(data => {
            console.log("DATA FROM SENDING REQUEST TO CHECK FOR BATTLE REQUEST");
            console.log(data);
            updatePlayerData();
            displayNoticeDivStartCounter();

        })
}

function updatePlayerData() {

    playerData = JSON.parse(sessionStorage.getItem("playerData"));

    playerData.inEvent = true;

    playerData.eventId = playerUserName + "battleEvent";

    sessionStorage.setItem("playerData", JSON.stringify(playerData));
}

function displayNoticeDivStartCounter() {

    waitingResponseDiv.innerHTML = "OTHER PLAYER HAS ACCEPTED YOUR INVITE YOU WILL NOW BE TELEPORTED TO FIGHTING ROOM";

    waitingResponseDiv.style.display = "block";

    setTimeout(teleportToFightingRoom, 10000)//10 seconds then teleport to fighting room

}

function teleportToFightingRoom() {

    location.href = "http://localhost:8080/fightingroom";

}

function eventInvite(data) {

    stompClient.send("/app/eventInvite/"+recipient, {}, JSON.stringify(data));

}
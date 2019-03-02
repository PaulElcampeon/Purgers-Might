var checkForBeingMatchedInterval, avatar, findingAMatch, counter,
    timerOuterDiv, timerDiv, timerTag, timeToHomePage, endTime;

timerOuterDiv = document.getElementById("timerOuterDiv");
timerDiv = document.getElementById("timerDiv");
timerTag = document.getElementById("timerTag");
findingAMatch = document.getElementById("findingAMatch");
counter = 0;
timeToHomePage = 23;
counterForTimeToHomePage = 0;

if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

    avatar = JSON.parse(sessionStorage.getItem("avatar"));

    let joinPvpEventReqDto = {username: avatar.username}

    joinPvpEvent(joinPvpEventReqDto);

    setInterval(revolvingMessage, 2000);
}

function revolvingMessage(){

    if(counter == 7) {

        counter = 0;

        findingAMatch.innerHTML = "Finding a match for you"

    } else {

        findingAMatch.innerHTML = findingAMatch.innerHTML + "."

        counter++;
    }
}

function joinPvpEvent(data){

    checkForBeingMatchedInterval = setInterval(checkForBeingMatchedInitializer, 4000);

    goBackToHomePageTimer();

    let url = "../pvp-event-service/join-event-queue";

        fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers:{
                'Content-Type': 'application/json'
            }
        })
//            .then(res => res.json())
//            .catch(error => console.error('Error:', error))

}

function checkForBeingMatchedInitializer(){

    let checkForInEventReqDto = {username: avatar.username};

    checkForBeingMatched(checkForInEventReqDto);
}

function checkForBeingMatched(data) {

    let url = "../pvp-event-service/check-for-in-event";

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

            if(data.inEvent){

              clearInterval(checkForBeingMatchedInterval);

                if(avatar.username == data.pvpEvent.player1.username){

                    sessionStorage.setItem("avatar", JSON.stringify(data.pvpEvent.player1));

                    sessionStorage.setItem("event", JSON.stringify(data.pvpEvent));

                } else {

                    sessionStorage.setItem("avatar", JSON.stringify(data.pvpEvent.player2));

                    sessionStorage.setItem("event", JSON.stringify(data.pvpEvent));
                }

                location.href = "../pvp-room"
            }
        })
}

function removeFromPvpQueue(data){

    let url = "../pvp-event-service/remove-from-queue";

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

        if(data.success) {

            location.href = "../home"

        } else {

            if(avatar.username == data.pvpEvent.player1.username){

                sessionStorage.setItem("avatar", JSON.stringify(data.pvpEvent.player1));

                sessionStorage.setItem("event", JSON.stringify(data.pvpEvent));

                goToHomePage();

            } else {

                sessionStorage.setItem("avatar", JSON.stringify(data.pvpEvent.player2));

                sessionStorage.setItem("event", JSON.stringify(data.pvpEvent));
            }

            location.href = "../pvp-room";
        }

        })
}

function goBackToHomePageTimer(){

    setTimeout(removeFromPvpQueueInitializer, 23000);

    setInterval(populateTimer, 1000);
}

function removeFromPvpQueueInitializer(){

    clearInterval(checkForBeingMatchedInterval);

    let removeFromPvpQueueReqDto = {username: avatar.username};

    removeFromPvpQueue(removeFromPvpQueueReqDto);
}

function goToHomePage(){

    location.href = "../home";
}

function populateTimer() {

    counterForTimeToHomePage++;

    let timerPercentage = ((counterForTimeToHomePage/timeToHomePage) * 100).toFixed(0);

    timerDiv.style.width = timerPercentage + "%";

    timerTag.innerHTML = timerPercentage+"%";
}
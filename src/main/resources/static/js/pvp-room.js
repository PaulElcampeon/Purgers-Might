var fighter1Div, fighter2Div, playerUsername, whosTurn, playerData, eventIdX, getBattleEventInterval,
    counterForBattleEventChecks, otherFightersName, battleMessageDiv, battleMessageTag, fighterRow,
    healthPercentage, mannaPercentage, backToHome, endTime, actionDiv, checkTimeActingInterval;

var stompClient = null;
var socket = null;

fighter1Div = document.getElementById("fighter1Div");
fighter2Div = document.getElementById("fighter2Div");
battleMessageDiv = document.getElementById("battleMessageDiv");
battleMessageTag = document.getElementById("battleMessageTag");
fighterRow = document.getElementById("fighterRow");
backToHome = document.getElementById("backToHome");
counterForBattleEventChecks = 0;

if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready() {

    battleMessageDiv.style.display = "none";

    playerData = JSON.parse(sessionStorage.getItem("avatar"));

    playerUsername = playerData.username;

    eventIdX = playerData.eventId;

    getBattleEventInterval = setInterval(getBattleEvent, 3000);
}

class AttackPlayerReqDto {

    constructor(eventId, attackType, spellPosition) {
        this.eventId = eventId;
        this.attackType = attackType;
        this.spellPosition = spellPosition;
    }
}

function getBattleEvent() {

    let url = "../pvp-event-service/" + eventIdX;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then((data) => {

            console.log("DATA FROM GETTING USER DATA REQUEST");

            console.log(data);

            if (data.success) {

                if (data.pvpEvent == null) {

                    location.href = "../home";
                }

                clearInterval(getBattleEventInterval);//stop requesting for battleEvent

                populateRoom(data.pvpEvent);

                //CLEAR WAITING ANIMATION

                connect();

            } else {

                counterForBattleEventChecks++;

                if (counterForBattleEventChecks == 4) {

                    location.href = "../home";//IF WE HAVE CHECKED 4 TIMES AND STILL NOTHING WE GO BACK TO PROFILE PAGE

                }
            }
        });
}


function populateRoom(data) {

    fighter1Div.innerHTML = "";

    fighter2Div.innerHTML = "";

    whosTurn = data.whosTurn;

    console.log("WHOS TURN IS IT");

    console.log(whosTurn)

    if (whosTurn == playerUsername) {

        console.log("I AM THE INITIATOR");

        if (data.player1.username == playerUsername) {

            sessionStorage.setItem("playerData", JSON.stringify(data.player1));

            populateMyData(data.player1);

            populateOtherFighter(data.player2);

            otherFightersName = data.player2.username;

        } else {

            sessionStorage.setItem("playerData", JSON.stringify(data.player2));

            populateMyData(data.player2);

            populateOtherFighter(data.player1);

            otherFightersName = data.player1.username;
        }

        setTimes(data.timestamp);

        checkTimeActingInterval = setInterval(checkTimeActing, 2000)//start interval

    } else {

        console.log("I AM THE RECEIEVER");

        if (data.player1.username == playerUsername) {

            sessionStorage.setItem("playerData", JSON.stringify(data.player1));

            populateMyData(data.player1);

            populateOtherFighter(data.player2);

            otherFightersName = data.player2.username;

        } else {

            sessionStorage.setItem("playerData", JSON.stringify(data.player2));

            populateMyData(data.player2);

            populateOtherFighter(data.player1);

            otherFightersName = data.player1.username;
        }
    }

    console.log("MY NAME AGAIN IS");

    console.log(playerUsername);

    console.log("THE OTHER FIGHTER IS");

    console.log(otherFightersName);
}


function populateMyData(data, timestamp) {

    let tempImgUrl = document.createElement("img");

    tempImgUrl.src = data.imageUrl;

    tempImgUrl.style.width = "70px";

    tempImgUrl.style.height = "70px";

    let tempPlayerInfoDiv = document.createElement("div");

    tempPlayerInfoDiv.appendChild(populatePlayerInfoDiv(data));

    let tempHealthEtcDiv = document.createElement("div");

    tempHealthEtcDiv.appendChild(populateHealthEtc(data));

    actionDiv = document.createElement("div");

    actionDiv.setAttribute("id", "actionDiv");

    let attackUsingWeaponButton = document.createElement("button");

    attackUsingWeaponButton.innerHTML = "ATTACK USING WEAPON";

    attackUsingWeaponButton.addEventListener("click", () => {

        let attackPlayerReqDto = new AttackPlayerReqDto(eventIdX, "MELEE", 0);

        attack(attackPlayerReqDto);
    });

    let tempSpellDiv = document.createElement("div");

    tempSpellDiv.innerHTML = "";

    tempSpellDiv.classList.add("row");

    for (let x = 0; x < data.spellBook.spellList.length; x++) {

        tempSpellDiv.appendChild(populateSpell(data.spellBook.spellList[x], x, data));
    }

    actionDiv.appendChild(attackUsingWeaponButton);

    actionDiv.appendChild(tempSpellDiv);

    fighter1Div.appendChild(tempImgUrl);

    fighter1Div.appendChild(tempPlayerInfoDiv);

    fighter1Div.appendChild(tempHealthEtcDiv);

    fighter1Div.appendChild(actionDiv);

    if (whosTurn == playerUsername) {

        console.log("WE ARE SHOWING THE ACTIVE DIV");

        actionDiv.style.display = "block";

    } else {

        console.log("WE ARE HIDING THE ACTIVE DIV");

        actionDiv.style.display = "none";
    }
}

function populateOtherFighter(data) {

    let tempImgUrl = document.createElement("img");

    tempImgUrl.src = data.imageUrl;

    tempImgUrl.style.width = "70px";

    tempImgUrl.style.height = "70px";

    let tempPlayerInfoDiv = document.createElement("div");

    tempPlayerInfoDiv.appendChild(populatePlayerInfoDiv(data));

    let tempHealthEtcDiv = document.createElement("div");

    tempHealthEtcDiv.appendChild(populateHealthEtc(data));

    fighter2Div.appendChild(tempImgUrl);

    fighter2Div.appendChild(tempPlayerInfoDiv);

    fighter2Div.appendChild(tempHealthEtcDiv);
}

function populateSpell(data, indexOfAbility, myData) {

    let tempSpellDiv = document.createElement("div");

    tempSpellDiv.classList.add("col");

    let tempAbilityImg = document.createElement("img");

    let tempDetails = document.createElement("h6");

    let tempButton = document.createElement("button");

    tempAbilityImg.src = data.imageUrl;

    tempDetails.innerHTML = "Name: " + data.name + "<br>Type: " + data.spellType + "<br>Cost: " + data.mannaCost + "<br>Amount " + data.damagePoints;

    if ((data.spellType == "DAMAGE" && myData.manna.running >= data.mannaCost) || (data.spellType == "HEAL" && myData.manna.running >= data.mannaCost)) {

        tempButton.innerHTML = "USE ABILITY";

        tempButton.addEventListener("click", () => {

            let attackPlayerReqDto = new AttackPlayerReqDto(eventIdX, "SPELL", indexOfAbility);

            attack(attackPlayerReqDto);
        });

    } else {

        tempButton.innerHTML = "NOT ENOUGH ENERGY OR MANNA";
    }

    tempSpellDiv.appendChild(tempAbilityImg);

    tempSpellDiv.appendChild(tempDetails);

    tempSpellDiv.appendChild(tempButton);

    return tempSpellDiv;
}

function populateHealthEtc(data) {

    //NEED TO SORT OUT THE VALUE OF WIDTHS
    let tempHealthEtcDiv1 = document.createElement("div");

    let outerProgressDivHealth = document.createElement("div");
    outerProgressDivHealth.classList.add('m-1', 'p-0', 'col-sm', 'progress');
    let innerProgressDivHealth = document.createElement("div");
    innerProgressDivHealth.classList.add("progress-bar");
    populateHealthDiv(data);
    innerProgressDivHealth.style.width = healthPercentage + "%";
    outerProgressDivHealth.setAttribute("id", "healthDivOuter");
    innerProgressDivHealth.setAttribute("id", "healthDiv");
    let healthTag = document.createElement("p");
    healthTag.classList.add('p-0', 'm-0');
    healthTag.innerHTML = data.health.running + "/" + data.health.actual;
    innerProgressDivHealth.appendChild(healthTag);
    outerProgressDivHealth.appendChild(innerProgressDivHealth);

    let outerProgressDivManna = document.createElement("div");
    outerProgressDivManna.classList.add('m-1', 'p-0', 'col-sm', 'progress');
    let innerProgressDivManna = document.createElement("div");
    innerProgressDivManna.classList.add("progress-bar");
    populateMannaDiv(data);
    innerProgressDivManna.style.width = mannaPercentage + "%";
    outerProgressDivManna.setAttribute("id", "manaDivOuter");
    innerProgressDivManna.setAttribute("id", "manaDiv");
    let mannaTag = document.createElement("p");
    mannaTag.classList.add('p-0', 'm-0');
    mannaTag.innerHTML = data.manna.running + "/" + data.manna.actual;
    innerProgressDivManna.appendChild(mannaTag);
    outerProgressDivManna.appendChild(innerProgressDivManna);

    tempHealthEtcDiv1.appendChild(outerProgressDivHealth);

    tempHealthEtcDiv1.appendChild(outerProgressDivManna);

    return tempHealthEtcDiv1;
}

function populatePlayerInfoDiv(data) {

    let tempHtag = document.createElement("h4");

    tempHtag.innerHTML = "Name: " + data.username + " Level: " + data.level;

    return tempHtag;
}

function populateHealthDiv(data) {

    let baseHealth = data.health.actual;

    let runningHealth = data.health.running;

    healthPercentage = (runningHealth / baseHealth) * 100;
}

function populateMannaDiv(data) {

    let baseManna = data.manna.actual;

    let runningManna = data.manna.running;

    mannaPercentage = (runningManna / baseManna) * 100;
}

function setTimes(timestamp) {

    endTime = timestamp + 15000 //adding 15 seconds onto the timstamp
}

function checkTimeActing() {

    let currentDate = new Date();

    if (currentDate.getTime() >= endTime) {

        clearInterval(checkTimeActingInterval);//stop the interval

        actionDiv.style.display = "none";

        automaticMeleeAttack();
    }
}


//////////////////EVENT LISTENERS//////////////////

backToHome.addEventListener("click", () => {

    location.href = "../home"
})


//////////////////WEBSOCET STUFF//////////////////

function connect() {

    socket = new SockJS('/pvp-event');

    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {

        stompClient.subscribe('/topic/pvp-event/' + eventIdX, function (data) {

            handleResponseData(JSON.parse(data.body));

        });
    }, function (error) {

        console.log(error);
    });

}

function attack(data) {

    console.log("ATTACKING OTHER PLAYER");

    stompClient.send("/app/pvp-event/" + eventIdX, {}, JSON.stringify(data));
}

function quitFight(data) {

    console.log("QUITING FIGHT");

    stompClient.send("/app/pvp-event/" + eventIdX, {}, JSON.stringify(data));
}

function handleResponseData(data) {

    if (data.ended) {

        fighterRow.style.display = "none";

        if (data.winner == playerUsername) {

            battleMessageTag.innerHTML = "CONGRATULATIONS YOU HAVE WON THE BATTLE";

        } else {

            battleMessageTag.innerHTML = "YOU HAVE LOST THE BATTLE";

        }

        battleMessageDiv.style.display = "block";

    } else {

        populateRoom(data.pvpEvent);
    }
}

function automaticMeleeAttack() {

    let attackPlayerReqDto = new AttackPlayerReqDto(eventIdX, "MELEE", 0);//send automatic attack

    attack(attackPlayerReqDto);
}

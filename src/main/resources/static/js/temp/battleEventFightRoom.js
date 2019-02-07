var fighter1Div, fighter2Div, playerUserName, whosTurn, playerData, eventIdX, getBattleEventInterval,
    counterForBattleEventChecks, otherFightersName, battleMessageDiv, battleMessageTag, fighterRow,
    healthPercentage, manaPercentage, energyPercentage, backToHub;

var stompClient = null;

fighter1Div = document.getElementById("fighter1Div");
fighter2Div = document.getElementById("fighter2Div");
battleMessageDiv = document.getElementById("battleMessageDiv");
battleMessageTag = document.getElementById("battleMessageTag");
fighterRow = document.getElementById("fighterRow");
backToHub = document.getElementById("backToHub");
counterForBattleEventChecks = 0;

if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready() {

    battleMessageDiv.style.display = "none";

    playerData = JSON.parse(sessionStorage.getItem("playerData"));

    playerUserName = playerData.userName;

    eventIdX = playerData.eventId;

    getBattleEventInterval = setInterval(getBattleEventInitializer, 3000);

    console.log(playerUserName);

    //WE NEED A WAITING ANIMATION

}

function getBattleEventInitializer() {

    let dataX = {sender: playerUserName, content: "getBattleEvent", option: eventIdX};

    getBattleEvent(dataX);

}

function getBattleEvent(data) {

    let url = "http://localhost:8080/getEvent";

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
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

                populateRoom(data.event);

                clearInterval(getBattleEventInterval);//stop requesting for battleEvent

                //CLEAR WAITING ANIMATION

                connect();


            } else {

                counterForBattleEventChecks++;

                if (counterForBattleEventChecks == 4) {

                    location.href = "http://localhost:8080/hub";//IF WE HAVE CHECKED 4 TIMES AND STILL NOTHING WE GO BACK TO PROFILE PAGE

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

    if (data.initiator.userName == playerUserName) {

        console.log("I AM THE INITIATOR");

        sessionStorage.setItem("playerData", JSON.stringify(data.initiator));

        populateMyData(data.initiator);

        populateOtherFighter(data.receiver);

        otherFightersName = data.receiver.userName;


    } else {

        console.log("I AM THE RECEIEVER");

        sessionStorage.setItem("playerData", JSON.stringify(data.receiver));

        populateMyData(data.receiver);

        populateOtherFighter(data.initiator);

        otherFightersName = data.initiator.userName;

    }

    console.log("MY NAME AGAIN IS");

    console.log(playerUserName);

    console.log("THE OTHER FIGHTER IS");

    console.log(otherFightersName);


}


function populateMyData(data) {

    let tempImgUrl = document.createElement("img");

    tempImgUrl.src = data.imgUrl;

    tempImgUrl.style.width = "70px";

    tempImgUrl.style.height = "70px";

    let tempPlayerInfoDiv = document.createElement("div");

    tempPlayerInfoDiv.appendChild(populatePlayerInfoDiv(data));

    let tempHealthEtcDiv = document.createElement("div");

    tempHealthEtcDiv.appendChild(populateHealthEtc(data));

    let actionDiv = document.createElement("div");

    actionDiv.setAttribute("id", "actionDiv");

    let attackUsingWeaponButton = document.createElement("button");

    attackUsingWeaponButton.innerHTML = "ATTACK USING WEAPON";

    attackUsingWeaponButton.addEventListener("click", () => {

        let dataForBattleEventAttack = {
            name: playerUserName,
            attackType: "weapon",
            indexOfAbility: 0,
            eventId: eventIdX,
            victim: otherFightersName
        };

        attack(dataForBattleEventAttack);

    });


    let tempAbilityDiv = document.createElement("div");

    tempAbilityDiv.innerHTML = "";

    tempAbilityDiv.classList.add("row");

    for (let x = 0; x < data.abilities.length; x++) {

        tempAbilityDiv.appendChild(populateAbility(data.abilities[x], x, data));

    }

    actionDiv.appendChild(attackUsingWeaponButton);

    actionDiv.appendChild(tempAbilityDiv);

    fighter1Div.appendChild(tempImgUrl);

    fighter1Div.appendChild(tempPlayerInfoDiv);

    fighter1Div.appendChild(tempHealthEtcDiv);

    fighter1Div.appendChild(actionDiv);

    if (whosTurn == playerUserName) {

        console.log("WE ARE SHOWING THE ACTIVE DIV");

        actionDiv.style.display = "block";

    } else {

         console.log("WE ARE HIDING THE ACTIVE DIV");

         actionDiv.style.display = "none";

    }

}


function populateOtherFighter(data) {

    let tempImgUrl = document.createElement("img");

    tempImgUrl.src = data.imgUrl;

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

function populateAbility(data, indexOfAbility, myData) {

    let tempAbilityDiv = document.createElement("div");

    tempAbilityDiv.classList.add("col");

    let tempAbilityImg = document.createElement("img");

    let tempDetails = document.createElement("h6");

    let tempButton = document.createElement("button");

    tempAbilityImg.src = data.imgUrl;

    tempDetails.innerHTML = "Name: " + data.name + "<br>Type: " + data.abilityType + "<br>Cost: " + data.cost + "<br>Amount " + data.amount;

    if ((data.abilityType == "PHYSICAL_DAMAGE" && myData.runningEnergy >= data.cost) || (data.abilityType == "SPELL_DAMAGE" && myData.runningMana >= data.cost) || ((data.abilityType == "BUFF_DEFENSE" || data.abilityType == "BUFF_DAMAGE" || data.abilityType == "DEBUFF_DEFENSE" || data.abilityType == "DEBUFF_DAMAGE") && (myData.runningMana >= data.cost || myData.runningEnergy >= data.cost)) || (data.abilityType == "HEAL" && myData.runningMana >= data.cost )) {

        tempButton.innerHTML = "USE ABILITY";

        tempButton.addEventListener("click", () => {

            let dataForBattleEventAttack = {
                name: playerUserName,
                attackType: "ability",
                indexOfAbility: indexOfAbility,
                eventId: eventIdX,
                victim: otherFightersName
            };

            attack(dataForBattleEventAttack);


            //ATTACKS DIRECTED TO OPPONENT
            // if (data.abilityType == PHYSICAL_DAMAGE || data.abilityType == SPELL_DAMAGE || data.abilityType == DEBUFF_DEFENSE || data.abilityType == DEBUFF_DAMAGE) {
            //
            //     let dataForBattleEventAttack = {name:playerUserName, indexOfAbility: indexOfAbility, eventId: eventIdX, victim: otherFightersName};
            //
            // }

            // if (data.abilityType == HEAL) {
            //
            //     //GET TEAM AND DO A FOR LOOP OF ALL TEAM MEMBERS PUTTING THEM INTO BUTTONS AND APPENDING THEM TO A DIV BUT WE CAN HIDE IT AND SHOW IT WHEN THEY CLICK ON THE HEAL BUTTON
            // }


        });
    } else {

        tempButton.innerHTML = "NOT ENOUGH ENERGY OR MANA";

    }

    tempAbilityDiv.appendChild(tempAbilityImg);

    tempAbilityDiv.appendChild(tempDetails);

    tempAbilityDiv.appendChild(tempButton);

    return tempAbilityDiv;
}

function populateHealthEtc(data) {

    //NEED TO SORT OUT THE VALUE OF WIDTHS
    let tempHealthEtcDiv1 = document.createElement("div");

    let outerProgressDivHealth = document.createElement("div");
    outerProgressDivHealth.classList.add('m-1', 'p-0', 'col-sm', 'progress');
    let innerProgressDivHealth = document.createElement("div");
    innerProgressDivHealth.classList.add("progress-bar");
    populateHealthDiv(data);
    innerProgressDivHealth.style.width = healthPercentage+"%";
    outerProgressDivHealth.setAttribute("id", "healthDivOuter");
    innerProgressDivHealth.setAttribute("id", "healthDiv");
    let healthTag = document.createElement("p");
    healthTag.classList.add('p-0', 'm-0');
    healthTag.innerHTML = data.runningHealth + "/" + data.health;
    innerProgressDivHealth.appendChild(healthTag);
    outerProgressDivHealth.appendChild(innerProgressDivHealth);

    let outerProgressDivMana = document.createElement("div");
    outerProgressDivMana.classList.add('m-1', 'p-0', 'col-sm', 'progress');
    let innerProgressDivMana = document.createElement("div");
    innerProgressDivMana.classList.add("progress-bar");
    populateManaDiv(data);
    innerProgressDivMana.style.width = manaPercentage+"%";
    outerProgressDivMana.setAttribute("id", "manaDivOuter");
    innerProgressDivMana.setAttribute("id", "manaDiv");
    let manaTag = document.createElement("p");
    manaTag.classList.add('p-0', 'm-0');
    manaTag.innerHTML = data.runningMana + "/" + data.mana;
    innerProgressDivMana.appendChild(manaTag);
    outerProgressDivMana.appendChild(innerProgressDivMana);

    let outerProgressDivEnergy = document.createElement("div");
    outerProgressDivEnergy.classList.add('m-1', 'p-0', 'col-sm', 'progress');
    let innerProgressDivEnergy = document.createElement("div");
    innerProgressDivEnergy.classList.add("progress-bar");
    populateEnergyDiv(data);
    innerProgressDivEnergy.style.width = energyPercentage+"%";
    outerProgressDivEnergy.setAttribute("id", "energyDivOuter");
    innerProgressDivEnergy.setAttribute("id", "energyDiv");
    let energyTag = document.createElement("p");
    energyTag.classList.add('p-0', 'm-0');
    energyTag.innerHTML = data.runningEnergy + "/" + data.energy;
    innerProgressDivEnergy.appendChild(energyTag);
    outerProgressDivEnergy.appendChild(innerProgressDivEnergy);

    tempHealthEtcDiv1.appendChild(outerProgressDivHealth);

    tempHealthEtcDiv1.appendChild(outerProgressDivMana);

    tempHealthEtcDiv1.appendChild(outerProgressDivEnergy);

    return tempHealthEtcDiv1;

}

function populatePlayerInfoDiv(data) {

    let tempHtag = document.createElement("h4");

    tempHtag.innerHTML = "Name: " + data.userName + " Level: " + data.level + " Trait: " + data.traitType;

    return tempHtag;

}

function waitingAnimation() {

}

function populateHealthDiv(data) {

    let baseHealth = data.health;

    let runningHealth = data.runningHealth;

    healthPercentage = (data.runningHealth/data.health) * 100;

}

function populateManaDiv(data) {

    let baseMana = data.mana;

    let runningMana = data.runningMana;

    manaPercentage = (data.runningMana/data.mana) * 100;

}

function populateEnergyDiv(data) {

    let baseEnergy = data.energy;

    let runningEnergy = data.runningEnergy;

    energyPercentage = (data.runningEnergy/data.energy) * 100;

}

backToHub.addEventListener("click", ()=>{

    location.href = "http://localhost:8080/hub"

})

//////////////////WEBSOCET STUFF//////////////////

function connect() {
    var socket = new SockJS('/gs');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        stompClient.subscribe('/topic/battleEvent/' + eventIdX, function (data) {

            handleResponseData(JSON.parse(data.body));

        });

    });
}


function attack(data) {

    console.log("ATTACKING OTHER PLAYER");

    stompClient.send("/app/battleEvent/" + eventIdX, {}, JSON.stringify(data));

}


function quitFight(data) {

    console.log("QUITING FIGHT");

    stompClient.send("/app/battleEvent/" + eventIdX, {}, JSON.stringify(data));

}

function handleResponseData(data) {

    if (data.ended) {

        fighterRow.style.display = "none";

        if (data.winner == playerUserName) {

            battleMessageTag.innerHTML = "CONGRATULATIONS YOU HAVE WON THE BATTLE";

        } else {

            battleMessageTag.innerHTML = "YOU HAVE LOST THE BATTLE";

        }

        battleMessageDiv.style.display = "block";

    } else {

        populateRoom(data.event);

    }
}
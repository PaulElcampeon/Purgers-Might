var playerUserName, tokenX, playerData, userName, userTrait, userLevel, userMoney, attributePoints, healthDiv,
    manaDiv, energyDiv, experienceDiv, healthTag, manaTag, energyTag, experienceTag, messageDiv, recipient,
    eventMessage, hasEventMessage, hideMessageDiv, hideMessageDivInterval, eventContent, messageHTag, operationEventDiv,
    activeOperationsDiv, activeOperationTitle, activeOperationResults, startTimeProgressBar, endTimeProgressBar,
    progressBarFunc, progressBarDiv, progressBarDivOuter, itemDisplay, tempOuterDivBag, tempOuterDivWeapon,
    tempOuterDivAbilities;

var stompClient = null;

hasEventMessage = true;//default set to true
userName = document.getElementById("userName");
userTrait = document.getElementById("userTrait");
userLevel = document.getElementById("userLevel");
userMoney = document.getElementById("userMoney");
attributePoints = document.getElementById("attributePoints");
healthDiv = document.getElementById("healthDiv");
manaDiv = document.getElementById("manaDiv");
energyDiv = document.getElementById("energyDiv");
experienceDiv = document.getElementById("experienceDiv");
healthTag = document.getElementById("healthTag");
manaTag = document.getElementById("manaTag");
energyTag = document.getElementById("energyTag");
experienceTag = document.getElementById("experienceTag");
messageDiv = document.getElementById("messageDiv");
messageHTag = document.getElementById("messageHTag");
operationEventDiv = document.getElementById("operationEventDiv");
activeOperationsDiv = document.getElementById("activeOperationsDiv");
activeOperationTitle = document.getElementById("activeOperationTitle");
activeOperationResults = document.getElementById("activeOperationResults");
progressBarDiv = document.getElementById("progressBarDiv");
progressBarDivOuter = document.getElementById("progressBarDivOuter");
itemDisplay = document.getElementById("itemDisplay");
itemDisplay.style.display = "none";
operationEventDiv.style.display = "none";


if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

    playerUserName = JSON.parse(sessionStorage.getItem("username"));

    tokenX = JSON.parse(sessionStorage.getItem("token"));

    let dataForGettingDetails = {name: playerUserName, token: tokenX};

    getUserData(dataForGettingDetails);

    connect();

}

function getUserData(data) {

    let url = "http://localhost:8080/user";

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

            console.log("DATA FROM GETTING USER DATA REQUEST");

            handleDataFromGettingUserRequest(data);

            savePlayerDataInSessionStorage(data.avatar);

        })

}


function handleDataFromGettingUserRequest(data) {

    populateUserInfo(data.avatar);

    checkForInEvent(data.avatar)

}

function checkForInEvent(data) {

    if (data.inEvent) {

        if (data.eventId.includes("battleEvent")) {//MEANS YOUR IN A BATTLE EVENT

            //TELEPORT THEM TO FIGHTING ROOM

            messageHTag.innerHTML = "YOU ARE CURRENTLY IN A BATTLE EVENT, PREPARE FOR TELEPORTATION";

            messageDiv.style.display = "block";

            teleportToFightingRoom();

            console.log("YOU SHOULD BE IN A BATTLE EVENT");


        }

        if (data.eventId.includes("bossEvent")) {//MEANS YOUR IN A BOSS EVENT

            //TELEPORT THEM TO BOSS EVENT ROOM

            messageHTag.innerHTML = "YOU ARE CURRENTLY IN A BOSS EVENT, PREPARE FOR TELEPORTATION";

            messageDiv.style.display = "block";

            teleportToBossEvent();

            console.log("YOU SHOULD BE IN A BOSS EVENT");

        }

        if (data.eventId.includes("operationEvent")) {

            console.log("YOU ARE IN AN OPERATION");

            populateActiveOperationsDiv(data.operationEvent);

        }

    } else {

        hasEventMessage = false;
    }
}

function populateUserInfo(data) {

    itemDisplay.innerHTML = "";

    playerData = data;

    userName.innerHTML = "Username: " + data.userName;

    userTrait.innerHTML = "Trait: " + data.traitType;

    userLevel.innerHTML = "Level: " + data.level;

    userMoney.innerHTML = "Money: £" + data.money;

    attributePoints.innerHTML = "Attribute Points: " + data.attributePoints;

    populateHealthDiv(data);

    populateManaDiv(data);

    populateEnergyDiv(data);

    populateExperience(data);

    displayBag();

    displayAbilities();

    displayArmour();

    displayWeapon();

}

function populateHealthDiv(data) {

    let baseHealth = data.health;

    let runningHealth = data.runningHealth;

    let healthPercentage = (data.runningHealth/data.health) * 100;

    healthDiv.style.width = healthPercentage + "%";

    healthTag.innerHTML = runningHealth + "/" + baseHealth;

}

function populateManaDiv(data) {

    let baseMana = data.mana;

    let runningMana = data.runningMana;

    let manaPercentage = (data.runningMana/data.mana) * 100;

    manaDiv.style.width = manaPercentage + "%";

    manaTag.innerHTML = runningMana + "/" + baseMana;

}

function populateEnergyDiv(data) {

    let baseEnergy = data.energy;

    let runningEnergy = data.runningEnergy;

    let energyPercentage = (data.runningEnergy/data.energy) * 100;

    energyDiv.style.width = energyPercentage + "%";

    energyTag.innerHTML = runningEnergy + "/" + baseEnergy;

}

function populateExperience(data) {

    let baseExperience = data.experience;

    let runningExperience = data.runningExperience;

    let experiencePercentage = (data.runningExperience/data.experience) * 100;

    experienceDiv.style.width = experiencePercentage + "%";

    experienceTag.innerHTML = runningExperience + "/" + baseExperience;

}

function displayBag() {

    let itemsInBag = playerData.bag.items;

    tempOuterDivBag = document.createElement("div");

    tempOuterDivBag.setAttribute("id", "tempOuterDivBag");

    tempOuterDivBag.classList.add('row', 'text-white', 'text-center', 'mb-5')

    for (let i = 0; i < itemsInBag.length; i++) {

        let tempDiv = document.createElement("div");
        let tempButton = document.createElement("button");
        let pTag = document.createElement("p");
        let tempImg = document.createElement("img");

        tempDiv.classList.add('p-0', 'col-sm-3');
        tempImg.style.width = "50px";
        tempImg.style.height = "50px";
        tempImg.src = itemsInBag[i].imgUrl;
        tempButton.innerHTML = "EQUIP";
        tempButton.addEventListener("click", ()=>{

            let dataX = {name: playerUserName, indexOfItem: i};

            equipItem(dataX);

        })

        if (itemsInBag[i].itemType == "WEAPON") {

            pTag.innerHTML = "Name: " + itemsInBag[i].name + "<br>Level: " + itemsInBag[i].level + "<br>Damage: " + itemsInBag[i].topDamage + " - " + itemsInBag[i].bottomDamage;

        } else if (itemsInBag[i].itemType == "ARMOUR") {

            pTag.innerHTML = "Name: " + itemsInBag[i].name + "<br>Level: " + itemsInBag[i].level + "<br>Defense: " + itemsInBag[i].defenseLevel + "<br>Body Part: " + itemsInBag[i].bodyPart;

        }

            tempDiv.appendChild(tempImg);
            tempDiv.appendChild(pTag);
            tempDiv.appendChild(tempButton);
            tempOuterDivBag.appendChild(tempDiv);
    }

    itemDisplay.appendChild(tempOuterDivBag);
}

function displayWeapon() {

    tempOuterDivWeapon = document.createElement("div");

    tempOuterDivWeapon.setAttribute("id", "tempOuterDivWeapon");

    tempOuterDivWeapon.classList.add('row', 'text-white', 'text-center');

    let tempDiv = document.createElement("div");
    let tempImg = document.createElement("img");
    let tempTag = document.createElement("p");

    tempDiv.classList.add('p-0', 'col-sm-4', 'offset-md-4', 'mb-5');
    tempImg.style.width = "50px";
    tempImg.style.height = "50px";
    tempImg.src = playerData.weapon.imgUrl;
    tempTag.innerHTML = "Name: " + playerData.weapon.name + "<br>Level: " + playerData.weapon.level + "<br>Damage: " + playerData.weapon.topDamage + " - " + playerData.weapon.bottomDamage;

    tempDiv.appendChild(tempImg);
    tempDiv.appendChild(tempTag);
    tempOuterDivWeapon.appendChild(tempDiv);

    itemDisplay.appendChild(tempOuterDivWeapon);

}

function displayArmour() {

//    let tempDiv = document.createElement("div");
//    let tempImg = document.createElement("img");
//    let tempTag = document.createElement("p");
//
//    tempDiv.classList.add('m-1', 'p-0', 'col-sm-4', 'offset-md-4');
//    tempImg.style.width = "50px";
//    tempImg.style.height = "50px";
//    tempImg.src = playerData.weapon.imgUrl;
//    tempTag.innerHTML = "Name: " + playerData.weapon.name + "<br>Level: " + playerData.weapon.level + "<br>Damage: " + playerData.weapon.topDamage + " - " + playerData.weapon.bottomDamage;
//
//    tempDiv.appendChild(tempImg);
//    tempDiv.appendChild(tempImg);
//
//    weaponItems.appendChild(tempDiv);
}

function displayAbilities() {

    tempOuterDivAbilities = document.createElement("div");

    tempOuterDivAbilities.setAttribute("id", "tempOuterDivAbilities");

    tempOuterDivAbilities.classList.add('row', 'text-white', 'text-center', 'mb-5');

    let abilitiesList = playerData.abilities;

    for (let i = 0; i < abilitiesList.length; i++) {

        let tempDiv = document.createElement("div");
        let pTag = document.createElement("p");
        let tempImg = document.createElement("img");

        tempDiv.classList.add('p-0', 'col-sm-3');
        tempImg.style.width = "50px";
        tempImg.style.height = "50px";
        tempImg.src = abilitiesList[i].imgUrl;

        pTag.innerHTML = "Name: " + abilitiesList[i].name + "<br>Ability Type: " + abilitiesList[i].abilityType + "<br>Increased Damage by : " + (abilitiesList[i].amount * 100).toFixed(2) + "%" + "<br>Cost : " + abilitiesList[i].cost;
        pTag.title = abilitiesList[i].description;

        tempDiv.appendChild(tempImg);
        tempDiv.appendChild(pTag);
        tempOuterDivAbilities.appendChild(tempDiv);
    }

    let tempButtonDiv = document.createElement("div");

    tempButtonDiv.classList.add('p-2', 'col-sm-4', 'offset-sm-4', 'text-center');
    let tempButtonChangeAbilities = document.createElement("button");
    let tempButtonUpgradeAbilities = document.createElement("button");
    tempButtonChangeAbilities.innerHTML = "Change Abilities";
    tempButtonUpgradeAbilities.innerHTML = "Upgrade Abilities";

    tempButtonChangeAbilities.classList.add('p-3', 'mb-3', 'buttonX');
    tempButtonChangeAbilities.addEventListener("click", ()=>{

        location.href = "http://localhost:8080/changeabilities"

    });

    tempButtonUpgradeAbilities.classList.add('p-3', 'buttonX');

    tempButtonUpgradeAbilities.addEventListener("click", ()=>{

         location.href = "http://localhost:8080/upgradeabilities"

    });

    tempButtonDiv.appendChild(tempButtonChangeAbilities);
    tempButtonDiv.appendChild(tempButtonUpgradeAbilities);

    tempOuterDivAbilities.appendChild(tempButtonDiv);

    itemDisplay.appendChild(tempOuterDivAbilities);
}

function equipItem(data) {

    let url = "http://localhost:8080/equipitem";

    fetch(url, {
        method: 'PUT',
        body: JSON.stringify(data),
        headers:{
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then((data) => {

            console.log("DATA FROM EQUIPPING ITEM REQUEST");

            if (data.success) {

                savePlayerDataInSessionStorage(data.avatar);

                populateUserInfo(data.avatar);

            } else {

                console.log("SOMETHING WENT WRONG");

            }
        })

}

function restoreAttribute(data) {

    let url = "http://localhost:8080/restoreHealth";

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

            console.log("DATA FROM RESTORING HEALTH REQUEST");

            handleRestoreAttributeResponse(data);
        })

}

function handleRestoreAttributeResponse(data) {

    if (data.success) {

        if (data.option == "health") {

            let tempHealthData = {health: playerData.health, runningHealth: playerData.health};

            playerData.runningHealth = playerData.health;

            populateHealthDiv(tempHealthData);

        } else {

            let tempManaData = {mana: playerData.mana, runningMana: playerData.mana};

            playerData.runningMana = playerData.mana;

            populateManaDiv(tempManaData);

        }

        playerData.money -= 10;

        userMoney.innerHTML = "Money: £" + playerData.money;

    }

    savePlayerDataInSessionStorage(playerData);
}


function populateActiveOperationsDiv(operationData) {

//        setEverythingToLocked();

        document.getElementById("previousOperationResults").innerHTML = "";

        console.log("POPULATING ACTIVE OPERATIONS DIV");

        activeOperationResults.innerHTML = "";

        activeOperationTitle.innerHTML = "ACTIVE OPERATION";

        let p = document.createElement("p");

        p.innerHTML = "Operation: " + operationData.name + "<br> Level: " + operationData.level + "<br> ExperienceSetter Points: " + operationData.experience + "<br> Payment: £" + operationData.money + "<br> End Time: " + (new Date(Number(operationData.endTime)).toLocaleTimeString());

        activeOperationsDiv.appendChild(p);
        startTimeProgressBar = operationData.startTime;
        endTimeProgressBar  = operationData.endTime;
        console.log(startTimeProgressBar);
        console.log(endTimeProgressBar)
        progressBarFunc = setInterval(progressBarAnimation, 2000);
        operationEventDiv.style.display = "block";

}

function progressBarAnimation() {

    let divWidth = Math.round((endTimeProgressBar - new Date().getTime())/(endTimeProgressBar - startTimeProgressBar) * 100);

    progressBarDiv.style.width = divWidth + "%";

    progressBarDiv.innerHTML = divWidth + "%";

    if (new Date().getTime() >= endTimeProgressBar) {

        clearInterval(progressBarFunc);

        let data = {name: playerUserName};

        operationEventDiv.style.display = "none";

        checkIfOperationIsComplete(data);

    }
}

function checkIfOperationIsComplete(data) {

    console.log("SENDING REQUEST TO CHECK IF ACTIVE OPERATION IS COMPLETE");

    let url = "http://localhost:8080/operationComplete";

    fetch(url, {
        method:'PUT',
        body: JSON.stringify(data),
        headers:{
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then(data => {
            console.log("DATA FROM CHECKING IF ACTIVE OPERATION IS COMPLETE REQUEST");
            console.log(data);
            checkIfOperationIsCompleteAction(data);
        })
}

function checkIfOperationIsCompleteAction(data) {

    if (data.success) {

        if (data.operationSuccess) {

            document.getElementById("previousOperationResults").innerHTML = "You have completed your last quest and were successful check your bag for a reward"
        }

        if (!data.operationSuccess) {

            document.getElementById("previousOperationResults").innerHTML = "You have completed your last quest and were unsuccessful"
        }

        savePlayerDataInSessionStorage(data.avatar);

        populateUserInfo(data.avatar);

        activeOperationsDiv.innerHTML = "";

//        operationEventDiv.style.display = "block";

//        setEverythingToUnlocked();

        hasEventMessage = false;

    }
}

function teleportToFightingRoom() {

   location.href = "http://localhost:8080/fightingroom";

}

document.getElementById("healPlayerBtn").addEventListener("click", ()=>{

    let messageDto = {sender: playerUserName, content: "health", option: ""};

    restoreAttribute(messageDto);

});

document.getElementById("restoreManaBtn").addEventListener("click", ()=>{

    let messageDto = {sender: playerUserName, content: "mana", option: ""};

    restoreAttribute(messageDto);

});

document.getElementById("pvpBtn").addEventListener("click", ()=>{

    location.href = "http://localhost:8080/playerpvplist"

});

document.getElementById("attributesBtn").addEventListener("click", ()=>{

    location.href = "http://localhost:8080/attributes"

});

document.getElementById("operationBtn").addEventListener("click", ()=>{

    location.href = "http://localhost:8080/operationlists"

});

document.getElementById("bagBtn").addEventListener("click", ()=>{

    if (itemDisplay.style.display == "none") {
        tempOuterDivAbilities.style.display = "none";
        tempOuterDivWeapon.style.display = "none";
        tempOuterDivBag.style.display = "flex";
        itemDisplay.style.display = "block";

    } else {

        itemDisplay.style.display = "none";

    }
})

document.getElementById("abilitiesBtn").addEventListener("click", ()=>{

    if (itemDisplay.style.display == "none") {
        tempOuterDivBag.style.display = "none";
        tempOuterDivWeapon.style.display = "none";
        tempOuterDivAbilities.style.display = "flex";
        itemDisplay.style.display = "block";

    } else {

        itemDisplay.style.display = "none";

    }
})

//document.getElementById("armourBtn").addEventListener("click", ()=>{
//
//    if (armourItems.style.display == "none") {
//        abilities.style.display = "none";
//        bagItems.style.display = "none";
//        weaponItems.style.display = "none";
//
//        armourItems.style.display = "flex";
//
//    } else {
//
//        armourItems.style.display = "none";
//
//    }
//})

document.getElementById("weaponBtn").addEventListener("click", ()=>{

    if (itemDisplay.style.display == "none") {
        tempOuterDivBag.style.display = "none";
        tempOuterDivAbilities.style.display = "none";
        tempOuterDivWeapon.style.display = "flex";
        itemDisplay.style.display = "block";

    } else {

        itemDisplay.style.display = "none";

    }
})


function savePlayerDataInSessionStorage(data) {

    sessionStorage.setItem("playerData", JSON.stringify(data));

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

    if (hasEventMessage) {

        console.log("WE HAVE JUST REFUSED AN EVENT")
        //DO NOITHING

    } else {

        eventMessage = data;

        hasEventMessage = true;

        recipient = eventMessage.sender;

        eventContent = eventMessage.content;

        if (data.content == "battleEvent") {

            hideMessageDivInterval = setTimeout(hideMessageDiv, 25000);//25 SECONDS TO ACT OR YOU LOSE YOUR CHANCE

            respondToBattleEvent();

        }

        if (data.content == "bossEvent") {

            hideMessageDivInterval = setTimeout(hideMessageDiv, 25000);//25 SECONDS TO ACT OR YOU LOSE YOUR CHANCE

            respondToBossEvent();

        }
    }
}

function eventInvite(data) {

    stompClient.send("/app/eventInvite/"+recipient, {}, JSON.stringify(data));

}

function respondToBossEvent() {

    messageHTag.innerHTML = "YOU HAVE AN INVITE TO A BOSS EVENT WOULD YOU LIKE TO ACCEPT";

    messageDiv.style.display = "block";
}

function respondToBattleEvent() {

    messageHTag.innerHTML = "YOU HAVE AN INVITE TO A BATTLE EVENT WOULD YOU LIKE TO ACCEPT";

    messageDiv.style.display = "block";

}

function hideMessageDiv() {

    console.log("JUST HIDE BOXES");

    hasEventMessage = false;

    messageDiv.style.display = "none";

}


document.getElementById("acceptInvite").addEventListener("click", ()=>{

    let dataX = {sender: playerUserName, content: eventContent, option: "accept"};

    eventInvite(dataX);

    playerData.inEvent = true;

    playerData.eventId = recipient + "battleEvent";

    savePlayerDataInSessionStorage(playerData);

    let fightersNames = {attacker: playerUserName, defender: eventMessage.sender};

    sessionStorage.setItem("fighters", JSON.stringify(fightersNames));

    teleportToFightingRoom();

});


document.getElementById("refuseInvite").addEventListener("click", ()=>{

    let dataX = {sender: playerUserName, content: eventContent, option: "refuse"};

    eventInvite(dataX);

    hasEventMessage = false;

    hideMessageDiv();

});



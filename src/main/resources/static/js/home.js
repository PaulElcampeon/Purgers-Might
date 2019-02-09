var playerUserName, avatar, userName, userLevel, kenjaPoints, healthDiv,
    mannaDiv, experienceDiv, healthTag, mannaTag, experienceTag, messageDiv, recipient,
    eventMessage, hasEventMessage, hideMessageDiv, hideMessageDivInterval, eventContent, messageHTag,
    itemDisplay, tempOuterDivBag, tempOuterDivWeapon, tempOuterDivAbilities, profilePic;

var stompClient = null;

hasEventMessage = true;//default set to true
userName = document.getElementById("userName");
userTrait = document.getElementById("userTrait");
userLevel = document.getElementById("userLevel");
userMoney = document.getElementById("userMoney");
kenjaPoints = document.getElementById("kenjaPoints");
healthDiv = document.getElementById("healthDiv");
mannaDiv = document.getElementById("mannaDiv");
experienceDiv = document.getElementById("experienceDiv");
healthTag = document.getElementById("healthTag");
mannaTag = document.getElementById("mannaTag");
experienceTag = document.getElementById("experienceTag");
messageDiv = document.getElementById("messageDiv");
messageHTag = document.getElementById("messageHTag");
itemDisplay = document.getElementById("itemDisplay");
itemDisplay.style.display = "none";
profilePic = document.getElementById("profilePic");


if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

    avatar = JSON.parse(sessionStorage.getItem("avatar"));

    playerUserName = avatar.username;

    handleDataFromGettingUserRequest(avatar)

//    connect();

}

//function getUserData(data) {
//
//    let url = "http://localhost:8080/user";
//
//    fetch(url, {
//        method: 'POST',
//        body: JSON.stringify(data),
//        headers:{
//            'Content-Type': 'application/json'
//        }
//    })
//        .then(res => res.json())
//        .catch(error => console.error('Error:', error))
//        .then((data) => {
//
//            console.log("DATA FROM GETTING USER DATA REQUEST");
//
//            handleDataFromGettingUserRequest(data);
//
//            saveavatarInSessionStorage(data.avatar);
//
//        })
//
//}


function handleDataFromGettingUserRequest(avatar) {
    populateUserInfo(avatar);
    checkForInEvent(avatar)
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

    profilePic.src = data.imageUrl;

    userName.innerHTML = "Username: " + data.username;

    userLevel.innerHTML = "Level: " + data.level;

    kenjaPoints.innerHTML = "Kenja Points: " + data.kenjaPoints;

    populateHealthDiv(data);

    populateMannaDiv(data);

    populateExperience(data);

    displayBag(data.bag.inventory);

    displayAbilities(data.spellBook.spellList);

    displayArmour(data.bodyArmour);

    displayWeapon(data.weapon);

}

function populateHealthDiv(data) {
    let baseHealth = data.health.actual;
    let runningHealth = data.health.running;
    let healthPercentage = (runningHealth/baseHealth) * 100;
    healthDiv.style.width = healthPercentage + "%";
    healthTag.innerHTML = runningHealth + "/" + baseHealth;
}

function populateMannaDiv(data) {
    let baseManna = data.manna.actual;
    let runningManna = data.manna.running;
    let mannaPercentage = (runningManna/baseManna) * 100;
    mannaDiv.style.width = mannaPercentage + "%";
    mannaTag.innerHTML = runningManna + "/" + baseManna;
}

function populateExperience(data) {
    let baseExperience = data.experience.actual;
    let runningExperience = data.experience.running;
    let experiencePercentage = (baseExperience/runningExperience) * 100;
    experienceDiv.style.width = experiencePercentage + "%";
    experienceTag.innerHTML = runningExperience + "/" + baseExperience;
}

function displayBag(data) {

    let itemsInBag = data;

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
        tempImg.src = itemsInBag[i].imageUrl;
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

function displayWeapon(data) {

    tempOuterDivWeapon = document.createElement("div");

    tempOuterDivWeapon.setAttribute("id", "tempOuterDivWeapon");

    tempOuterDivWeapon.classList.add('row', 'text-white', 'text-center');

    let tempDiv = document.createElement("div");
    let tempImg = document.createElement("img");
    let tempTag = document.createElement("p");

    tempDiv.classList.add('p-0', 'col-sm-4', 'offset-md-4', 'mb-5');
    tempImg.style.width = "50px";
    tempImg.style.height = "50px";
    tempImg.src = data.imageUrl;
    tempTag.innerHTML = "Name: " + data.name + "<br>Damage: " + data.topDamage + " - " + data.bottomDamage;

    tempDiv.appendChild(tempImg);
    tempDiv.appendChild(tempTag);
    tempOuterDivWeapon.appendChild(tempDiv);

    itemDisplay.appendChild(tempOuterDivWeapon);

}

function displayArmour(data) {

//    let tempDiv = document.createElement("div");
//    let tempImg = document.createElement("img");
//    let tempTag = document.createElement("p");
//
//    tempDiv.classList.add('m-1', 'p-0', 'col-sm-4', 'offset-md-4');
//    tempImg.style.width = "50px";
//    tempImg.style.height = "50px";
//    tempImg.src = avatar.weapon.imageUrl;
//    tempTag.innerHTML = "Name: " + avatar.weapon.name + "<br>Level: " + avatar.weapon.level + "<br>Damage: " + avatar.weapon.topDamage + " - " + avatar.weapon.bottomDamage;
//
//    tempDiv.appendChild(tempImg);
//    tempDiv.appendChild(tempImg);
//
//    weaponItems.appendChild(tempDiv);
}

function displayAbilities(data) {

    tempOuterDivAbilities = document.createElement("div");

    tempOuterDivAbilities.setAttribute("id", "tempOuterDivAbilities");

    tempOuterDivAbilities.classList.add('row', 'text-white', 'text-center', 'mb-5');

    let abilitiesList = data;

    for (let i = 0; i < abilitiesList.length; i++) {

        let tempDiv = document.createElement("div");
        let pTag = document.createElement("p");
        let tempImg = document.createElement("img");

        tempDiv.classList.add('p-0', 'col-sm-3');
        tempImg.style.width = "50px";
        tempImg.style.height = "50px";
        tempImg.src = abilitiesList[i].imageUrl;

        pTag.innerHTML = "Name: " + abilitiesList[i].name + "<br>Spell Type: " + abilitiesList[i].spellType + "<br>Damage : " + abilitiesList[i].damagePoints + "<br>Cost : " + abilitiesList[i].mannaCost;
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

                saveavatarInSessionStorage(data.avatar);

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

            let tempHealthData = {health: avatar.health, runningHealth: avatar.health};

            avatar.runningHealth = avatar.health;

            populateHealthDiv(tempHealthData);

        } else {

            let tempmannaData = {manna: avatar.manna, runningmanna: avatar.manna};

            avatar.runningmanna = avatar.manna;

            populateMannaDiv(tempmannaData);

        }

        avatar.money -= 10;

        userMoney.innerHTML = "Money: £" + avatar.money;

    }

    saveavatarInSessionStorage(avatar);
}



function teleportToFightingRoom() {

   location.href = "http://localhost:8080/fightingroom";

}

document.getElementById("healPlayerBtn").addEventListener("click", ()=>{

    let messageDto = {sender: playerUserName, content: "health", option: ""};

    restoreAttribute(messageDto);

});

document.getElementById("restoreMannaBtn").addEventListener("click", ()=>{

    let messageDto = {sender: playerUserName, content: "manna", option: ""};

    restoreAttribute(messageDto);

});

document.getElementById("pvpBtn").addEventListener("click", ()=>{

    location.href = "http://localhost:8080/playerpvplist"

});

document.getElementById("spellsBtn").addEventListener("click", ()=>{

    location.href = "http://localhost:8080/attributes"

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


function saveavatarInSessionStorage(data) {

    sessionStorage.setItem("avatar", JSON.stringify(data));

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

    avatar.inEvent = true;

    avatar.eventId = recipient + "battleEvent";

    saveavatarInSessionStorage(avatar);

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



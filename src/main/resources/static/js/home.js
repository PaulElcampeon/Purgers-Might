var playerUserName, avatar, userName, userLevel, kenjaPoints, healthDiv, messageHTag,
    mannaDiv, experienceDiv, healthTag, mannaTag, experienceTag, messageDiv, itemDisplay,
    tempOuterDivBag, tempOuterDivWeapon, tempOuterDivSpells, tempOuterDivArmour, profilePic,
    tempBattleReceiptOuterDiv, tempBattleStatisticsOuterDiv;

userName = document.getElementById("userName");
userLevel = document.getElementById("userLevel");
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
tempBattleReceiptOuterDiv = document.createElement("div");
tempBattleReceiptOuterDiv.setAttribute("id", "tempBattleReceiptOuterDiv");
tempBattleStatisticsOuterDiv = document.createElement("div");
tempBattleStatisticsOuterDiv.setAttribute("id", "tempBattleStatisticsOuterDiv");


if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

    avatar = JSON.parse(sessionStorage.getItem("avatar"));

    playerUserName = avatar.username;

    populateUserInfo(avatar);

    checkForInEvent(avatar);
}

function checkForInEvent(data) {

    console.log("Checking if you are in an event");

    if (data.inEvent) {

        if (data.eventId.includes("eventId")) {//MEANS YOUR IN A PVP EVENT

            //TELEPORT THEM TO FIGHTING ROOM

            messageHTag.innerHTML = "YOU ARE CURRENTLY IN A PVP EVENT, PREPARE FOR TELEPORTATION";

            messageDiv.style.display = "block";

            teleportToFightingRoom();
        }
    }
}

function populateUserInfo(data) {

    saveAvatarDataInSessionStorage(data);

    itemDisplay.innerHTML = "";

    profilePic.src = data.imageUrl;

    userName.innerHTML = "Username: " + data.username;

    userLevel.innerHTML = "Level: " + data.level;

    kenjaPoints.innerHTML = "Kenja Points: " + data.kenjaPoints;

    populateHealthDiv(data);

    populateMannaDiv(data);

    populateExperience(data);

    displayBag(data.bag.inventory);

    displaySpells(data.spellBook.spellList);

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

    let experiencePercentage = (runningExperience/baseExperience) * 100;

    experienceDiv.style.width = experiencePercentage + "%";

    experienceTag.innerHTML = runningExperience + "/" + baseExperience;
}

function displayBag(data) {

    let itemsInBag = data;

    tempOuterDivBag = document.createElement("div");

    tempOuterDivBag.setAttribute("id", "tempOuterDivBag");

    tempOuterDivBag.classList.add('row', 'text-white', 'text-center', 'my-3', 'p-3', 'border', 'border-white')

    for (let i = 0; i < itemsInBag.length; i++) {

        let tempDiv = document.createElement("div");
        let tempButtonEquip = document.createElement("button");
        let tempButtonDrop = document.createElement("button");
        let pTag = document.createElement("p");
        let tempImg = document.createElement("img");

        tempDiv.classList.add('p-0', 'col-sm-3');
        tempImg.style.width = "50px";
        tempImg.style.height = "50px";
        tempImg.src = itemsInBag[i].imageUrl;

        tempButtonEquip.innerHTML = "EQUIP";
        tempButtonEquip.addEventListener("click", ()=>{

            let dataX = {username: playerUserName, indexOfItem: i};

            equipItem(dataX);

        });

        tempButtonDrop.innerHTML = "DROP";
        tempButtonDrop.addEventListener("click", ()=>{

             let dataX = {username: playerUserName, indexOfItem: i};

             dropItem(dataX);

        });

        if (itemsInBag[i].itemType == "WEAPON") {

            pTag.innerHTML = "Name: " + itemsInBag[i].name + "<br>Level: " + itemsInBag[i].level + "<br>Damage: " + itemsInBag[i].topDamage + " - " + itemsInBag[i].bottomDamage;

        } else if (itemsInBag[i].itemType == "ARMOUR") {

            pTag.innerHTML = "Name: " + itemsInBag[i].name + "<br>Level: " + itemsInBag[i].level + "<br>Defense: " + itemsInBag[i].armourPoints + "<br>Armour Part: " + itemsInBag[i].armourType;

        }
            tempDiv.appendChild(tempImg);
            tempDiv.appendChild(pTag);
            tempDiv.appendChild(tempButtonEquip);
            tempDiv.appendChild(tempButtonDrop);
            tempOuterDivBag.appendChild(tempDiv);
    }

    itemDisplay.appendChild(tempOuterDivBag);
}

function displayWeapon(data) {

    tempOuterDivWeapon = document.createElement("div");

    tempOuterDivWeapon.setAttribute("id", "tempOuterDivWeapon");

    tempOuterDivWeapon.classList.add('row', 'text-white', 'text-center', 'my-3', 'p-3');

    let tempDiv = document.createElement("div");
    let tempImg = document.createElement("img");
    let tempTag = document.createElement("p");

    tempDiv.classList.add('p-0', 'col-sm-4', 'offset-md-4', 'border', 'border-white');
    tempImg.style.width = "50px";
    tempImg.style.height = "50px";
    tempImg.src = data.imageUrl;
    tempTag.innerHTML = "Name: " + data.name + "<br>Damage: " + data.topDamage + " - " + data.bottomDamage;

    tempDiv.appendChild(tempImg);
    tempDiv.appendChild(tempTag);
    tempOuterDivWeapon.appendChild(tempDiv);

    itemDisplay.appendChild(tempOuterDivWeapon);
}

function createArmourDisplay(data) {

    let tempDiv = document.createElement("div");
    let tempImg = document.createElement("img");
    let tempBreak = document.createElement("br");
    let tempPTag = document.createElement("p");

    tempDiv.classList.add('col-sm-4');

    tempImg.src = data.imageUrl;
    tempImg.style.width = "50px";
    tempImg.style.height = "50px";

    tempPTag.innerHTML = "Name: "+ data.name + "<br> Armour Points: " + data.armourPoints;

    tempDiv.appendChild(tempImg);
    tempDiv.appendChild(tempBreak);
    tempDiv.appendChild(tempPTag);

    return tempDiv;
}

function displayArmour(data) {

    tempOuterDivArmour = document.createElement("div");

    tempOuterDivArmour.setAttribute("id", "tempOuterDivArmour");

    tempOuterDivArmour.classList.add('row', 'text-white', 'text-center', 'my-3', 'p-3', 'border', 'border-white');

    tempOuterDivArmour.appendChild(createArmourDisplay(data.headArmour));
    tempOuterDivArmour.appendChild(createArmourDisplay(data.chestArmour));
    tempOuterDivArmour.appendChild(createArmourDisplay(data.legArmour));

    itemDisplay.appendChild(tempOuterDivArmour);
}

function displaySpells(data) {

    tempOuterDivSpells = document.createElement("div");

    tempOuterDivSpells.setAttribute("id", "tempOuterDivSpells");

    tempOuterDivSpells.classList.add('row', 'text-white', 'text-center', 'my-3', 'p-3', 'border', 'border-white');

    let spellList = data;

    for (let i = 0; i < spellList.length; i++) {

        let tempDiv = document.createElement("div");
        let pTag = document.createElement("p");
        let tempImg = document.createElement("img");

        tempDiv.classList.add('p-0', 'col-sm-3');
        tempImg.style.width = "50px";
        tempImg.style.height = "50px";
        tempImg.src = spellList[i].imageUrl;

        pTag.innerHTML = "Name: " + spellList[i].name + "<br>Spell Type: " + spellList[i].spellType + "<br>Damage : " + spellList[i].damagePoints + "<br>Cost : " + spellList[i].mannaCost;
        pTag.title = spellList[i].description;

        tempDiv.appendChild(tempImg);
        tempDiv.appendChild(pTag);
        tempOuterDivSpells.appendChild(tempDiv);
    }

    let tempButtonDiv = document.createElement("div");

    tempButtonDiv.classList.add('p-2', 'col-sm-4', 'offset-sm-4', 'text-center');
    let tempButtonChangeSpells = document.createElement("button");
    let tempButtonUpgradeSpells = document.createElement("button");
    tempButtonChangeSpells.innerHTML = "Change Spells";
    tempButtonUpgradeSpells.innerHTML = "Upgrade Spells";

    tempButtonChangeSpells.classList.add('p-3', 'mb-3', 'btnh', 'mx-1');
    tempButtonChangeSpells.addEventListener("click", ()=>{

        location.href = "../change-spells";

    });

    tempButtonUpgradeSpells.classList.add('p-3', 'btnh');

    tempButtonUpgradeSpells.addEventListener("click", ()=>{

         location.href = "../upgrade-spells"

    });

    tempButtonDiv.appendChild(tempButtonChangeSpells);
    tempButtonDiv.appendChild(tempButtonUpgradeSpells);

    tempOuterDivSpells.appendChild(tempButtonDiv);

    itemDisplay.appendChild(tempOuterDivSpells);
}

function displayBattleReceipts(data) {

    tempBattleReceiptOuterDiv.innerHTML = "";

    tempBattleReceiptOuterDiv.classList.add('row', 'text-white', 'text-center', 'my-3', 'p-1', 'border', 'border-white');

    let battleReceiptList = data;

    for (let i = 0; i < battleReceiptList.length; i++) {

        let tempDiv = document.createElement("div");
        let pTag = document.createElement("p");

        tempDiv.classList.add('p-0', 'col-sm-3', 'my-1', 'border-white', 'border');

        pTag.innerHTML = "Winner: " + battleReceiptList[i].winner +
         "<br>Loser: " + battleReceiptList[i].loser +
         "<br>Date: " + battleReceiptList[i].date +
          "<br>Time: " + battleReceiptList[i].time;

        tempDiv.appendChild(pTag);
        tempBattleReceiptOuterDiv.appendChild(tempDiv);
    }

        itemDisplay.appendChild(tempBattleReceiptOuterDiv);

        tempBattleReceiptOuterDiv.style.display = "flex";
}

function displayBattleStatistics(data) {

    tempBattleStatisticsOuterDiv.innerHTML = "";

    tempBattleStatisticsOuterDiv.classList.add('row', 'text-white', 'text-center', 'py-2', 'border', 'border-white', 'm-auto', 'w-50');

    let tempPTag = document.createElement("p");

    tempPTag.classList.add('h3', 'm-auto');

    tempPTag.innerHTML = "Victories: " + data.victories + "<br>Defeats: " + data.defeats;

    tempBattleStatisticsOuterDiv.appendChild(tempPTag);

    itemDisplay.appendChild(tempBattleStatisticsOuterDiv);

    tempBattleStatisticsOuterDiv.style.display = "flex";
}

function equipItem(data) {

   let url = "../equip-item";

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

           itemDisplay.style.display = "none";

           populateUserInfo(data.avatar);
       })
}

function dropItem(data) {

   let url = "../drop-item";

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

           itemDisplay.style.display = "none";
           avatar.bag = data;
           populateUserInfo(avatar);
       })
}

function restoreAttributeHealth(data) {

    let url = "../restore-attribute-service/health";

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

            console.log("DATA FROM RESTORING HEALTH REQUEST");

            populateUserInfo(data)
        })
}

function restoreAttributeManna(data) {

    let url = "../restore-attribute-service/manna";

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

            console.log("DATA FROM RESTORING HEALTH REQUEST");

            populateUserInfo(data)
        })
}

function getBattleReceipts() {

    let url = "../battle-receipt-service/" + playerUserName;

    fetch(url, {
        method: 'GET',
        headers:{
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then((data) => {

            console.log("DATA FROM BATTLE RECEIPT REQUEST");

            displayBattleReceipts(data)
        })
}

function getBattleStatistics() {

    let url = "../battle-statistics-service/battle-statistics/" + playerUserName;

    fetch(url, {
        method: 'GET',
        headers:{
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then((data) => {

            console.log("DATA FROM BATTLE STATISTICS REQUEST");

            displayBattleStatistics(data);
        })
}

function teleportToFightingRoom() {

   location.href = "../pvp-room";
}

function restoreAttributeReqDto(){

    return {username: playerUserName};
}

document.getElementById("leaderBoardBtn").addEventListener("click", ()=>{

    location.href = "../leaderboard";
});

document.getElementById("healPlayerBtn").addEventListener("click", ()=>{

    restoreAttributeHealth(restoreAttributeReqDto());
});

document.getElementById("restoreMannaBtn").addEventListener("click", ()=>{

    restoreAttributeManna(restoreAttributeReqDto());
});

document.getElementById("pvpBtn").addEventListener("click", ()=>{

    location.href = "../waiting-room"
});

document.getElementById("attributesBtn").addEventListener("click", ()=>{

    location.href = "../upgrade-attributes"
});

document.getElementById("chatRoomBtn").addEventListener("click", ()=>{

    location.href = "../chat-room"
});


document.getElementById("battleStatsBtn").addEventListener("click", ()=>{

    if (itemDisplay.style.display == "none") {
        getBattleStatistics();
        tempOuterDivSpells.style.display = "none";
        tempOuterDivWeapon.style.display = "none";
        tempOuterDivArmour.style.display = "none";
        tempOuterDivBag.style.display = "none";
        itemDisplay.style.display = "block";

    } else {

        itemDisplay.style.display = "none";
    }
});

document.getElementById("battleReceiptsBtn").addEventListener("click", ()=>{

    if (itemDisplay.style.display == "none") {
        getBattleReceipts();
        tempBattleStatisticsOuterDiv.style.display = "none";
        tempOuterDivSpells.style.display = "none";
        tempOuterDivWeapon.style.display = "none";
        tempOuterDivArmour.style.display = "none";
        tempOuterDivBag.style.display = "none";
        itemDisplay.style.display = "block";

    } else {

        itemDisplay.style.display = "none";
    }
});

document.getElementById("bagBtn").addEventListener("click", ()=>{

    if (itemDisplay.style.display == "none") {
        tempOuterDivSpells.style.display = "none";
        tempOuterDivWeapon.style.display = "none";
        tempOuterDivArmour.style.display = "none";
        tempBattleStatisticsOuterDiv.style.display = "none";
        tempBattleReceiptOuterDiv.style.display = "none";
        tempOuterDivBag.style.display = "flex";
        itemDisplay.style.display = "block";

    } else {

        itemDisplay.style.display = "none";
    }
});

document.getElementById("spellBtn").addEventListener("click", ()=>{

    if (itemDisplay.style.display == "none") {
        tempOuterDivBag.style.display = "none";
        tempOuterDivWeapon.style.display = "none";
        tempOuterDivArmour.style.display = "none";
        tempBattleStatisticsOuterDiv.style.display = "none";
        tempBattleReceiptOuterDiv.style.display = "none";
        tempOuterDivSpells.style.display = "flex";
        itemDisplay.style.display = "block";

    } else {

        itemDisplay.style.display = "none";
    }
});

document.getElementById("armourBtn").addEventListener("click", ()=>{

    if (itemDisplay.style.display == "none") {
        tempOuterDivBag.style.display = "none";
        tempOuterDivWeapon.style.display = "none";
        tempBattleStatisticsOuterDiv.style.display = "none";
        tempOuterDivSpells.style.display = "none";
        tempBattleReceiptOuterDiv.style.display = "none";
        tempOuterDivArmour.style.display = "flex";
        itemDisplay.style.display = "block";

    } else {

        itemDisplay.style.display = "none";
    }
});

document.getElementById("weaponBtn").addEventListener("click", ()=>{

    if (itemDisplay.style.display == "none") {
        tempOuterDivBag.style.display = "none";
        tempOuterDivSpells.style.display = "none";
        tempBattleStatisticsOuterDiv.style.display = "none";
        tempOuterDivArmour.style.display = "none";
        tempBattleReceiptOuterDiv.style.display = "none";
        tempOuterDivWeapon.style.display = "flex";
        itemDisplay.style.display = "block";

    } else {

        itemDisplay.style.display = "none";
    }
});

function saveAvatarDataInSessionStorage(data) {

    sessionStorage.setItem("avatar", JSON.stringify(data));
}



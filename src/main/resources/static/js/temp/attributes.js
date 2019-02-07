var playerUserName, tokenX, playerData, userName, userTrait, userLevel, userMoney, attributePoints, healthDiv,
    manaDiv, energyDiv, experienceDiv, healthTag, manaTag, energyTag, experienceTag, strengthTag, strengthDecreaseBtn,
    strengthIncreaseBtn, intellectTag, intellectDecreaseBtn, intellectIncreaseBtn, agilityTag, agilityDecreaseBtn,
    agilityIncreaseBtn, chanceToHitTag, chanceToDodgeTag, chanceToCritTag, damageReductionTag, spellPowerTag, physicalDamageTag;

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
strengthTag = document.getElementById("strengthTag");
strengthDecreaseBtn = document.getElementById("strengthDecreaseBtn");
strengthIncreaseBtn = document.getElementById("strengthIncreaseBtn");
intellectTag = document.getElementById("intellectTag");
intellectDecreaseBtn = document.getElementById("intellectDecreaseBtn");
intellectIncreaseBtn = document.getElementById("intellectIncreaseBtn");
agilityTag = document.getElementById("agilityTag");
agilityDecreaseBtn = document.getElementById("agilityDecreaseBtn");
agilityIncreaseBtn = document.getElementById("agilityIncreaseBtn");
chanceToHitTag = document.getElementById("chanceToHitTag");
chanceToDodgeTag = document.getElementById("chanceToDodgeTag");
chanceToCritTag = document.getElementById("chanceToCritTag");
damageReductionTag = document.getElementById("damageReductionTag");
spellPowerTag = document.getElementById("spellPowerTag");
physicalDamageTag = document.getElementById("physicalDamageTag");


if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready() {

    playerData = JSON.parse(sessionStorage.getItem("playerData"));

    playerUserName = playerData.userName;

    populateUserInfo(playerData);

    tokenX = JSON.parse(sessionStorage.getItem("token"))

    //WE NEED A WAITING ANIMATION
}

function attributeSetter(data) {

    let url = "http://localhost:8080/setAttribute";

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

            console.log("DATA FROM GETTING USER DATA REQUEST");

            handleResponseData(data);

            savePlayerDataInSessionStorage(data.avatar);

        })

}

function handleResponseData(data) {

    if (data.success) {

        populateUserInfo(data.avatar);

    } else {

        console.log("SOMETHING WENT WRONG")

    }
}

function populateUserInfo(data) {

    playerData = data;

    userName.innerHTML = "Username: " + data.userName;

    userTrait.innerHTML = "Trait: " + data.traitType;

    userLevel.innerHTML = "Level: " + data.level;

    userMoney.innerHTML = "Money: £" + data.money;

    attributePoints.innerHTML = "Attribute Points: " + data.attributePoints;

    strengthTag.innerHTML = "STRENGTH: " + data.strength;

    intellectTag.innerHTML = "INTELLECT: " + data.intellect;

    agilityTag.innerHTML = "AGILITY: " + data.agility;

    chanceToHitTag.innerHTML = "<strong>Hit</strong>: increases chance to hit by " + (data.chanceToHit * 100).toFixed(2) + "%";

    chanceToDodgeTag.innerHTML = "<strong>Dodge</strong>: increases chance to dodge by " + (data.chanceToDodge * 100).toFixed(2) + "%";

    chanceToCritTag.innerHTML = "<strong>Critical Hit</strong>: increases chance for a critical strike by " + (data.chanceToCrit * 100).toFixed(2) + "%";

    damageReductionTag.innerHTML = "<strong>Damage Reduction</strong>: decreases damage taken by  " + (data.damageReduction * 100).toFixed(2) + "%";

    spellPowerTag.innerHTML = "<strong>Spell Power</strong>: increases damage by spell abilities by " + (data.spellPower * 100).toFixed(2) + "%";

    physicalDamageTag.innerHTML = "<strong>Physical Damage</strong>: increases damage by weapon/physical abilities by " + (data.physicalDamage * 100).toFixed(2) + "%";

    populateHealthDiv(data);

    populateManaDiv(data);

    populateEnergyDiv(data);

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

strengthDecreaseBtn.addEventListener("click", ()=>{

    if (playerData.strength > 0) {

        let dataX = {name: playerUserName, attribute: "strength", increase: false, token: tokenX};

        attributeSetter(dataX);

    } else {

        console.log("CANT BE DONE")
    }

})

strengthIncreaseBtn.addEventListener("click", ()=>{

    if (playerData.attributePoints > 0) {

        let dataX = {name: playerUserName, attribute: "strength", increase: true, token: tokenX};

        attributeSetter(dataX);

    }  else {

        console.log("CANT BE DONE");

    }

})

intellectDecreaseBtn.addEventListener("click", ()=>{

    if (playerData.intellect > 0) {

        let dataX = {name: playerUserName, attribute: "intellect", increase: false, token: tokenX};

        attributeSetter(dataX);

    }  else {

        console.log("CANT BE DONE");

    }
})

intellectIncreaseBtn.addEventListener("click", ()=>{

    if (playerData.attributePoints > 0) {

        let dataX = {name: playerUserName, attribute: "intellect", increase: true, token: tokenX};

        attributeSetter(dataX);

    }  else {

        console.log("CANT BE DONE");

    }
})

agilityDecreaseBtn.addEventListener("click", ()=>{

    if (playerData.agility > 0) {

        let dataX = {name: playerUserName, attribute: "agility", increase: false, token: tokenX};

        attributeSetter(dataX);

    }  else {

        console.log("CANT BE DONE");

    }

})

agilityIncreaseBtn.addEventListener("click", ()=>{

    if (playerData.attributePoints > 0) {

        let dataX = {name: playerUserName, attribute: "agility", increase: true, token: tokenX};

        attributeSetter(dataX);

    }  else {

        console.log("CANT BE DONE");

    }
})




function savePlayerDataInSessionStorage(data) {

    sessionStorage.setItem("playerData", JSON.stringify(data));

}
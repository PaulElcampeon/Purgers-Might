var playerUserName, userName, userLevel, kenjaPoints, healthDiv, mannaDiv, experienceDiv,
    profilePic, increaseHealthBtn, decreaseHealthBtn, increaseMannaBtn, decreaseMannaBtn;

userName = document.getElementById("userName");
userLevel = document.getElementById("userLevel");
kenjaPoints = document.getElementById("kenjaPoints");
healthDiv = document.getElementById("healthDiv");
mannaDiv = document.getElementById("mannaDiv");
experienceDiv = document.getElementById("experienceDiv");
profilePic = document.getElementById("profilePic");
increaseHealthBtn = document.getElementById("increaseHealthBtn");
decreaseHealthBtn = document.getElementById("decreaseHealthBtn");
increaseMannaBtn = document.getElementById("increaseMannaBtn");
decreaseMannaBtn = document.getElementById("decreaseMannaBtn");


if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

    avatar = JSON.parse(sessionStorage.getItem("avatar"));

    playerUserName = avatar.username;

    populateUserInfo(avatar);
}

function populateUserInfo(data) {

    profilePic.src = data.imageUrl;

    userName.innerHTML = "Username: " + data.username;

    userLevel.innerHTML = "Level: " + data.level;

    kenjaPoints.innerHTML = "Kenja Points: " + data.kenjaPoints;

    populateHealthDiv(data);

    populateMannaDiv(data);

    populateExperience(data);
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

    saveAvatarDataInSessionStorage(data)

    let baseExperience = data.experience.actual;

    let runningExperience = data.experience.running;

    let experiencePercentage = (runningExperience/baseExperience) * 100;

    experienceDiv.style.width = experiencePercentage + "%";

    experienceTag.innerHTML = runningExperience + "/" + baseExperience;
}

function upgradeAttribute(data){

    let url = "../update-avatar-attribute";

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

            console.log("DATA FROM UPDATING AVATAR ATTRIBUTE REQUEST");

            populateUserInfo(data.avatar)
        })
}

function saveAvatarDataInSessionStorage(data) {

    sessionStorage.setItem("avatar", JSON.stringify(data));
}


increaseHealthBtn.addEventListener("click",()=>{

    let updateAvatarAttributeReqDto = {username: playerUserName, attributeType: HEALTH, cost: 1};

    upgradeAttribute(updateAvatarAttributeReqDto);
})

increaseMannaBtn.addEventListener("click",()=>{

    let updateAvatarAttributeReqDto = {username: playerUserName, attributeType: MANNA, cost: 1};

    upgradeAttribute(updateAvatarAttributeReqDto);
})
class ChangeSpellReqDto {
    constructor(username,indexOfSpell,nameOfNewSpell){
        this.username = username;
        this.indexOfSpell = indexOfSpell;
        this.nameOfNewSpell = nameOfNewSpell;
    }

    setUsername(username){
        this.username = username;
    }

    setIndexOfSpell(indexOfSpell) {
        this.indexOfSpell = indexOfSpell;
    }

    setNameOfNewSpell(nameOfNewSpell) {
        this.nameOfNewSpell = nameOfNewSpell;
    }

    getUsername() {
        return this.username;
    }

    getIndexOfSpell() {
        return this.indexOfSpell
    }

    getNameOfNewSpell() {
        return this.nameOfNewSpell;
    }
}

var learnedSpells, unlearnedSpells, changeSpellBtn, oldSpellSelected, newSpellSelected, playerUserName;

var changeSpellReqDto = new ChangeSpellReqDto();

learnedSpells = document.getElementById("learnedSpells");

unlearnedSpells = document.getElementById("unlearnedSpells");

changeSpellBtn = document.getElementById("changeSpellBtn");

changeSpellBtn.disabled = true;

oldSpellSelected = document.getElementById("oldSpellSelected");

newSpellSelected = document.getElementById("newSpellSelected");

if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

    avatar = JSON.parse(sessionStorage.getItem("avatar"));

    playerUserName = avatar.username;

    displayMySpells(avatar.spellBook.spellList);

    getSpellList();

    changeSpellReqDto.setIndexOfSpell("");

    changeSpellReqDto.setNameOfNewSpell("");

    changeSpellReqDto.setUsername(playerUserName);
}

function displayMySpells(data) {

    for (let i = 0; i < data.length; i++) {

        let equippedSpell = renderSpell(data[i], i, true);

        learnedSpells.appendChild(equippedSpell);
    }
}

function getSpellList() {

    let url = "../spell-service/all-spells";

     fetch(url, {
         method: 'GET',
         headers: {
             'Content-Type': 'application/json'
         }
     })
         .then(res => res.json())
         .catch(error => console.error('Error:', error))
         .then((data) => {

             console.log("DATA FROM GETTING ALL SPELLS REQUEST");

             console.log(data);

             displayAllSpells(data);
         });

}

function displayAllSpells(data) {

    for (let i = 0; i < data.length; i++) {

        let newSpell = renderSpell(data[i], i, false);

        unlearnedSpells.appendChild(newSpell);
    }
}

function renderSpell(data, indexOfSpell, equipped) {

    let tempDiv = document.createElement("div");

    tempDiv.classList.add("col-sm-3", "text-center", "mb-3", "pb-3");

    let tempImage = document.createElement("img");

    tempImage.src = data.imageUrl;

    tempImage.style.width = "60px";

    tempImage.style.height = "60px";

    let tempPTag = document.createElement("p")

    tempPTag.innerHTML = "<br>Name: " + data.name + "<br>Spell Type: " + data.spellType +
     "<br>Damage Points: " + data.damagePoints + "<br>Manna Cost: " + data.mannaCost +
     "<br>Spell Level: " + data.spellLevel + "<br>";

    let tempButton = document.createElement("button");

    tempButton.title = data.description;

    tempButton.innerHTML = "SELECT";

    if (equipped) {

        tempButton.addEventListener("click", ()=>{

            changeSpellReqDto.setIndexOfSpell(indexOfSpell);

            oldSpellSelected.innerHTML = "Old Spell Selected: " + data.name;

            attemptToChangeSpellButton();
        })

    } else {

        tempButton.addEventListener("click", ()=>{

            changeSpellReqDto.setNameOfNewSpell(data.name);

            newSpellSelected.innerHTML = "New Spell Selected: " + data.name;

            attemptToChangeSpellButton();
        })
    }

    tempDiv.appendChild(tempImage);

    tempDiv.appendChild(tempPTag);

    tempDiv.appendChild(tempButton);

    return tempDiv;
}

function sendRequestToChangeSpell(data){

    let url = "../change-spell";

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

            console.log("DATA FROM CHANGING SPELL REQUEST");

            sessionStorage.setItem("avatar", JSON.stringify(data.avatar));

            location.href = "../home"
        })
}


function attemptToChangeSpellButton() {

    if (verifyRequest()) {

        changeSpellBtn.disabled = false;

    } else {

        changeSpellBtn.disabled = true;
    }
}

function verifyRequest(){

    if (typeof changeSpellReqDto.getIndexOfSpell() == 'number' && changeSpellReqDto.getNameOfNewSpell().length > 1 && changeSpellReqDto.getUsername().length > 1) {

        return true;
    }

    return false;
}

changeSpellBtn.addEventListener("click",()=>{

    if (verifyRequest()) {

        sendRequestToChangeSpell(changeSpellReqDto);
    }
})

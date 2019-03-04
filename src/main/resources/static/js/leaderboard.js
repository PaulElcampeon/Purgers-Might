var leaderBoardDiv, skip, avatar, playerUserName, backToUserProfileBtn;

skip = 0;

leaderBoardDiv = document.getElementById("leaderBoardDiv");
backToUserProfileBtn = document.getElementById("backToUserProfileBtn");

if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

    avatar = JSON.parse(sessionStorage.getItem("avatar"));

    playerUserName = avatar.username;

    setInitialSkip();

    getLeaderBoard();
}

function setInitialSkip() {

    let tempSkip = JSON.parse(sessionStorage.getItem("skip"));

    if (tempSkip != null) {

        skip = tempSkip;
    }
}

function displayPlayerList(data) {

    leaderBoardDiv.innerHTML = "";

    for (let i = 0; i < data.length; i++) {

        let div = document.createElement("div");
        let p = document.createElement("p");
        let img = document.createElement("img");

        div.classList.add("mb-5");
        img.classList.add("mb-4");

        p.innerHTML = "Name: " + data[i].username + "<br>Level: " + data[i].level + "<br>Victories: " + data[i].victories + "<br>Defeats: " + data[i].defeats;
        img.src = data[i].imageUrl;

        div.appendChild(p);
        div.appendChild(img);
        leaderBoardDiv.appendChild(div);
    }
}

function increaseSkip() {

     skip+=20;

     sessionStorage.setItem(JSON.Stringify(skip));
}

function decreaseSkip() {

    skip-=20;

    sessionStorage.setItem(JSON.Stringify(skip));
}

///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////REQUESTS////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////


function getLeaderBoard() {

    console.log("GETTING player LIST");

    let url = '../battle-statistics-service/leaderboard/' + skip;

    fetch(url, {
        method:'GET'
    })
        .then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then((data) => {

            console.log("DATA FROM player LIST REQUEST");

            console.log(data);

            displayPlayerList(data);

        })
}


///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////BUTTON EVENT LISTENERS//////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////


backToUserProfileBtn.addEventListener("click",()=>{

    location.href = "../home"
})


document.getElementById("next20").addEventListener("click", () => {

    increaseSkip();

    getLeaderBoard();
});


document.getElementById("previous20").addEventListener("click", () => {

    if (skip == 20) {
        //do nothing
    } else {

        decreaseSkip();

        getLeaderBoard();
    }
});
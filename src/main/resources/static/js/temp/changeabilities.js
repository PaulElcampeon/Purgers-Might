var playerUserName, tokenX, playerData, userName, userTrait, userLevel, userMoney;


if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

    playerData = JSON.parse(sessionStorage.getItem("playerData"));

    playerUserName = JSON.parse(sessionStorage.getItem("username"));

    tokenX = JSON.parse(sessionStorage.getItem("token"));

}
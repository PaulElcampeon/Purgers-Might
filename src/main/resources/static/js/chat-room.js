var playerUserName, avatar, chatbox, stompClient, socket, sendMessageBtn, chatMessageDiv;

stompClient = null;
socket = null;
positionLeft = true;
chatbox = document.getElementById("chatbox");
sendMessageBtn = document.getElementById("sendMessageBtn");
chatMessageDiv = document.getElementById("chatMessageDiv");


if (document.readyState !== 'loading') {

    ready();

} else {

    document.addEventListener('DOMContentLoaded', ready);
}

function ready () {

    avatar = JSON.parse(sessionStorage.getItem("avatar"));

    playerUserName = avatar.username;

    let tempDiv = document.createElement("div");

    tempDiv.innerHTML = "Welcome To P-M ChatRooms";

    tempDiv.classList.add('text-center', 'text-dark');

    tempDiv.style.fontSize = "2vw";

    chatbox.appendChild(tempDiv);

    connect();
}

function connect() {

    socket = new SockJS('/chat-room');

    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {

        stompClient.subscribe('/topic/chat-room/general', function (data) {

            displayMessage(JSON.parse(data.body));
        });
    });
}


function displayMessage(data) {

    let div = document.createElement("div");

    div.innerHTML = "<strong>" + data.sender + ":</strong> " + data.message + "<br> " + data.date + " " + data.time;

    div.style.width = "60%";

    div.style.backgroundColor = "#D8BFDC";

    div.classList.add('rounded', 'p-2', 'm-2');

    if (data.sender == playerUserName) {

        div.classList.add('text-left', 'float-left');

    } else {

        div.classList.add('text-left', 'float-right');
    }

    chatbox.appendChild(div);
}

sendMessageBtn.addEventListener("click", () => {

    sendMessage();
});


function sendMessage() {

    let messageContent = chatMessageDiv.value;

    let message = {sender: playerUserName, content: messageContent};

    chatMessageDiv.value = "";

    stompClient.send("/app/chat-room/general", {}, JSON.stringify(message));
}


//function populateChatBox(data) {
//
//    chatBoxDiv.innerHTML += "<br>" + data.sender + ": " + data.content;
//
//    if (document.activeElement.id == chatBoxDiv) {
//
//        //do not scroll
//
//    } else {
//
//        goToBottom();
//    }
//}
//
//function goToBottom(){
//
//    chatBoxDiv.scrollTop = chatBoxDiv.scrollHeight - chatBoxDiv.clientHeight;
//}

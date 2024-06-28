let request = {
    page: 0,
    pageSize: 10
};

let selectedUserId;
let selectedUserImgSrc;
let stompClient = null;
$(document).ready(function () {
    moment.locale('uk');
    $("#chairman-avatar").attr("src", "uploads/"+authenticatedAvatar);
    connect();
    getUsers(0);
});

function connect(){
    const socket = new SockJS("my-ws")
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError)
}

function onConnected() {
    stompClient.subscribe(`/user/${authenticatedId}/CHAIRMAN/queue/messages`, onMessageReceived)
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    if(selectedUserId && selectedUserId === message.senderId) {
        displayUserMessage(message.content, message.creationDate, message.creationTime)
    }
    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if(notifiedUser && !notifiedUser.classList.contains("active")) {
        $(notifiedUser).find("div.avatar").addClass("avatar-busy");
    }
}

function onError() {

}

function getUsers(currentPage) {
    blockCardDody();
    request.page = currentPage;
    $.ajax({
        type: "GET",
        url: "chat/get-users",
        data: request,
        success: function (response) {
            console.log(response);
            drawUsers(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function drawUsers(response) {
    for (let user of response.content) {
        $("#chat-list")
            .append(
                `<li class="chat-contact-list-item" id="${user.id}">
                    <a class="d-flex align-items-center">
                        <div class="flex-shrink-0 avatar">
                            <img src="${'uploads/'+user.avatar}" alt="Avatar" class="rounded-circle" />
                        </div>
                        <div class="chat-contact-info flex-grow-1 ms-2">
                            <h6 class="chat-contact-name text-truncate m-0">
                                ${user.fullName}
                            </h6>
                            <p class="chat-contact-status text-muted text-truncate mb-0">
                                ${'Будинок '+user.houseNumber+', Квартира '+user.apartmentNumber}
                            </p>
                        </div>
                    </a>
                </li>`
            );
    }
    listenClick()
}
function listenClick() {
    $(".chat-contact-list-item").on("click", function () {
        document.querySelectorAll(".chat-contact-list-item").forEach(item => {
            item.classList.remove("active");
        });

        selectedUserImgSrc = $(this).find("img.rounded-circle").attr("src");
        let name = $(this).find("h6.chat-contact-name").text();
        let house = $(this).find("p.chat-contact-status").text();
        selectedUserId = $(this).attr("id");
        $(".chat-history-wrapper").empty();
        showChatHeader(name, house);
        showChatBody();
        showChatForm();
        $(this).find("div.avatar").removeClass("avatar-busy")
        $(this).addClass("active");
    });
}

function showChatHeader(name, house) {
    $(".chat-history-wrapper").append(
        `<div class="chat-history-header border-bottom">
            <div class="d-flex justify-content-between align-items-center">
                <div class="d-flex overflow-hidden align-items-center">
                    <i
                        class="ti ti-menu-2 ti-sm cursor-pointer d-lg-none d-block me-2"
                        data-bs-toggle="sidebar"
                        data-overlay
                        data-target="#app-chat-contacts">                
                    </i>
                    <div class="flex-shrink-0 avatar">
                        <img
                            src="${selectedUserImgSrc}"
                            alt="Avatar"
                            class="rounded-circle"
                            data-bs-toggle="sidebar"
                            data-overlay
                            data-target="#app-chat-sidebar-right"
                        />
                    </div>
                    <div class="chat-contact-info flex-grow-1 ms-2">
                        <h6 class="m-0">${name}</h6>
                        <small class="user-status text-muted">${house}</small>
                    </div>
                </div>
            </div>
        </div>`
    );
}

function showChatBody() {
    $(".chat-history-wrapper").append(
        ` <div class="chat-history-body bg-body">
                <ul class="list-unstyled chat-history">
                </ul>
         </div>`
    );
    getChatMessages();
}

function getChatMessages() {
    $.ajax({
        type: "GET",
        url: "chat/messages/"+selectedUserId+"/"+authenticatedId,
        data: request,
        success: function (response) {
            drawChatMessages(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function drawChatMessages(response) {
    for (let message of response.content) {
        if(message.sender.localeCompare("CHAIRMAN") === 0){
            displayChairmanMessage(message.content, message.creationDate,
                message.creationTime);
        } else {
            displayUserMessage(message.content, message.creationDate,
                message.creationTime);
        }
    }
}

function displayChairmanMessage(content, creationDate, creationTime){
    $("ul.chat-history").append(
        `<li class="chat-message chat-message-right">
                    <div class="d-flex overflow-hidden">
                        <div class="chat-message-wrapper flex-grow-1">
                            <div class="chat-message-text">
                                <p class="mb-0">${content}</p>
                            </div>
                            <div class="text-end text-muted mt-1">
                                <small>${formatDate(creationDate, creationTime)}</small>
                            </div>
                        </div>
                        <div class="user-avatar flex-shrink-0 ms-3">
                            <div class="avatar avatar-sm">
                                <img src="${'uploads/'+authenticatedAvatar}" alt="Avatar" class="rounded-circle" />
                            </div>
                        </div>
                    </div>
                </li>`
    );
}

function displayUserMessage(content, creationDate, creationTime) {
    $("ul.chat-history").append(
        `<li class="chat-message">
                    <div class="d-flex overflow-hidden">
                        <div class="chat-message-wrapper flex-grow-1">
                            <div class="chat-message-text">
                                <p class="mb-0">${content}</p>
                            </div>
                            <div class="text-end text-muted mt-1">
                                <small>${formatDate(creationDate, creationTime)}</small>
                            </div>
                        </div>
                        <div class="user-avatar flex-shrink-0 ms-3">
                            <div class="avatar avatar-sm">
                                <img src="${selectedUserImgSrc}" alt="Avatar" class="rounded-circle" />
                            </div>
                        </div>
                    </div>
                </li>`
    );
}

function formatDate(date, time){
    const dateParts = date.split('-');
    let formattedDate = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]).toLocaleString("uk", {
        day: 'numeric',
        month: 'long',
        year: 'numeric'
    });
    const timePart = time.split(":");
    return formattedDate +" "+timePart[0]+":"+timePart[1];
}

function showChatForm() {
    $(".chat-history-wrapper").append(
        `<div class="chat-history-footer shadow-sm">
            <form class="form-send-message d-flex justify-content-between align-items-center">
                <input
                    class="form-control message-input border-0 me-3 shadow-none"
                    placeholder="Type your message here"
                />
                <div class="message-actions d-flex align-items-center">
<!--                    <label for="attach-doc" class="form-label mb-0">-->
<!--                        <i class="ti ti-photo ti-sm cursor-pointer mx-3"></i>-->
<!--                        <input type="file" id="attach-doc" hidden />-->
<!--                    </label>-->
                    <button type="button" class="btn btn-primary d-flex" id="send-button">
                        <i class="ti ti-send me-md-1 me-0"></i>
                        <span class="align-middle d-md-inline-block d-none">Send</span>
                    </button>
                </div>
            </form>
        </div>`
    );
    listenSendButton();
}
function listenSendButton() {
    $("#send-button").on("click", function () {
        sendMessage()
    });
}
function sendMessage() {
    let messageInput = document.querySelector('.message-input');
    const messageText = messageInput.value.trim();
    let userId = document.querySelector('li.chat-contact-list-item.active').id;
    if(messageText && stompClient) {
        const chatMessage = {
            text: messageText,
            userId: userId,
            chairmanId: authenticatedId,
            sender: "CHAIRMAN"
        }
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        displayChairmanMessage(messageText, moment().format('YYYY-MM-DD'), moment().format('LT'));
    }
}
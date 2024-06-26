let request = {
    page: 0,
    pageSize: 10
};

$(document).ready(function () {
    $("#chairman-avatar").attr("src", "uploads/"+authenticatedAvatar);
    getUsers(0);
});

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
        error: function (error) {
            printErrorMessageToField(error);
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
}

$(document).ready(function () {
    getContactsPage();
});

function getContactsPage() {
    blockCardDody();
    $.ajax({
        type: "GET",
        url: "contacts/get",
        success: function (response) {
            console.log(response);
            setFields(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function setFields(response) {
    $("#firstTitle").text(response.firstTitle);
    $("#firstText").append(response.firstText);
    $("#secondTitle").text(response.secondTitle);
    $("#secondText").append(response.secondText);
}

let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
$(document).ready(function () {
    getMessage();
});

function getMessage() {
    blockCardDody();
    $.ajax({
        type: "GET",
        url: "get/"+id,
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
    const responseMap = new Map(Object.entries((response)));
    responseMap.forEach((value, key) => {
        $("#" + key).text(value);
    });
    $("#date").text(formatDate(response.creationDate));
}

function formatDate(date) {
    const splitDate = date.split("T");
    const datePart = moment(splitDate[0], 'YYYY-MM-DD').format('DD.MM.YYYY');
    const timePart = splitDate[1].split(":");
    return datePart + " "+timePart[0]+":"+timePart[1];
}

$("#delete-button").on("click", function () {
    blockCardDody();
    $.ajax({
        type: "POST",
        url: "delete/"+id,
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function (response) {
            window.location = "../messages";
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
});

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
        error: function (error) {
            printErrorMessageToField(error);
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
    return datePart + " "+splitDate[1];
}



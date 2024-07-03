let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
$(document).ready(function () {
    getRegistrationRequest();
});
function getRegistrationRequest() {
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
    let address = "м. "+response.houseResponse.city+" вул. "+response.houseResponse.street+" "+response.houseResponse.number
    $("#address").text(address);
}

$("#accept-button").on("click", function () {
    blockCardDody();
    $.ajax({
        type: "POST",
        url: "accept/"+id,
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            toastr.success("Заявка була прийнята");
            window.location = "../requests";
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
});

$("#reject-button").on("click", function () {
    blockCardDody();
    $.ajax({
        type: "POST",
        url: "reject/"+id,
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            toastr.success("Заявка була відхилена");
            window.location = "../requests";
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
});

$(document).ready(function () {
    getProfile();
});

function getProfile() {
    blockCardDody();
    $.ajax({
        type: "GET",
        url: "profile/get",
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
    $("#fullName").text(response.lastName+" "+response.firstName+" "+response.middleName);
    $("#avatar-img").attr("src","uploads/"+response.avatar);
}

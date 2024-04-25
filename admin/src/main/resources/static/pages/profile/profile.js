
$(document).ready(function () {
    getProfile();
});

function getProfile() {
    blockCardDody();
    $.ajax({
        type: "GET",
        url: "profile/get",
        success: function (response) {
            console.log(response)
            setFields(response);
        },
        error: function () {
            toastr.error("Виникла помилка");
        }
    });
}

function setFields(response) {
    const responseMap = new Map(Object.entries((response)));
    responseMap.forEach((value, key) => {
        if(key.localeCompare("avatar") !== 0)
            $("#" + key).val(value);
    });
    $("#faAuthentication").prop("checked", response.faAuthentication);
}

$("#save-button").on("click", function () {
    blockCardDody();
    clearAllErrorMessage();
    let formData = collectData();
    for (var pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
    }
    sendData(formData);
});

function collectData() {
    let formData = new FormData();
    $("#form").find('input:text').each(function (){
        formData.append($(this).attr("id"), $(this).val());
    });
    let image = $("#avatar").prop('files')[0];
    if(image === undefined) {
        formData.append("avatar", new File([""], "filename"));
    } else {
        formData.append("avatar", image);
    }
    formData.append("faAuthentication",$("#faAuthentication").is(':checked'));
    return formData;
}

function sendData(formData) {
    $.ajax({
        type: "POST",
        url: window.location.href,
        data: formData,
        contentType: false,
        processData: false,
        success: function () {
            toastr.success("Оновлення успішне!");
            setTimeout(() => location.reload(), 6000)
        },
        error: function (error) {
            printErrorMessageToField(error);
        }
    });
}
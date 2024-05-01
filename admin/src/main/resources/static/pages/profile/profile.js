let secretKey;
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
            toastr.error(errorMessage);
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
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            toastr.success(updateSuccessMessage);
        },
        error: function (error) {
            toastr.error(errorMessage);
            printErrorMessageToField(error);
        }
    });
}

$("#faAuthentication").on("change", function () {
    $(this).prop("checked", !$(this).is(':checked'));
    if($(this).is(':checked')){
        showCodeInput();
    } else {
        getQRCode();
    }
});

function showCodeInput() {
    $("#authentication").append(
        `<label for="code" class="form-label mt-3">Код</label>
        <div id="code-div">
         <input type="text" class="form-control" id="code" name="code" placeholder="Введіть код"
                       maxlength="32">
         </div>
         <button type="button" class="btn btn-primary mt-3" onclick="verifyCode()">Надіслати</button>
         `);
}

function verifyCode() {
    $.ajax({
        type: "POST",
        url: "profile/verify-code",
        data: {
            code: $("#code").val()
        },
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            toastr.success("Двофакторна автентифікація вимкнена");
            $("#authentication").empty();
            $("#faAuthentication").prop("checked", false);

        },
        error: function (error) {
            toastr.error(errorMessage);
            if (error.status === 400){
                $("#code-div").append(
                    `<p class="text-danger" id="code-error">Невірний код</p>`
                );
            }
        }
    });
}

function getQRCode() {
    blockCardDody();
    $.ajax({
        type: "GET",
        url: "profile/getQR",
        success: function (response) {
            console.log(response)
            setQrCode(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function setQrCode(response) {
    secretKey = response.qrCodeKey;
    $("#authentication").append(
        `<p class="mt-3">Проскануйте QR код за допомогою Google Authenticator та збережіть код</p>
         <img src="${response.qrCode}" alt="qr code"/>
         <p class="mt-3">Якщо не маєте можливості сканувати QR код скопіюйте цей код: 
         <br><span id="secret-key">${response.qrCodeKey}</span></br></p>
         <button type="button" class="btn btn-primary" onclick="saveSecretKey()">OK</button>
        `
    );
}

function saveSecretKey() {
    blockCardDody();
    $.ajax({
        type: "POST",
        url: "profile/save-secret-key",
        data: {
            secretKey: $("#secret-key").text()
        },
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            toastr.success("Двофакторна автентифікація увімкнена");
            $("#authentication").empty();
            $("#faAuthentication").prop("checked", true);
        },
        error: function (error) {
            toastr.error(errorMessage);
        }
    });
}





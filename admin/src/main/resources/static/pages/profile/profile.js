let secretKey;
let authenticationOn = false;
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
        if (key.localeCompare("avatar") !== 0)
            $("#" + key).val(value);
    });
    $("#faAuthentication").prop("checked", response.faAuthentication);
    $("#avatar-img").attr("src", "../uploads/" + response.avatar);
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
    $("#form").find('input:text').each(function () {
        formData.append($(this).attr("id"), $(this).val());
    });
    let image = $("#avatar").prop('files')[0];
    if (image === undefined) {
        formData.append("avatar", new File([""], "filename"));
    } else {
        formData.append("avatar", image);
    }
    formData.append("faAuthentication", $("#faAuthentication").is(':checked'));
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
            setTimeout(() => location.reload(), 6000)
        },
        error: function (error) {
            toastr.error(errorMessage);
            printErrorMessageToField(error);
        }
    });
}

$("#faAuthentication").on("change", function () {
    $(this).prop("checked", !$(this).is(':checked'));
    if ($(this).is(':checked')) {
        openConfirmCodeModal();
    } else {
        getQRCode();
    }
});

function getQRCode() {
    blockCardDody();
    $.ajax({
        type: "GET",
        url: "profile/getQR",
        success: function (response) {
            console.log(response)
            openQrCodeModal(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function openQrCodeModal(response) {
    secretKey = response.qrCodeKey;
    if ($("#qrModal").length === 0) {
        $(".card-body").append(
            `<div class="modal fade" id="qrModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Проскануйте QR код за допомогою Google Authenticator або іншого додатку</p>
                        <img src="${response.qrCode}" alt="qr code" class="ms-5"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-label-secondary close-modal" data-bs-dismiss="modal">
                        Закрити
                        </button>
                        <button type="button" class="btn btn-primary" onclick="confirmCode()">
                            OK
                        </button>
                    </div>
                </div>
            </div>
        </div>`
        );
    }
    $('#qrModal').modal('show');
    authenticationOn = true;
}
function confirmCode() {
    $('#qrModal').modal('hide');
    openConfirmCodeModal();
}

function openConfirmCodeModal(){
    if ($("#confirmCodeModal").length === 0) {
        $(".card-body").append(
            `<div class="modal fade" id="confirmCodeModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                    </div>
                    <div class="modal-body">
                       <label for="code" class="form-label mt-3">Код</label>
                        <div id="code-div">
                            <input type="text" class="form-control" id="code" name="code" placeholder="Введіть код"
                            maxlength="32">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onclick="disableOrEnableFaAuthentication()">
                            OK
                        </button>
                    </div>
                </div>
            </div>
        </div>`
        );
    }
    $('#confirmCodeModal').modal('show');
}

function disableOrEnableFaAuthentication() {
    if(authenticationOn){
        enableFaAuthentication();
    } else {
        disableFaAuthentication();
    }
}
function enableFaAuthentication() {
    $.ajax({
        type: "POST",
        url: "profile/enable-faAuthentication",
        data: {
            code: $("#code").val(),
            secretKey: secretKey
        },
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            toastr.success("Двофакторна автентифікація увімкнена");
            $("#faAuthentication").prop("checked", true);
            authenticationOn = false;
            $('#confirmCodeModal').modal('hide');
            $("#code").val("");
        },
        error: function (error) {
            $("#code-error").remove();
            toastr.error(errorMessage);
            if (error.status === 400) {
               showCodeValidation();
            }
        }
    });
}

function disableFaAuthentication() {
    $.ajax({
        type: "POST",
        url: "profile/disable-faAuthentication",
        data: {
            code: $("#code").val()
        },
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            toastr.success("Двофакторна автентифікація вимкнена");
            $("#faAuthentication").prop("checked", false);
            $('#confirmCodeModal').modal('hide');
            $("#code").val("");
        },
        error: function (error) {
            $("#code-error").remove();
            toastr.error(errorMessage);
            if (error.status === 400) {
                showCodeValidation();
            }
        }
    });
}

function showCodeValidation() {
    $("#code-div").append(
        `<p class="text-danger" id="code-error">Невірний код</p>`
    );
}

$("#avatar").on("change", function () {
    var myFile = $(this).prop('files');
    if (validateFile(myFile[0].name) != false) {
        $("#avatar-img").attr("src", window.URL.createObjectURL(myFile[0]));
    } else {
        $("#avatarValidation").text(fileValidation);
        $(this).val('');
    }
});

function validateFile(value) {
    var ext = value.substring(value.lastIndexOf('.') + 1).toLowerCase();
    if ($.inArray(ext, ['png', 'jpg', 'jpeg']) == -1 && value != "") {
        return false;
    } else {
        return true;
    }
}



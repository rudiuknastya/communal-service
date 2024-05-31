
$(document).ready(function () {
    getNumber();
    initializeUsersSelect();
});

function getNumber() {
    $.ajax({
        type: "GET",
        url: "get-number",
        success: function (response) {
            $("#number").val(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function initializeUsersSelect() {
    $("#userId").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#userId").parent(),
        language: "uk",
        maximumInputLength: 100,
        placeholder: "Оберіть користувача",
        ajax: {
            type: "GET",
            url: "get-users",
            data: function (params) {
                return {
                    search: params.term,
                    page: params.page || 1
                };
            },
            processResults: function (response) {
                return {
                    results: $.map(response.content, function (item) {
                        return {
                            text: item.fullName,
                            id: item.id
                        }
                    }),
                    pagination: {
                        more: (response.pageable.pageNumber + 1) < response.totalPages
                    }
                };
            }
        }
    });
}

$("#userId").on("change", function () {
    $.ajax({
        type: "GET",
        url: "get-personal-account/"+$(this).val(),
        success: function (response) {
            $("#personal-account").text(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
});

$("#file").on("change", function () {
    $("#fileValidation").text("");
    let myFile = $(this).prop('files');
    if(!validateFile(myFile[0].name)){
        $("#fileValidation").text("Файл не відповідає формату .pdf");
        $(this).val('');
    }
});
function validateFile(value){
    let ext = value.substring(value.lastIndexOf('.') + 1).toLowerCase();
    if(value !== "" && $.inArray(ext, ['pdf']) === -1) {
        return false;
    } else {
        return true;
    }
}
$("#save-button").on("click", function () {
    blockCardDody();
    clearAllErrorMessage();
    let formData = collectData();
    sendData(formData);
});

function collectData() {
    let formData = new FormData();
    let userId = $("#userId").val() == null? '': $("#userId").val();
    formData.append("userId", userId);
    let avatar = $('#file').prop('files')[0];
    if(avatar === undefined) {
        formData.append("file",  new File([""], "filename"));
    } else {
        formData.append("file", avatar);
    }
    for (let pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
    }
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
            window.location = "../invoices";
        },
        error: function (error) {
            toastr.error(errorMessage);
            printErrorMessageToField(error);
        }
    });
}
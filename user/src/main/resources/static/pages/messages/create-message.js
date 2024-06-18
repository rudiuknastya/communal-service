
$(document).ready(function () {
    autosize($("#text"));
    initializeChairmanSelect()
});

function initializeChairmanSelect() {
    $("#chairmanId").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#chairmanId").parent(),
        language: "uk",
        maximumInputLength: 100,
        placeholder: "Оберіть голову будинку",
        ajax: {
            type: "GET",
            url: "get-chairmen",
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

$("#save-button").on("click", function () {
    blockCardDody();
    clearAllErrorMessage();
    let formData = collectData();
    sendData(formData);
});

function collectData() {
    let formData = new FormData();
    $("#form").find('input:text, textarea').each(function (){
        formData.append($(this).attr("id"), $(this).val());
    });
    let chairmanId = $("#chairmanId").val() == null? '': $("#chairmanId").val();
    formData.append("chairmanId", chairmanId);
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
            window.location = "../messages";
        },
        error: function (error) {
            toastr.error(errorMessage);
            printErrorMessageToField(error);
        }
    });
}
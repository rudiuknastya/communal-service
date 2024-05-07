let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
$(document).ready(function () {
    initializeSelects();
    getHouse();
});

function initializeSelects() {
    initializeStatusSelect();
    initializeChairmanSelect();
}

function initializeStatusSelect() {
    $("#status").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#status").parent(),
        language: "uk",
        minimumResultsForSearch: -1,
        placeholder: "Оберіть статус",
        ajax: {
            type: "GET",
            url: "../get-statuses",
            processResults: function (response) {
                return {
                    results: $.map(response, function (item) {
                        return {
                            text: getStatus(item),
                            id: item
                        }
                    })
                };
            }

        }
    });
}

function getStatus(status) {
    switch (status) {
        case 'NEW':
            return "Новий";
        case 'ACTIVE':
            return "Активний";
        case 'DISABLED':
            return "Вимкнений";
    }
}

function initializeChairmanSelect() {
    $("#chairmanId").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#chairmanId").parent(),
        maximumInputLength: 100,
        placeholder: "Оберіть голову",
        ajax: {
            type: "GET",
            url: "../get-chairmen",
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

function getHouse() {
    blockCardDody();
    $.ajax({
        type: "GET",
        url: "get/"+id,
        success: function (response) {
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
        $("#" + key).val(value);
    });
    let statusOption = new Option(getStatus(response.status), response.status, true, true);
    $('#status').append(statusOption).trigger('change');
    let chairmanOption = new Option(response.chairmanNameResponse.fullName, response.chairmanNameResponse.id, true, true);
    $('#chairmanId').append(chairmanOption).trigger('change');
}

$("#save-button").on("click", function () {
    blockCardDody();
    clearAllErrorMessage();
    let formData = collectData();
    sendData(formData);
});

function collectData() {
    let formData = new FormData();
    $("#form").find('input').each(function (){
        formData.append($(this).attr("id"), $(this).val());
    });
    let status = $("#status").val() == null? '': $("#status").val();
    formData.append("status", status);
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
        success: function (response) {
            window.location = "../../houses";
        },
        error: function (error) {
            printErrorMessageToField(error);
        }
    });
}

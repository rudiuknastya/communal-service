let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
$(document).ready(function () {
    initializeSelects();
    getHouse();
});

function initializeSelects() {
    initializeCitySelect();
    initializeStreetSelect()
    initializeStatusSelect();
    initializeChairmanSelect();
}
function initializeCitySelect() {
    $("#city").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#city").parent(),
        language: "uk",
        maximumInputLength: 100,
        placeholder: "Оберіть місто",
        ajax: {
            type: "GET",
            url: "../get-cities",
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
                            text: item.city,
                            id: item.ref
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
function initializeStreetSelect() {
    $("#street").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#street").parent(),
        language: "uk",
        maximumInputLength: 100,
        placeholder: "Оберіть вулицю",
        ajax: {
            type: "GET",
            url: "../get-streets",
            data: function (params) {
                return {
                    search: params.term,
                    page: params.page || 1,
                    cityRef: $("#city").val()
                };
            },
            processResults: function (response) {
                console.log(response);
                return {
                    results: $.map(response.content, function (item) {
                        return {
                            text: item.street,
                            id: item.street
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
    let cityOption = new Option(response.city, response.city, true, true);
    $('#city').append(cityOption).trigger('change');
    let streetOption = new Option(response.street, response.street, true, true);
    $('#street').append(streetOption).trigger('change');

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
    let city = $("#city  option:selected").text();
    formData.append("city", city);
    let street = $("#street").val() == null? '': $("#street").val();
    formData.append("street", street);
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
            toastr.error(errorMessage);
            printErrorMessageToField(error);
        }
    });
}

$("#city").on("change", function () {
    $("#street").prop('disabled',  false);
    $("#street").val(null).trigger('change');
});
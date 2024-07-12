
$(document).ready(function () {
    initializeSelects();
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
            url: "get-cities",
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
            url: "get-streets",
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
            url: "get-statuses",
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
        language: "uk",
        maximumInputLength: 100,
        placeholder: "Оберіть голову",
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
        success: function () {
            window.location = "../houses";
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
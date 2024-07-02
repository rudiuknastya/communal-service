let token = $("meta[name='_csrf']").attr("content");
const errorMessage = "Виникла помилка!";
$(document).ready(function () {
    initializeInputMasks();
    initializeSelects();
});

function initializeInputMasks() {
    new Cleave('.phoneNumber', {
        numericOnly: true,
        blocks: [0, 15],
        delimiters: ["+"]
    });
    new Cleave('.personalAccount', {
        numericOnly: true,
        blocks: [4, 4, 4, 4],
        delimiters: ["-"]
    });
}

function initializeSelects() {
    initializeCitySelect();
    initializeStreetSelect();
    initializeHouseNumberSelect();
}

function initializeCitySelect() {
    $("#city").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#city").parent(),
        maximumInputLength: 100,
        placeholder: "Оберіть місто",
        ajax: {
            type: "get",
            url: "register/get-cities",
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
                            text: item,
                            id: item
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
        maximumInputLength: 100,
        placeholder: "Оберіть вулицю",
        ajax: {
            type: "get",
            url: "register/get-streets",
            data: function (params) {
                return {
                    search: params.term,
                    page: params.page || 1,
                    city: $("#city").val(),
                    number: $("#number  option:selected").text()
                };
            },
            processResults: function (response) {
                return {
                    results: $.map(response.content, function (item) {
                        return {
                            text: item,
                            id: item
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

function initializeHouseNumberSelect() {
    $("#houseId").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#houseId").parent(),
        maximumInputLength: 100,
        allowClear: true,
        placeholder: "Оберіть номер будинку",
        ajax: {
            type: "get",
            url: "register/get-numbers",
            data: function (params) {
                return {
                    search: params.term,
                    page: params.page || 1,
                    city: $("#city").val(),
                    street: $("#street").val()
                };
            },
            processResults: function (response) {
                return {
                    results: $.map(response.content, function (item) {
                        return {
                            text: item.number,
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

$("#city").on("change", function () {
    $("#street").val(null).trigger('change');
    $("#street").prop('disabled', false);
});

$("#street").on("change", function () {
    $("#houseId").val(null).trigger('change');
    $("#houseId").prop('disabled', false);
});

$("#apartmentNumber").on("input", function () {
    $(this).val($(this).val().replace(/[^0-9]/g, '')
        .replace(/(\..*?)\..*/g, '$1'));
});

$("#area").on("input", function () {
    $(this).val($(this).val().replace(/[^0-9.]/g, '')
        .replace(/(\..*?)\..*/g, '$1'));
});

$("#register-button").on("click", function () {
    blockCardDody();
    clearAllErrorMessage();
    let formData = collectData();
    sendData(formData);
});

function collectData() {
    let formData = new FormData();
    $("#form").find('input:text, input:password').each(function (){
        formData.append($(this).attr("id"), $(this).val());
    });
    formData.append("apartmentNumber", $("#apartmentNumber").val());
    formData.append("area", $("#area").val());
    let number = $("#houseId").val() == null? '': $("#houseId").val();
    formData.append("houseId", number);
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
            window.location = "register/success";
        },
        error: function (error) {
            toastr.error(errorMessage);
            printErrorMessageToField(error);
        }
    });
}
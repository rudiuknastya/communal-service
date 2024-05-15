let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
$(document).ready(function () {
    getUser();
    initializeInputMasks();
    initializeSelects();
});

function getUser() {
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
        if(key.localeCompare("avatar") !== 0)
            $("#" + key).val(value);
    });
    let statusOption = new Option(getStatus(response.status), response.status, true, true);
    $('#status').append(statusOption).trigger('change');
    let cityOption = new Option(response.city, response.city, true, true);
    $('#city').append(cityOption).trigger('change');
    let streetOption = new Option(response.street, response.street, true, true);
    $('#street').append(streetOption).trigger('change');
    let numberOption = new Option(response.houseNumberResponse.number, response.houseNumberResponse.id, true, true);
    $('#number').append(numberOption).trigger('change');
    $("#avatar-img").attr("src","../../../uploads/"+response.avatar);
}

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
    initializeStatusSelect();
}

function initializeCitySelect() {
    $("#city").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#city").parent(),
        maximumInputLength: 100,
        placeholder: "Оберіть місто",
        ajax: {
            type: "get",
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
            url: "../get-streets",
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
    $("#number").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#number").parent(),
        maximumInputLength: 100,
        allowClear: true,
        placeholder: "Оберіть номер будинку",
        ajax: {
            type: "get",
            url: "../get-numbers",
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

function initializeStatusSelect() {
    $("#status").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#status").parent(),
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

$("#city").on("change", function () {
    $("#street").val(null).trigger('change');
    $("#street").prop('disabled', false);
});

$("#street").on("change", function () {
    $("#number").val(null).trigger('change');
    $("#number").prop('disabled', false);
});

$("#generatePassword").on("click", function () {
    let password = generatePassword();
    $("#password").val(password);
    $("#confirmPassword").val(password);
});

function generatePassword(){
    var passwordLength = Math.random() * (16 - 8) + 8;
    var password = "";
    while(password.length < passwordLength) {
        password += getLowerCase() + getUpperCase()  + getNumber();
    }
    password += getSymbol() + getNumber();
    return password;
}
const types = {
    upperCase: "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
    lowerCase: "abcdefghijklmnopqrstuvwxyz",
    numbers: "0123456789",
    symbols: ",./?"
}
function getUpperCase() {
    return types.upperCase[Math.floor(Math.random() * types.upperCase.length)];
}

function getLowerCase() {
    return types.lowerCase[Math.floor(Math.random() * types.lowerCase.length)];
}

function getNumber() {
    return types.numbers[Math.floor(Math.random() * types.numbers.length)];
}

function getSymbol() {
    return types.symbols[Math.floor(Math.random() * types.symbols.length)];
}


$("#avatar").on("change", function () {
    let myFile = $(this).prop('files');
    if(validateFile(myFile[0].name)){
        $("#avatar-img").attr("src", window.URL.createObjectURL(myFile[0]));
    } else {
        $("#avatarValidation").text(fileValidation);
        $(this).val('');
    }
});
function validateFile(value){
    let ext = value.substring(value.lastIndexOf('.') + 1).toLowerCase();
    if(value !== "" && $.inArray(ext, ['png','jpg','jpeg']) === -1) {
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
    $("#form").find('input:text, input:password').each(function (){
        formData.append($(this).attr("id"), $(this).val());
    });
    formData.append("apartmentNumber", $("#apartmentNumber").val());
    formData.append("area", $("#area").val());
    let status = $("#status").val() == null? '': $("#status").val();
    formData.append("status", status);
    let number = $("#number").val() == null? '': $("#number").val();
    formData.append("number", number);
    let avatar = $('#avatar').prop('files')[0];
    if(avatar === undefined) {
        formData.append("avatar",  new File([""], "filename"));
    } else {
        formData.append("avatar", avatar);
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
            window.location = "../../users";
        },
        error: function (error) {
            printErrorMessageToField(error);
        }
    });
}

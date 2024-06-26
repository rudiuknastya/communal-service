
$(document).ready(function () {
    autosize($("#text"));
    initializeStatusSelect();
    initializeFlatPickers()
});

function initializeFlatPickers() {
    $("#startDate").flatpickr({
        locale: "uk",
        enableTime: true,
        dateFormat: 'd.m.Y H:i',
        minDate: moment().format('DD.MM.YYYY HH:MM')
    });
    $("#endDate").flatpickr({
        locale: "uk",
        enableTime: true,
        dateFormat: 'd.m.Y H:i',
        minDate: moment().format('DD.MM.YYYY HH:MM')
    });
}

function initializeStatusSelect() {
    $("#status").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#status").parent(),
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
        case 'ACTIVE':
            return "Активне";
        case 'CLOSED':
            return "Закрито";
    }
}

$("#quorum").on("input", function () {
    $(this).val($(this).val().replace(/[^0-9]/g, '')
        .replace(/(\..*?)\..*/g, '$1'));

})

$("#save-button").on("click", function () {
    blockCardDody();
    clearAllErrorMessage();
    let formData = collectData();
    sendData(formData);
});

function collectData() {
    let formData = new FormData();
    $("#form").find('input:text, textarea').each(function (){
        if($(this).attr("id").localeCompare("startDate") !== 0 &&
            $(this).attr("id").localeCompare("endDate") !== 0) {
            formData.append($(this).attr("id"), $(this).val());
        }
    });
    let status = $("#status").val() == null? '': $("#status").val();
    formData.append("status", status);
    let startDate = $("#startDate").val()
    if (startDate.localeCompare('') === 0) {
        formData.append("startDate", '');
    } else {
        const splitStartDate = startDate.split(" ");
        let formattedDate = moment(splitStartDate[0], 'DD.MM.YYYY').format('YYYY-MM-DD');
        formData.append("startDate", formattedDate+"T"+splitStartDate[1]);
    }
    let endDate = $("#endDate").val()
    if (endDate.localeCompare('') === 0) {
        formData.append("endDate", '');
    } else {
        const splitEndDate = endDate.split(" ");
        let formattedDate = moment(splitEndDate[0], 'DD.MM.YYYY').format('YYYY-MM-DD');
        formData.append("endDate", formattedDate+"T"+splitEndDate[1]);
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
            window.location = "../voting";
        },
        error: function (error) {
            toastr.error(errorMessage);
            printErrorMessageToField(error);
        }
    });
}

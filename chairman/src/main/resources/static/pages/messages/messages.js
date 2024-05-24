
let tableLength = 2;
let request = {
    page: 0,
    pageSize: tableLength,
    fullName: "",
    apartmentNumber: "",
    phoneNumber: "",
    subject: "",
    dateFrom: "",
    dateTo: ""
};

$(document).ready(function () {
    getMessages(0);
    initializeFlatPickers();
});

function getMessages(currentPage) {
    blockCardDody();
    request.page = currentPage;
    request.pageSize = tableLength;
    $.ajax({
        type: "GET",
        url: "messages/get",
        data: request,
        success: function (response) {
            $("tbody").empty();
            $("ul.pagination").empty();
            $("#paginationElements").empty();
            drawTable(response);
            console.log(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function drawTable(response) {
    if (response.numberOfElements == 0) {
        let tdCount = $("td").length;
        $("tbody").append(`<tr class="tr"><td colspan="${tdCount}" class="text-center">${dataNotFound}</td>></tr>`);
    } else {
        let i = response.pageable.pageNumber+1;
        for (let message of response.content) {
            $("tbody")
                .append(
                    `<tr class="tr text-nowrap">
                        <td>${i}</td>
                        <td>${message.fullName}</td>
                        <td>${message.apartmentNumber}</td>
                        <td>${message.phoneNumber}</td>
                        <td>${message.subject}</td>
                        <td>${formDate(message.creationDate)}</td>
                        <td>
                            <a href="messages/${message.id}">
                            <i class="ti ti-eye me-1"></i>
                            </a>
                        </td>
                    </tr>`
                );
            i++;
        }
    }
    buildPagination(response, "getMessages");
}

function formDate(date) {
    const splitDate = date.split("T");
    const datePart = moment(splitDate[0], 'YYYY-MM-DD').format('DD.MM.YYYY');
    return datePart + " "+splitDate[1];
}

function initializeFlatPickers() {
    $("#filter-by-dateFrom").flatpickr({
        locale: "uk",
        dateFormat: 'd.m.Y'
    });
    $("#filter-by-dateTo").flatpickr({
        locale: "uk",
        dateFormat: 'd.m.Y'
    });
}

$("#filter-by-fullName").on("change", function () {
    request.fullName = $(this).val();
    getMessages(0);
});

$("#filter-by-apartmentNumber").on("change", function () {
    request.apartmentNumber = $(this).val();
    getMessages(0);
});
$("#filter-by-apartmentNumber").on("input", function () {
    $(this).val($(this).val().replace(/[^0-9]/g, '')
        .replace(/(\..*?)\..*/g, '$1'));
});
$("#filter-by-phoneNumber").on("change", function () {
    request.phoneNumber = $(this).val();
    getMessages(0);
});

$("#filter-by-subject").on("change", function () {
    request.subject = $(this).val();
    getMessages(0);
});

$("#filter-by-dateFrom").on("change", function () {
    let date = $(this).val();
    if(date.localeCompare("") !== 0) {
        let formattedDate = moment($(this).val(), 'DD.MM.YYYY').format('YYYY-MM-DD');
        request.dateFrom = formattedDate;
    } else {
        request.dateFrom = date;
    }
    getMessages(0);
});

$("#filter-by-dateTo").on("change", function () {
    let date = $(this).val();
    if(date.localeCompare("") !== 0) {
        let formattedDate = moment($(this).val(), 'DD.MM.YYYY').format('YYYY-MM-DD');
        request.dateTo = formattedDate;
    } else {
        request.dateTo = date;
    }
    getMessages(0);
});

$("#clear-filters").on("click", function () {
    $("#filter-by-fullName, #filter-by-apartmentNumber, #filter-by-phoneNumber, #filter-by-subject, #filter-by-dateFrom, #filter-by-dateTo")
        .val("").trigger('change');
});
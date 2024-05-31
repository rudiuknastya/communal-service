let tableLength = 2;
let request = {
    page: 0,
    pageSize: tableLength,
    number: "",
    personalAccount: "",
    creationDateFrom: "",
    creationDateTo: "",
};

$(document).ready(function () {
    getInvoices(0);
    initializeFlatPickers();
});

function getInvoices(currentPage) {
    blockCardDody();
    request.page = currentPage;
    request.pageSize = tableLength;
    $.ajax({
        type: "GET",
        url: "invoices/get",
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
        for (let invoice of response.content) {
            $("tbody")
                .append(
                    `<tr class="tr text-nowrap">
                        <td><input class="form-check-input checks" name="checks" type="checkbox" id="${invoice.id}"></td>
                        <td>${i}</td>
                        <td>${invoice.number}</td>
                        <td>${invoice.personalAccount}</td>
                        <td>${formatDate(invoice.creationDate)}</td>
                        <td>
                            <div class="dropdown">
                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                    <i class="ti ti-dots-vertical"></i>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="users/edit/${invoice.id}">
                                        <i class="ti ti-pencil me-1"></i>${buttonLabelEdit}
                                    </a>
                                    <button type="button" class="dropdown-item btn justify-content-start">
                                        <i class="ti ti-printer me-1"></i>Роздрукувати
                                    </button>
                                </div>
                            </div>
                        </td> 
                    </tr>`
                );
            i++;
        }
    }
    buildPagination(response, "getInvoices");
}

function formatDate(date) {
    return moment(date, 'YYYY-MM-DD').format('DD.MM.YYYY');
}

function initializeFlatPickers() {
    $("#filter-by-creationDateFrom").flatpickr({
        locale: "uk",
        dateFormat: 'd.m.Y'
    });
    $("#filter-by-creationDateTo").flatpickr({
        locale: "uk",
        dateFormat: 'd.m.Y'
    });
}

$("#filter-by-number").on("change", function () {
    request.number = $(this).val();
    getInvoices(0);
});

$("#filter-by-personalAccount").on("change", function () {
    request.personalAccount = $(this).val();
    getInvoices(0);
});

$("#filter-by-creationDateFrom").on("change", function () {
    if($(this).val().localeCompare("") === 0){
        request.creationDateFrom = $(this).val();
    } else {
        request.creationDateFrom = moment($(this).val(),'DD.MM.YYYY').format('YYYY-MM-DD');
    }
    getInvoices(0);
});

$("#filter-by-creationDateTo").on("change", function () {
    if($(this).val().localeCompare("") === 0){
        request.creationDateTo = $(this).val();
    } else {
        request.creationDateTo = moment($(this).val(), 'DD.MM.YYYY').format('YYYY-MM-DD');
    }
    getInvoices(0);
});

$("#delete-button").on("click", function () {
    let invoiceIds = [];
    $("input[name=checks]:checked").each(function () {
        invoiceIds.push($(this).attr("id"));
    });
    deleteInvoices(invoiceIds);
});

function deleteInvoices(invoiceIds) {
    if (invoiceIds.length != 0) {
        blockCardDody();
        $.ajax({
            type: "GET",
            url: "invoices/delete-invoices",
            data: {
                invoiceIds: invoiceIds
            },
            success: function () {
                toastr.success(deleteSuccessMessage);
                getInvoices(0)
            },
            error: function () {
                toastr.error(errorMessage);
            }
        });
    } else {
        toastr.warning("Не вибрано елемент для видалення");
    }
}

$("#mainCheck").on("change", function () {
    $(".checks").each(function () {
        $(this).prop("checked", $("#mainCheck").is(":checked"));
    });
});

$("#clear-filters").on("click", function () {
    $("#filter-by-number, #filter-by-personalAccount,#filter-by-creationDateFrom, #filter-by-creationDateTo")
        .val("").trigger('change');
});
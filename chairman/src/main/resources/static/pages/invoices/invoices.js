let tableLength = 2;
let request = {
    page: 0,
    pageSize: tableLength,
    number: "",
    personalAccount: "",
    creationDateFrom: "",
    creationDateTo: "",
};
const BORDER_ALL = {
    top: {style: 'thin', color: {rgb: '000000'}},
    right: {style: 'thin', color: {rgb: '000000'}},
    bottom: {style: 'thin', color: {rgb: '000000'}},
    left: {style: 'thin', color: {rgb: '000000'}}
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
                                    <button type="button" class="dropdown-item btn justify-content-start" onclick="printRow(this)">
                                        <i class="ti ti-printer me-1"></i>Роздрукувати
                                    </button>
                                    <a class="dropdown-item" href="invoices/edit/${invoice.id}">
                                        <i class="ti ti-pencil me-1"></i>${buttonLabelEdit}
                                    </a>
                                    <button type="button" class="dropdown-item btn justify-content-start" onclick="toExcel(this)">
                                        <i class="ti ti-download me-1"></i>Завантажити в Excel
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

function printRow(printButton) {
    let row = formRow(printButton);
    let table = [];
    table.push(row);
    printJS({
        printable: table,
        properties: ['№ квитанції', 'Особистий рахунок', 'Дата'],
        type: 'json',
        header: "Квитанція"
    });
}

function formRow(printButton) {
    let i = 0;
    $td = $(printButton).parent().parent().parent();
    let row = {};
    $($td).siblings().each(function () {
        if(i > 1) {
            setObject(row, i, $(this).text());
        }
        i++;
    });
    return row;
}

function setObject(object, i, value) {
    switch (i) {
        case 2:
            object["№ квитанції"] = value;
        case 3:
            object["Особистий рахунок"] = value;
        case 4:
            object["Дата"] = value;
    }
}

function toExcel(toExcelButton) {
    let name = createFileName();
    let table = getTable(toExcelButton);
    var workbook = XLSX.utils.book_new();
    var worksheet = XLSX.utils.aoa_to_sheet(table);
    styleTable(worksheet);
    XLSX.utils.book_append_sheet(workbook, worksheet, "Invoices Data Table");
    XLSX.writeFile(workbook, name, {bookType: 'xlsx', type: 'base64'});
}

function createFileName(toExcelButton) {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var yyyy = today.getFullYear();
    return "invoice-"+dd+"-"+mm+"-"+yyyy+".xlsx";
}

function getTable(toExcelButton) {
    let table = [];
    let head = [];
    let headText = ["№ квитанції", "Особистий рахунок", "Дата"];
    for(let text of headText){
        head.push({
            v: text,
            t: "s",
            s: {font: {bold: true}, border: BORDER_ALL, alignment: {horizontal: 'center'}}
        });
    }
    table.push(head);
    let i = 0;
    let row = []
    $td = $(toExcelButton).parent().parent().parent();
    $($td).siblings().each(function () {
        if(i > 1) {
            let text = $(this).text();
            row.push({
                v: text,
                t: "s",
                s: {border: BORDER_ALL, alignment: {horizontal: 'center'}}
            });
        }
        i++;
    });
    table.push(row);
    // $('#myTHead tr').find('th').not(':first-child').not(':last-child').each(function () {
    //     let text = $(this).text();
    //     head.push({
    //         v: text,
    //         t: "s",
    //         s: {font: {bold: true}, border: BORDER_ALL, alignment: {horizontal: 'center'}}
    //     });
    // });

    // $('tr[data-href]').each(function () {
    //     let row = []
    //     $(this).find('td').not(":last-child").not(':first-child').each(function () {
    //         let text = $(this).text();
    //         row.push({
    //             v: text,
    //             t: "s",
    //             s: {border: BORDER_ALL, alignment: {horizontal: 'center'}}
    //         });
    //     });
    //     table.push(row);
    // });
    return table;
}

function styleTable(worksheet) {
    let DEF_ColW = 20;
    worksheet['!cols'] = [{width: DEF_ColW}, {width: DEF_ColW}, {width: DEF_ColW}];
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
    if ($("input[name=checks]:checked").length !== 0) {
        openDeleteModal();
    } else {
        toastr.warning("Не вибрано елемент для видалення");
    }
});

function openDeleteModal() {
    if($("#deleteModal").length === 0) {
        $("div.card").append(
            `<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <h4>Ви впевнені що хочете видалити ці рахунки?</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-label-secondary close-modal" data-bs-dismiss="modal">
                        Закрити
                        </button>
                        <button type="button" class="btn btn-danger" id="delete-button" onclick="deleteInvoices()">
                            Видалити
                        </button>
                    </div>
                </div>
            </div>
        </div>`
        )
    }
    $('#deleteModal').modal('show');
}

function deleteInvoices() {
    $('#deleteModal').modal('hide');
    $("#mainCheck").prop("checked", false);
    blockCardDody();
    let invoiceIds = [];
    $("input[name=checks]:checked").each(function () {
        invoiceIds.push($(this).attr("id"));
    });
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
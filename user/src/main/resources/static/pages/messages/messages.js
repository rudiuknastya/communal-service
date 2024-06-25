
let tableLength = 2;
let request = {
    page: 0,
    pageSize: tableLength,
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
        let i = (response.size * response.pageable.pageNumber) + 1;
        for (let message of response.content) {
            $("tbody")
                .append(
                    `<tr class="tr text-nowrap">
                        <td><input class="form-check-input checks" name="checks" type="checkbox" id="${message.id}"></td>
                        <td>${i}</td>
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
    const timePart = splitDate[1].split(":");
    return datePart + " "+timePart[0]+":"+timePart[1];
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

$("#mainCheck").on("change", function () {
    $(".checks").each(function () {
        $(this).prop("checked", $("#mainCheck").is(":checked"));
    });
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
    $("#filter-by-subject, #filter-by-dateFrom, #filter-by-dateTo")
        .val("").trigger('change');
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
                        <h4>Ви впевнені що хочете видалити ці повідомлення?</h4>
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
    let messageIds = [];
    $("input[name=checks]:checked").each(function () {
        messageIds.push($(this).attr("id"));
    });
    $.ajax({
        type: "DELETE",
        url: "messages/delete-messages",
        data: {
            messageIds: messageIds
        },
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            toastr.success("Видалення успішне");
            getMessages(0)
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}
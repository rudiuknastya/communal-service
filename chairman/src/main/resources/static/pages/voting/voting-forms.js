let entityId;
let tableLength = 2;
let request = {
    page: 0,
    pageSize: tableLength,
    subject: "",
    endDate: "",
    status: "",
    resultStatus: ""
};

$(document).ready(function () {
    getVotingForms(0);
    initializeSelects();
    initializeFlatPicker();
});

function getVotingForms(currentPage) {
    blockCardDody();
    request.page = currentPage;
    request.pageSize = tableLength;
    $.ajax({
        type: "GET",
        url: "voting/get",
        data: request,
        success: function (response) {
            emptyTableAndPagination();
            drawTable(response);
            console.log(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function emptyTableAndPagination() {
    $("tbody").empty();
    $("ul.pagination").empty();
    $("#paginationElements").empty();
}

function drawTable(response) {
    if (response.numberOfElements == 0) {
        let tdCount = $("td").length;
        $("tbody").append(`<tr class="tr"><td colspan="${tdCount}" class="text-center">${dataNotFound}</td>></tr>`);
    } else {
        let i = response.pageable.pageNumber+1;
        for (let form of response.content) {
            $("tbody")
                .append(
                    `<tr class="tr text-nowrap">
                        <td>${i}</td>
                        <td>${form.subject}</td>
                        <td>${formatDate(form.endDate)}</td>
                        <td>${getStatusSpan(form.status)}</td>
                        <td>${getResultStatusSpan(form.resultStatus)}</td>
                        <td>${formatVoted(form.voted)}</td>
                        <td>
                            <div class="dropdown">
                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                    <i class="ti ti-dots-vertical"></i>
                                </button>
                                <div class="dropdown-menu">
                                    ${getShowButton(form.status, form.id)}
                                    <a class="dropdown-item" href="voting/edit/${form.id}">
                                        <i class="ti ti-pencil me-1"></i>${buttonLabelEdit}
                                    </a>
                                    <button type="button" class="dropdown-item btn justify-content-start" onclick="openDeleteModal(${form.id}, '${form.subject}')">
                                        <i class="ti ti-trash me-1"></i>${buttonLabelDelete}
                                    </button>
                                </div>
                            </div>
                        </td> 
                    </tr>`
                );
            i++;
        }
    }
    buildPagination(response, "getVotingForms");
}
function formatDate(date) {
    return moment(date, 'YYYY-MM-DD HH:MM').format('DD.MM.YYYY HH:MM')
}

function getStatusSpan(status) {
    switch (status) {
        case 'ACTIVE':
            return '<span class="badge bg-label-success">Активне</span>';
        case 'CLOSED':
            return '<span class="badge bg-label-danger">Закрито</span>';
    }
}

function getResultStatusSpan(status) {
    switch (status) {
        case 'ACCEPTED':
            return '<span class="badge bg-label-success">Прийнято</span>';
        case 'REJECTED':
            return '<span class="badge bg-label-danger">Відхилено</span>';
        default:
            return "";
    }
}

function formatVoted(voted) {
    const votes = voted.split("/");
    if(votes.length === 3){
        return "<span style='color: limegreen'>"+votes[0]
            +"</span>/<span style='color: yellow'>"
            +votes[1]+"</span>/<span style='color: red'>"+votes[2]+"</span>";
    } else {
        return voted;
    }
}
function getShowButton(status, id) {
    if(status.localeCompare("CLOSED") === 0){
        return '<a class="dropdown-item" href="voting/'+id+'">'
            +'<i class="ti ti-eye me-1"></i>Перегляд</a>'
    } else {
        return '';
    }
}
function openDeleteModal(id, subject) {
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
                        <h4>Ви впевнені що хочете видалити голосування "${subject}"?</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-label-secondary close-modal" data-bs-dismiss="modal">
                        Закрити
                        </button>
                        <button type="button" class="btn btn-danger" id="delete-button" onclick="deleteEntity()">
                            Видалити
                        </button>
                    </div>
                </div>
            </div>
        </div>`
        )
    }
    $('#deleteModal').modal('show');
    entityId = id;
}

function deleteEntity() {
    blockCardDody();
    $.ajax({
        type: "DELETE",
        url: "voting/delete/"+entityId,
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function (response) {
            $('#deleteModal').modal('hide');
            getVotingForms(0);
            toastr.success(deleteSuccessMessage);
        },
        error: function (error) {
            $('#deleteModal').modal('hide');
            toastr.error(errorMessage);
        }
    });
}

function initializeSelects() {
    initializeStatusSelect();
    initializeResultStatusSelect();
}

function initializeStatusSelect() {
    $("#filter-by-status").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        minimumResultsForSearch: -1,
        placeholder: "Оберіть статус",
        allowClear: true,
        ajax: {
            type: "GET",
            url: "voting/get-statuses",
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

function initializeResultStatusSelect() {
    $("#filter-by-result").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        minimumResultsForSearch: -1,
        placeholder: "Оберіть результат",
        allowClear: true,
        ajax: {
            type: "GET",
            url: "voting/get-resultStatuses",
            processResults: function (response) {
                return {
                    results: $.map(response, function (item) {
                        return {
                            text: getResultStatus(item),
                            id: item
                        }
                    })
                };
            }

        }
    });
}

function getResultStatus(status) {
    switch (status) {
        case 'ACCEPTED':
            return "Прийнято";
        case 'REJECTED':
            return "Відхилено";
    }
}

function initializeFlatPicker() {
    $("#filter-by-endDate").flatpickr({
        locale: "uk",
        dateFormat: 'd.m.Y'
    });
}

$("#filter-by-status").on("change", function () {
    request.status = $(this).val();
    getVotingForms(0);
});

$("#filter-by-subject").on("change", function () {
    request.subject = $(this).val();
    getVotingForms(0);
});

$("#filter-by-result").on("change", function () {
    request.resultStatus = $(this).val();
    getVotingForms(0);
});

$("#filter-by-endDate").on("change", function () {
    let date = $(this).val();
    if(date.localeCompare("") !== 0) {
        let formattedEndDate = moment($(this).val(), 'DD.MM.YYYY').format('YYYY-MM-DD');
        request.endDate = formattedEndDate;
    } else {
        request.endDate = date;
    }
    getVotingForms(0);
});

$("#clear-filters").on("click", function () {
    $("#filter-by-subject, #filter-by-endDate, #filter-by-status, #filter-by-result")
        .val("").trigger('change');
});
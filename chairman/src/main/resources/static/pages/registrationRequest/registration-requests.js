
let tableLength = 2;
let request = {
    page: 0,
    pageSize: tableLength,
    fullName: "",
    email: "",
    status: ""
};

$(document).ready(function () {
    getRegistrationRequests(0);
    initializeStatusSelect();
});

function getRegistrationRequests(currentPage) {
    blockCardDody();
    request.page = currentPage;
    request.pageSize = tableLength;
    $.ajax({
        type: "GET",
        url: "requests/get",
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
        for (let request of response.content) {
            $("tbody")
                .append(
                    `<tr class="tr text-nowrap">
                        <td><input class="form-check-input checks" name="checks" type="checkbox" id="${request.id}"></td>
                        <td>${i}</td>
                        <td>${request.fullName}</td>
                        <td>${request.email}</td>
                        <td>${getStatusSpan(request.status)}</td>
                        <td>
                            <div class="dropdown">
                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                    <i class="ti ti-dots-vertical"></i>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="requests/${request.id}">
                                        <i class="ti ti-pencil me-1"></i>Обробити
                                    </a>
                                </div>
                            </div>
                        </td> 
                    </tr>`
                );
            i++;
        }
    }
    buildPagination(response, "getRegistrationRequests");
}

function getStatusSpan(status) {
    switch (status) {
        case 'IN_PROCESS':
            return '<span class="badge bg-label-info">В процесі</span>';
        case 'ACCEPTED':
            return '<span class="badge bg-label-success">Прийнята</span>';
        case 'REJECTED':
            return '<span class="badge bg-label-danger">Відхилена</span>';
    }
}

function initializeStatusSelect() {
    $("#filter-by-status").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        minimumResultsForSearch: -1,
        allowClear: true,
        placeholder: "Оберіть статус",
        ajax: {
            type: "GET",
            url: "requests/get-statuses",
            processResults: function (response) {
                console.log(response);
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
        case 'IN_PROCESS':
            return 'В процесі';
        case 'ACCEPTED':
            return 'Прийнята';
        case 'REJECTED':
            return 'Відхилена';
    }
}

$("#filter-by-fullName").on("change", function () {
    request.fullName = $(this).val();
    getRegistrationRequests(0);
});

$("#filter-by-email").on("change", function () {
    request.email = $(this).val();
    getRegistrationRequests(0);
});

$("#filter-by-status").on("change", function () {
    request.status = $(this).val();
    getRegistrationRequests(0);
});

$("#clear-filters").on("click", function () {
    $("#filter-by-fullName, #filter-by-email, #filter-by-status")
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
                        <h4>Ви впевнені що хочете видалити ці заявки?</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-label-secondary close-modal" data-bs-dismiss="modal">
                        Закрити
                        </button>
                        <button type="button" class="btn btn-danger" id="delete-button" onclick="deleteRequests()">
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

function deleteRequests() {
    $('#deleteModal').modal('hide');
    $("#mainCheck").prop("checked", false);
    blockCardDody();
    let requestIds = [];
    $("input[name=checks]:checked").each(function () {
        requestIds.push($(this).attr("id"));
    });
    $.ajax({
        type: "GET",
        url: "requests/delete-requests",
        data: {
            requestIds: requestIds
        },
        success: function () {
            toastr.success(deleteSuccessMessage);
            getRegistrationRequests(0)
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
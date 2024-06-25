let entityId;
let tableLength = 2;
let request = {
    page: 0,
    pageSize: tableLength,
    fullName: "",
    phoneNumber: "",
    status: "",
};
$(document).ready(function () {
    getChairmen(0);
    initializeStatusSelect();
});

function getChairmen(currentPage) {
    blockCardDody();
    request.page = currentPage;
    request.pageSize = tableLength;
    $.ajax({
        type: "GET",
        url: "chairmen/get",
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
        for (let chairman of response.content) {
            $("tbody")
                .append(
                    `<tr class="tr text-nowrap">
                        <td>${i}</td>
                        <td>${chairman.fullName}</td>
                        <td>${chairman.phoneNumber}</td>
                        <td>${getStatusSpan(chairman.status)}</td>
                        <td>
                            <div class="dropdown">
                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                    <i class="ti ti-dots-vertical"></i>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="chairmen/edit/${chairman.id}">
                                        <i class="ti ti-pencil me-1"></i>${buttonLabelEdit}
                                    </a>
                                    <button type="button" class="dropdown-item btn justify-content-start" onclick="checkIfPossibleToDelete(${chairman.id}, '${chairman.fullName}')">
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
    buildPagination(response, "getChairmen");
}

function getStatusSpan(status) {
    switch (status) {
        case 'ACTIVE':
            return '<span class="badge bg-label-success">Активний</span>';
        case 'DISABLED':
            return '<span class="badge bg-label-danger">Вимкнений</span>';
    }
}
function checkIfPossibleToDelete(id, fullName) {
    $.ajax({
        type: "GET",
        url: "chairmen/check-delete/"+id,
        data: request,
        success: function (response) {
            if(response){
                openDeleteModal(id, fullName);
            } else {
                toastr.warning("Неможливо видалити. Голова привязаний до будинку");
            }
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}

function openDeleteModal(chairmanId, fullName) {
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
                        <h4>Ви впевнені що хочете видалити голову ${fullName}?</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-label-secondary close-modal" data-bs-dismiss="modal">
                        Закрити
                        </button>
                        <button type="button" class="btn btn-danger" id="delete-button" onclick="deleteEntry()">
                            Видалити
                        </button>
                    </div>
                </div>
            </div>
        </div>`
        )
    }
    $('#deleteModal').modal('show');
    entityId = chairmanId;
}

function deleteEntry() {
    blockCardDody();
    $.ajax({
        type: "DELETE",
        url: "chairmen/delete/"+entityId,
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            $('#deleteModal').modal('hide');
            getChairmen(0);
            toastr.success(deleteSuccessMessage);
        },
        error: function () {
            $('#deleteModal').modal('hide');
            toastr.error(errorMessage);
        }
    });
}

function initializeStatusSelect() {
    $("#filter-by-status").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        language: "uk",
        minimumResultsForSearch: -1,
        placeholder: "Оберіть статус",
        allowClear: true,
        ajax: {
            type: "GET",
            url: "chairmen/get-statuses",
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
            return "Активний";
        case 'DISABLED':
            return "Вимкнений";
    }
}

$("#filter-by-fullName").on("change", function () {
    request.fullName = $(this).val();
    getChairmen(0);
});
$("#filter-by-phoneNumber").on("change", function () {
    request.phoneNumber = $(this).val();
    getChairmen(0);
});
$("#filter-by-status").on("change", function () {
    request.status = $(this).val();
    getChairmen(0);
});

$("#clear-filters").on("click", function () {
    $('#filter-by-status, #filter-by-phoneNumber, #filter-by-fullName')
        .val("").trigger('change');
});


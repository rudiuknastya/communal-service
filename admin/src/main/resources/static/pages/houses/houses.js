let entityId;
let tableLength = 2;
let request = {
    page: 0,
    pageSize: tableLength,
    city: "",
    street: "",
    number: null,
    chairmanId: null,
    status: "",
};

$(document).ready(function () {
    getHouses(0);
    initializeStatusSelect();
    initializeChairmanSelect();
});

function getHouses(currentPage) {
    blockCardDody();
    request.page = currentPage;
    request.pageSize = tableLength;
    $.ajax({
        type: "GET",
        url: "houses/get",
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
        for (let house of response.content) {
            $("tbody")
                .append(
                    `<tr class="tr text-nowrap">
                        <td>${i}</td>
                        <td>${house.city}</td>
                        <td>${house.street}</td>
                        <td>${house.number}</td>
                        <td>${house.chairman}</td>
                        <td>${getStatusSpan(house.status)}</td>
                        <td>
                            <div class="dropdown">
                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                    <i class="ti ti-dots-vertical"></i>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="houses/edit/${house.id}">
                                        <i class="ti ti-pencil me-1"></i>${buttonLabelEdit}
                                    </a>
                                    <button type="button" class="dropdown-item btn justify-content-start" onclick="openDeleteModal(${house.id})">
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
    buildPagination(response, "getHouses");
}

function getStatusSpan(status) {
    switch (status) {
        case 'NEW':
            return '<span class="badge bg-label-info">Новий</span>';
        case 'ACTIVE':
            return '<span class="badge bg-label-success">Активний</span>';
        case 'DISABLED':
            return '<span class="badge bg-label-danger">Вимкнений</span>';
    }
}
$("#filter-by-city").on("change", function () {
    request.city = $(this).val();
    getHouses(0);
});
$("#filter-by-street").on("change", function () {
    request.street = $(this).val();
    getHouses(0);
});
$("#filter-by-number").on("change", function () {
    request.number = $(this).val();
    getHouses(0);
});
$("#filter-by-chairman").on("change", function () {
    request.chairmanId = $(this).val();
    getHouses(0);
});
$("#filter-by-status").on("change", function () {
    request.status = $(this).val();
    getHouses(0);
});

$("#clear-filters").on("click", function () {
    $("#filter-by-city, #filter-by-street, #filter-by-number, #filter-by-chairman, #filter-by-status")
        .val("").trigger('change');
});

function initializeStatusSelect() {
    $("#filter-by-status").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        language: "uk",
        minimumResultsForSearch: -1,
        placeholder: "Оберіть статус",
        allowClear: true,
        ajax: {
            type: "GET",
            url: "houses/get-statuses",
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

function initializeChairmanSelect() {
    $("#filter-by-chairman").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        maximumInputLength: 100,
        placeholder: "Оберіть голову",
        allowClear: true,
        ajax: {
            type: "GET",
            url: "houses/get-chairmen",
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
                            text: item.fullName,
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
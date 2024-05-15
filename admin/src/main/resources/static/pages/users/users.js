let entityId;
let tableLength = 2;
let request = {
    page: 0,
    pageSize: tableLength,
    fullName: "",
    city: "",
    street: "",
    houseNumber: null,
    apartmentNumber: null,
    personalAccount: "",
    phoneNumber: "",
    status: "",
};

$(document).ready(function () {
    let url = window.location.pathname;
    let id = url.substring(url.lastIndexOf('/') + 1);
    if(id.localeCompare("users") === 0) {
        getUsers(0);
    } else {
        getHouse(id);
    }
    initializeSelects();
});
function getHouse(id) {
    $.ajax({
        type: "GET",
        url: "get/"+id,
        success: function (response) {
            setHouseFilter(response);
        },
        error: function () {
            toastr.error(errorMessage);
        }
    });
}
function setHouseFilter(response) {
    let cityOption = new Option(response.city, response.city, true, true);
    $('#filter-by-city').append(cityOption).trigger('change');
    let streetOption = new Option(response.street, response.street, true, true);
    $('#filter-by-street').append(streetOption).trigger('change');
    let numberOption = new Option(response.number, response.number, true, true);
    $('#filter-by-houseNumber').append(numberOption).trigger('change');
    $("#filter-by-houseNumber").prop('disabled', false);
}
function getUsers(currentPage) {
    blockCardDody();
    request.page = currentPage;
    request.pageSize = tableLength;
    $.ajax({
        type: "GET",
        url: getUrl,
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
        for (let user of response.content) {
            $("tbody")
                .append(
                    `<tr class="tr text-nowrap">
                        <td>${i}</td>
                        <td>${user.fullName}</td>
                        <td>${user.city}</td>
                        <td>${user.street}</td>
                        <td>${user.houseNumber}</td>
                        <td>${user.apartmentNumber}</td>
                        <td>${user.personalAccount}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${getStatusSpan(user.status)}</td>
                        <td>
                            <div class="dropdown">
                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                    <i class="ti ti-dots-vertical"></i>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="users/edit/${user.id}">
                                        <i class="ti ti-pencil me-1"></i>${buttonLabelEdit}
                                    </a>
                                    <button type="button" class="dropdown-item btn justify-content-start" onclick="openDeleteModal(${user.id}, '${user.fullName}')">
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
    buildPagination(response, "getUsers");
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

function openDeleteModal(userId, fullName) {
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
                        <h4>Ви впевнені що хочете видалити користувача ${fullName}?</h4>
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
    entityId = userId;
}

function deleteEntry() {
    blockCardDody();
    $.ajax({
        type: "DELETE",
        url: deleteUrl+""+entityId,
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function (response) {
            $('#deleteModal').modal('hide');
            getUsers(0);
            toastr.success(deleteSuccessMessage);
        },
        error: function (error) {
            $('#deleteModal').modal('hide');
            toastr.error(errorMessage);
        }
    });
}

function initializeSelects() {
    initializeCitySelect();
    initializeStreetSelect();
    initializeHouseNumberSelect();
    initializeStatusSelect();
}

function initializeCitySelect() {
    $("#filter-by-city").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        maximumInputLength: 100,
        allowClear: true,
        placeholder: "Оберіть місто",
        ajax: {
            type: "GET",
            url: citySelectUrl,
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
    $("#filter-by-street").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        maximumInputLength: 100,
        allowClear: true,
        placeholder: "Оберіть вулицю",
        ajax: {
            type: "GET",
            url: streetSelectUrl,
            data: function (params) {
                return {
                    search: params.term,
                    page: params.page || 1,
                    city: $("#filter-by-city").val(),
                    number: $("#filter-by-houseNumber").val()
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
    $("#filter-by-houseNumber").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        maximumInputLength: 100,
        allowClear: true,
        placeholder: "Оберіть номер будинку",
        ajax: {
            type: "GET",
            url: numberSelectUrl,
            data: function (params) {
                return {
                    search: params.term,
                    page: params.page || 1,
                    city: $("#filter-by-city").val(),
                    street: $("#filter-by-street").val()
                };
            },
            processResults: function (response) {
                return {
                    results: $.map(response.content, function (item) {
                        return {
                            text: item.number,
                            id: item.number
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
    $("#filter-by-status").wrap('<div class="position-relative"></div>').select2({
        dropdownParent: $("#dropdownParent"),
        minimumResultsForSearch: -1,
        allowClear: true,
        placeholder: "Оберіть статус",
        ajax: {
            type: "GET",
            url: statusSelectUrl,
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

$("#filter-by-fullName").on("change", function () {
    request.fullName = $(this).val();
    getUsers(0);
});

$("#filter-by-city").on("change", function () {
    $("#filter-by-street").val(null).trigger('change');
    $("#filter-by-street").prop('disabled',  !$('#filter-by-street').prop('disabled'));
    request.city = $(this).val();
    getUsers(0);
});

$("#filter-by-street").on("change", function () {
    $("#filter-by-houseNumber").val(null).trigger('change');
    $("#filter-by-houseNumber").prop('disabled',  !$('#filter-by-houseNumber').prop('disabled'));
    request.street = $(this).val();
    getUsers(0);
});

$("#filter-by-houseNumber").on("change", function () {
    request.houseNumber = $(this).val();
    getUsers(0);
});

$("#filter-by-apartmentNumber").on("change", function () {
    request.apartmentNumber = $(this).val();
    getUsers(0);
});

$("#filter-by-personalAccount").on("change", function () {
    request.personalAccount = $(this).val();
    getUsers(0);
});

$("#filter-by-phoneNumber").on("change", function () {
    request.phoneNumber = $(this).val();
    getUsers(0);
});

$("#filter-by-status").on("change", function () {
    request.status = $(this).val();
    getUsers(0);
});

$("#clear-filters").on("click", function () {
    $("#filter-by-fullName, #filter-by-city, #filter-by-street, #filter-by-houseNumber, #filter-by-apartmentNumber, #filter-by-personalAccount, #filter-by-phoneNumber, #filter-by-status")
        .val("").trigger('change');

});

$("#upload-button").on("click", function () {
    openUploadFileModal()
});

function openUploadFileModal() {
    if($("#upload-file-modal").length === 0) {
        $("div.card").append(
            `<div class="modal fade" id="upload-file-modal" tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>
                            <input type="file" accept=".xlsx" class="form-control"  id="xlsxFile" name="xlsxFile" onchange="fileValidationChange(this)">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-label-secondary close-modal" data-bs-dismiss="modal">
                        Закрити
                        </button>
                        <button type="button" class="btn btn-primary" id="-button" onclick="sendFile()">
                            Ок
                        </button>
                    </div>
                </div>
            </div>
        </div>`
        )
    }
    $('#upload-file-modal').modal('show');
}

function fileValidationChange(input) {
    let myFile = $(input).prop('files');
    if(!validateFile(myFile[0].name)){
        $(input).addClass("is-invalid")
        $(input).parent().append($(
            '<p class="error-message invalid-feedback m-0">Файл не відповідає формату .xlsx</p>'));
        $(input).val('');
    }
}
function validateFile(value){
    let ext = value.substring(value.lastIndexOf('.') + 1).toLowerCase();
    if(value !== "" && $.inArray(ext, ['xlsx']) === -1) {
        return false;
    } else {
        return true;
    }
}

function sendFile() {
    let formData = new FormData();
    let xlsxFile = $('#xlsxFile').prop('files')[0];
    if(xlsxFile === undefined) {
        formData.append("xlsxFile",  new File([""], "filename"));
    } else {
        formData.append("xlsxFile", xlsxFile);
    }

    $.ajax({
        type: "POST",
        url: uploadFileUrl,
        data: formData,
        contentType: false,
        processData: false,
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            $('#upload-file-modal').modal('hide');
            getUsers(0);
        },
        error: function (error) {
            printErrorMessageToField(error);
        }
    });
}
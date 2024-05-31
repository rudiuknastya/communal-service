let defaultContactsPage;
$(document).ready(function () {
    getContactsPage();
});

function getContactsPage() {
    blockCardDody();
    $.ajax({
        type: "GET",
        url: "contacts/get",
        success: function (response) {
            console.log(response);
            defaultContactsPage = response;
            setFields(response);
        },
        error: function (error) {
            printErrorMessageToField(error);
        }
    });
}

function setFields(response) {
    const responseMap = new Map(Object.entries((response)));
    responseMap.forEach((value, key) => {
            $("#" + key).val(value);
    });
    const convertedFirstText = firstText.clipboard.convert(response.firstText)
    firstText.setContents(convertedFirstText, 'silent');

    const convertedSecondText = secondText.clipboard.convert(response.secondText)
    secondText.setContents(convertedSecondText, 'silent');
}

$("#save-button").on("click", function () {
    blockCardDody();
    clearAllErrorMessage();
    let formData = collectData();
    sendData(formData);
});

function collectData() {
    let formData = new FormData();
    $("#form").find('input:text').each(function (){
        formData.append($(this).attr("id"), $(this).val());
    });
    formData.append("firstText", firstText.root.innerHTML);
    formData.append("firstTextWithoutTags", firstText.getText($("#firstText")));
    formData.append("secondText", secondText.root.innerHTML);
    formData.append("secondTextWithoutTags", secondText.getText($("#secondText")));
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
            toastr.success("Дані оновлено!")
            getContactsPage();
        },
        error: function (error) {
            toastr.error(errorMessage);
            printErrorMessageToField(error);
        }
    });
}

$("#cancel-button").on("click", function () {
    setFields(defaultContactsPage);
});

const fullToolbar = [
    [
        {
            font: []
        },
        {
            size: []
        }
    ],
    ['bold', 'italic', 'underline', 'strike'],
    [
        {
            list: 'ordered'
        },
        {
            list: 'bullet'
        },
        {
            indent: '-1'
        },
        {
            indent: '+1'
        }
    ],
    ['image']
];

const firstText = new Quill('#firstText', {
    bounds: '#full-editor',
    placeholder: 'Type Something...',
    modules: {
        formula: true,
        toolbar: fullToolbar
    },
    theme: 'snow'
});

const secondText = new Quill('#secondText', {
    bounds: '#full-editor',
    placeholder: 'Type Something...',
    modules: {
        formula: true,
        toolbar: fullToolbar
    },
    theme: 'snow'
});

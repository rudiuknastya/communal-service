
const errorMessage = "Виникла помилка!";
const updateSuccessMessage = "Оновлення успішне!";
const saveSuccessMessage = "Збереження успішне!";
const fileValidation = "Зображення не відповідає формату .jpeg, .jpg, .png";
let token = $("meta[name='_csrf']").attr("content");
$(document).ready(function () {
    const currHref = window.location.pathname;
    const currentUrl = window.location.href;
    const myArray = currentUrl.split("/");
    let root = myArray[3];
    $("#nav-avatar").attr("src", "/"+root+"/uploads/"+authenticatedAvatar);
    $('.menu-item a').each(function (i, item) {
        if (currHref.includes($(item).attr('href'))) {
            $(item).parent().addClass('active');
            $(item).css("color","#7367f0");
        }
    });
});
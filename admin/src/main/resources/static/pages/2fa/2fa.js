
$("#send-button").on("click", function () {
    let token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        type: "POST",
        url: window.location.href,
        data: {
            code: $("#code").val()
        },
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function () {
            window.location = "../users";
        },
        error: function () {
            $("#error").text("Невірний код")
        }
    });
})
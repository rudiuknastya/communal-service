
$("#send-button").on("click", function () {
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
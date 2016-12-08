$(document).ready(function () {

    $(".btn_add_to_cart").click(function(){
        var prodId = $(this).data("id");
        $.ajax({
            type: 'POST',
            url: "/add",
            data: {id: prodId},
            success: function(msg) {
                $("#number_on_cart").hide().fadeIn('fast');
                $("#number_on_cart").text(msg);
            }
        });
    });

    var LogInModal = document.getElementById('log_in_modal');
    var LogInbtn = document.getElementById("log_in_btn");
    var span = document.getElementsByClassName("close")[0];

    LogInbtn.onclick = function() {
        LogInModal.style.display = "block";
    };
    span.onclick = function() {
        LogInModal.style.display = "none";
    };
    window.onclick = function(event) {
        if (event.target == LogInModal) {
            LogInModal.style.display = "none";
        }
    };

    $("#login_submit_btn").click(loginValidation);

});

function loginValidation() {
    $.get("/validate-user", {user_name: document.getElementById("input_user_name").value,
    password: document.getElementById("input_password").value}).done(function (resp) {
        if (resp === "false"){
            document.getElementById("input_password").setCustomValidity('Wrong username OR password');
        } else {
            // input is valid -- reset the error message
            document.getElementById("input_password").setCustomValidity('');
            $("#log_in_modal").hide();
        }
    })
}
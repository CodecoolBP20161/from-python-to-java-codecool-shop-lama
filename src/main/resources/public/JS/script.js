$(document).ready(function () {
    var LogInModal = document.getElementById('log_in_modal');
    var LogInbtn = document.getElementById("log_in_btn");
    var span = document.getElementsByClassName("close")[0];

    $("#log_out_btn").hide();

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
    $("#log_out_btn").click(logout);

});

function loginValidation() {
    $.get("/validate-user", {user_name: document.getElementById("input_user_name").value,
    password: document.getElementById("input_password").value}).done(function (resp) {
        if (resp === "false"){

            console.log("failed to logged in")

            document.getElementById("input_password").setCustomValidity('Wrong username OR password');
        } else {
            // input is valid -- reset the error message

            console.log("logged in")

            document.getElementById("input_password").setCustomValidity('');
            $("#log_in_modal").hide();
            $("#log_out_btn").show();
            $("#log_in_btn").hide();

        }
    })
}

function logout() {
    $.get("/logout-user", {user_name: document.getElementById("input_user_name").value,
        password: document.getElementById("input_password").value}).done(function (resp) {
        if (resp === "logged out"){
            $("#log_out_btn").hide();
            $("#log_in_btn").show();
        }
    })
}
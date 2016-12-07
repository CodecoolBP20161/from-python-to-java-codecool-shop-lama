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

    var LogInModal = document.getElementById('LogIn');
    var LogInbtn = document.getElementById("LogInBtn");
    var span = document.getElementsByClassName("close")[0];

    LogInbtn.onclick = function() {
        LogInModal.style.display = "block";
    }
    span.onclick = function() {
        LogInModal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == LogInModal) {
            LogInModal.style.display = "none";
        }
    }

});
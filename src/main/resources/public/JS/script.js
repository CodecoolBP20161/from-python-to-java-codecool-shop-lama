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


});
/**
 * Created by annakertesz on 1/19/17.
 */
$(document).ready(function () {

    $("#printlabels").click(function() {
        var jsonData = $(this).data().ordersJson;
        console.log(jsonData);
        // TODO: avoid unnecessery request
        // var url = "http://localhost:60000/api/create-label";
        // console.log(url+$.param(jsonData));
        $.ajax({
            url: "http://localhost:60000/api/create-label",
            data: {orders: jsonData},
            success: function(){
                console.log(this.url);
                window.open(this.url);

            }
        });
    });
    $("#calculate").click(function() {
        $("#calculation").slideDown();
    })
});
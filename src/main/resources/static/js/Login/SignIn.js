$(function(){

    $("#signInSubmit").click(function (e) {

        if (isEmpty($("#username").val())) {
            message("Username is required", 'danger');
            e.preventDefault();
        }

        if (isEmpty($("#password").val())) {
            message("Password is required", 'danger');
            e.preventDefault();
        }

    });


});
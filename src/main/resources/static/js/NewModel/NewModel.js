$( document ).ready(function() {


    if($("#title").val() != "" && $("#description").val() != ""){
        $("#uploadBox").hide();
    }else{
        $("#currentImageBox").hide();
    }

    $( "#changeImage" ).click(function() {
        $("#currentImageBox").hide();
        $("#uploadBox").show();
    });

    $("#newModel").addClass("ativo");
});



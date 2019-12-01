$( document ).ready(function() {

    $( ".delete" ).click(function() {
        setInfo($(this)[0].dataset);
        UIkit.modal("#modal-delete").show();
    });

    $("#myModels").addClass("ativo");

});


function setInfo(model){
    $(".modal-model-title").text(model.title);
    $(".modal-model-id").val(model.id);
}
$( document ).ready(function() {

    $( ".card" ).click(function() {

        setInfo($(this)[0].dataset);
        UIkit.modal("#modal-full").show();

    });

    $( ".card" ).keydown(function(event) {

        if(event.which === 13 || event.which === 32) {
            setInfo($(this)[0].dataset);
            UIkit.modal("#modal-full").show();
        }

    });



    $("#home").addClass("ativo");

});





function setInfo(card){
    $(".modal-model-title").text(card.title);
    $(".modal-model-description").text(card.description);
    $(".modal-model-date").text(realDate(card.date));
    $(".modal-model-user").text(card.user);
    $(".modal-model-image").css("background-image" , 'url(' + card.url + ')');
}






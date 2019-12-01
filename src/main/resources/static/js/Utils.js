function message(msg, type) {
    UIkit.notification({
        message: msg,
        status: type,
        pos: 'bottom-right',
        timeout: 4000
    });
}



function limparCampos(){
    $(".uk-input").val("");
}


function isEmpty(value) {
    return typeof value == 'string' && !value.trim() || typeof value == 'undefined' || value === null;
}


function realDate(d){
    var data = new Date(d),
        dia  = data.getDate().toString(),
        diaF = (dia.length == 1) ? '0'+dia : dia,
        mes  = (data.getMonth()+1).toString(), //+1 pois no getMonth Janeiro começa com zero.
        mesF = (mes.length == 1) ? '0'+mes : mes,
        anoF = data.getFullYear();
    return diaF+"/"+mesF+"/"+anoF;
}


$("#altocontraste").on("keydown", function (event) {
    var tecla = event.keyCode;
    var ativado = $("#altocontraste").data("acessibilidade");


    if (tecla == 13) {
        window.toggleContrast();

        if (ativado) {
            $("#altocontraste").text("HIGH CONTRAST OFF")
            $("#altocontraste").data("acessibilidade", false);
        } else {
            $("#altocontraste").text("HIGH CONTRAST ON")
            $("#altocontraste").data("acessibilidade", true);
        }

        event.preventDefault();
    } else if (tecla == 9) {

    }
});


$("#altocontraste").on("click", function (event) {
    var tecla = event.keyCode;
    var ativado = $("#altocontraste").data("acessibilidade");

    window.toggleContrast();

        if (ativado) {
            $("#altocontraste").text("HIGH CONTRAST OFF")
            $("#altocontraste").data("acessibilidade", false);
        } else {
            $("#altocontraste").text("HIGH CONTRAST ON")
            $("#altocontraste").data("acessibilidade", true);
        }

    event.preventDefault();

});






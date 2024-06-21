COLDIGO = new Object();

$(document).ready(function () {

	/* indicação estática do caminho PATH*/
	COLDIGO.PATH = "/";

	$("header").load("/pages/admin/general/header.html");
	$("footer").load("/pages/admin/general/footer.html");

	COLDIGO.carregaPagina = function (pagename) {
		if ($(".ui-dialog"))
			$(".ui-dialog").remove();

		$("section").empty();
		$("section").load("/pages/admin/" + pagename + "/index.html", function (response, status, info) {	//TODO alterado
			if (status == "error") {
				var msg = "Houve um erro ao carregar a página: " + info.status + " - " + info.statusText;
				$("section").html(msg);
			}
		});
	}

	COLDIGO.formatarDinheiro = function (valor) {
		return valor.toFixed(2).replace('.', ',').replace(/(\d)(?=(\d{3})+\,)/g, "$1.");
	}

	COLDIGO.exibirAviso = function (aviso) {
		var modal = {
			title: "Mensagem",
			height: 250,
			width: 400,
			modal: true,
			buttons: {
				"OK": function () {
					$(this).dialog("close");
				}
			}
		};
		$("#modalAviso").html(aviso);
		$("#modalAviso").dialog(modal);

	}
});
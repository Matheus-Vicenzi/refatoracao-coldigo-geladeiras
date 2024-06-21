function validaFaleConosco() {
	var nome = document.frmfaleconosco.txtnome.value;
	var expRegNome = new RegExp("^[A-zÀ-ü]{3,}([ ]{1}[A-zÀ-ü]{2,})+$");
	if (!expRegNome.test(nome)) {
		alert("Preencha o campo nome corretamente.");
		document.frmfaleconosco.txtnome.focus();
		return false;
	}
	var fone = document.frmfaleconosco.txtfome.value;
	var expRegFone = new RegExp("^[(]{1}[1-9]{2}[)]{1}[0-9]{4,5}[-]{1}[0-9]{4}$");
	if (!expRegFone.test(fone)) {
		alert("Preencha o campo telefone corretamente.");
		document.frmfaleconosco.txtfome.focus();
		return false;
	}



	if (document.frmfaleconosco.txtemail.value == "") {
		alert("Preencha o campo email");
		document.frmfaleconosco.txtemail.focus();
		return false;
	} else if (document.frmfaleconosco.selmotivo.value == "") {
		alert("Selecione o produto que deseja obter Informação");
		document.frmfaleconosco.selmotivo.focus();
		return false;
	} else if (document.frmfaleconosco.selmotivo.value == "PR" && document.frmfaleconosco.selproduto.value == "") {
		alert("Selecione o produto que deseja obter Informação");
		document.frmfaleconosco.selproduto.focus();
		return false;
	} else if (document.frmfaleconosco.txacomentario.value == "") {
		alert("Preencha o campo comentario");
		document.frmfaleconosco.txacomentario.focus();
		return false;
	}
	return true;
}

function verificaMotivo(motivo) {
	var elemento = document.getElementById("opcaoProduto");

	if (motivo == "PR") {
		var select = document.createElement("select");
		select.setAttribute("name", "selproduto");

		var option = document.createElement("option");
		option.setAttribute("value", "");
		var texto = document.createTextNode("Escolha")
		option.appendChild(texto);

		select.appendChild(option);

		var option = document.createElement("option");
		option.setAttribute("value", "FR");

		var texto = document.createTextNode("Freezer");
		option.appendChild(texto);

		select.appendChild(option);

		var option = document.createElement("option");
		option.setAttribute("value", "GE");

		var texto = document.createTextNode("Geladeira");
		option.appendChild(texto);

		select.appendChild(option);

		elemento.appendChild(select);
	} else {
		if (elemento.firstChild) {
			elemento.removeChild(elemento.firstChild);
		}

	}
}

$(document).ready(function () {
	$("header").load("/pages/site/general/cabecalho.html");
	$("nav").load("/pages/site/general/menu.html");
	$("footer").load("/pages/site/general/rodape.html");
});



COLDIGO.marca = new Object();

$(document).ready(() => {

	COLDIGO.marca.cadastrar = () => {

		var marca = {};
		marca.nome = document.frmAddMarca.nome.value;
		marca.data = document.frmAddMarca.data.value;
		console.log("AAAAAAAAAA")

		if ((marca.nome == "") || (marca.data == "")) {
			COLDIGO.exibirAviso("Preencha todos os campos");
		} else {

			$.ajax({
				type: "POST",
				url: COLDIGO.PATH + "marca/inserir",
				data: JSON.stringify(marca),
				contentType: "application/json",
				success: (msg) => {
					COLDIGO.exibirAviso(msg);
					$("#addMarca").trigger("reset");
				},
				error: () => {
					COLDIGO.exibirAviso(msg);
				}
			});
		}

	}

	COLDIGO.marca.buscar = () => {
		var valorBusca = $("#campoBuscaMarca").val();

		$.ajax({
			type: "GET",
			url: COLDIGO.PATH + "marca/buscarMarca",
			data: "valorBusca=" + valorBusca,
			success: (dados) => {
				dados = JSON.parse(dados);
				$("#listaMarcas").html(COLDIGO.marca.exibir(dados));
			},
			error: (info) => {
				COLDIGO.exibirAviso("Erro ao consultar os contatos: " + info.status + " - " + info.statusText);
			}
		});
	};

	COLDIGO.marca.exibir = (listaDeMarcas) => {

		var tabela = "<table>" +
			"<tr>" +
			"<th>Nome</th>" +
			"<th>Data</th>" +
			"<th class='acoes'>Ações</th>" +
			"<th class='acoes'>Status</th>" +
			"</tr>";

		if (listaDeMarcas != undefined && listaDeMarcas.length > 0) {

			for (var i = 0; i < listaDeMarcas.length; i++) {
				var checkbox = "<input type='checkbox' onclick=\"COLDIGO.marca.inativar('" + listaDeMarcas[i].id + "')\"/>";
				if (listaDeMarcas[i].status == 1) {
					checkbox = "<input type='checkbox' checked onclick=\"COLDIGO.marca.inativar('" + listaDeMarcas[i].id + "')\"/>";
				}

				tabela += "<tr>" +
					"<td>" + listaDeMarcas[i].nome + "</td>" +
					"<td>" + listaDeMarcas[i].data + "</td>" +
					"<td>" +
					"<a onclick=\"COLDIGO.marca.exibirEdicao('" + listaDeMarcas[i].id + "')\"><img src='../../imgs/edit.png' alt='Editar registro'></a> " +
					"<a onclick=\"COLDIGO.marca.excluir('" + listaDeMarcas[i].id + "')\"><img src='../../imgs/delete.png' alt='Excluir registro'></a>" +
					"</td>" +
					"<td>" +
					"<label class='switch'>" +
					checkbox +
					"<span class='slider round'></span>" +
					"</label>" +
					"</td>" +
					"</tr>";
			}
		} else if (listaDeMarcas == "") {
			tabela += "<tr><td colspan='6'> Nenhum registro encontrado</td></tr>";
		}
		tabela += "</table>";

		return tabela;
	};

	COLDIGO.marca.buscar();

	COLDIGO.marca.excluir = (id) => {
		if (confirm("Você tem certeza que deseja deletar?")) {
			$.ajax({
				type: "DELETE",
				url: COLDIGO.PATH + "marca/excluir/" + id,
				success: function (msg) {
					COLDIGO.exibirAviso(msg);
					COLDIGO.marca.buscar();
				},
				error: function () {
					COLDIGO.exibirAviso(msg);
				}
			});
		}
	};

	COLDIGO.marca.exibirEdicao = (id) => {
		$.ajax({
			type: "GET",
			url: COLDIGO.PATH + "marca/buscarPorId",
			data: "id=" + id,
			success: (marca) => {

				console.log(marca);
				console.log(document.frmEditaMarca)
				document.frmEditaMarca.idMarca.value = marca.id;
				document.frmEditaMarca.nome.value = marca.nome;
				document.frmEditaMarca.data.value = marca.data;

				var modalEditaMarca = {
					title: "Editar Marca",
					height: 400,
					width: 550,
					modal: true,
					buttons: {
						"Salvar": () => {
							COLDIGO.marca.editar();
						},
						"Cancelar": () => {
							$(this).dialog("close");
						}
					},
					close: () => {
						//empty
					}
				};

				$("#modalEditaMarca").dialog(modalEditaMarca);

			},
			error: (info) => {
				COLDIGO.exibirAviso("Erro ao buscar marca para edição: " + info.status + " - " + info.statusText);
			}
		});
	};

	COLDIGO.marca.editar = () => {
		var marca = {};
		marca.id = document.frmEditaMarca.idMarca.value;
		marca.nome = document.frmEditaMarca.nome.value;
		marca.data = document.frmEditaMarca.data.value;
		console.log(marca);

		$.ajax({
			type: "PUT",
			url: COLDIGO.PATH + "marca/alterar",
			data: JSON.stringify(marca),
			contentType: "application/json",
			success: (msg) => {
				COLDIGO.exibirAviso(msg);
				COLDIGO.marca.buscar();
				$("#modalEditaMarca").dialog("close");
			},
			error: () => {
				COLDIGO.exibirAviso(msg);
			}
		});
	};

	COLDIGO.marca.inativar = (id) => {
		$.ajax({

			type: "PUT",
			url: COLDIGO.PATH + "marca/inativar/" + id,
			success: (msg) => {
				COLDIGO.exibirAviso(msg);
				COLDIGO.marca.buscar();
			},
			error: () => {
				COLDIGO.exibirAviso(msg);
			}
		});

	};
});
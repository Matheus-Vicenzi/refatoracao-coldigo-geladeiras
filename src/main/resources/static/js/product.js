COLDIGO.produto = new Object();

$(document).ready(function () {

	COLDIGO.produto.carregarMarcas = function (id) {

		if (id != undefined) {
			select = "#selMarcaEdicao";
		} else {
			select = "#selMarca";
		}

		$.ajax({
			type: "GET",
			url: COLDIGO.PATH + "marca/buscar",
			success: function (marcas) {
				marcas = JSON.parse(marcas);
				if (marcas != "") {

					$(select).html("");
					var option = document.createElement("option");
					option.setAttribute("value", "");
					option.innerHTML = ("Escolha");
					$(select).append(option);
					for (var i = 0; i < marcas.length; i++) {

						var option = document.createElement("option");
						option.setAttribute("value", marcas[i].id);

						if ((id != undefined) && (id == marcas[i].id)) {
							option.setAttribute("selected", "selected");
						}
						option.innerHTML = (marcas[i].nome);
						$(select).append(option);
					}
				} else {

					$(select).html("");
					var option = document.createElement("option");
					option.setAttribute("value", "");
					option.innerHTML = ("Cadastre uma marca primeiro");
					$(select).append(option);
					$(select).addClass("aviso");
				}

			},

			error: function (info) {

				COLDIGO.exibirAviso("Erro ao buscar as marcas: " + info.status + "-" + info.statusText);

				$(select).html("");
				var option = document.createElement("option");
				option.setAttribute("value", "");
				option.innerHTML = ("Erro ao carregar marcas");
				$(select).append(option);
				$(select).addClass("aviso");
			}

		});

	}

	COLDIGO.produto.carregarMarcas();

	COLDIGO.produto.cadastrar = function () {

		/*criando um objeto produto, recebendo nele todos os valores do formulário*/

		var produto = new Object();
		produto.categoria = document.frmAddProduto.categoria.value;
		produto.marcaId = parseInt(document.frmAddProduto.marcaId.value);
		produto.modelo = document.frmAddProduto.modelo.value;
		produto.capacidade = parseInt(document.frmAddProduto.capacidade.value);
		produto.valor = parseFloat(document.frmAddProduto.valor.value);

		/*verificar se ficou campo vazio*/
		if ((produto.categoria == "") || (produto.marcaId == "") || (produto.modelo == "") || (produto.capacidade == "") || (produto.valor == "")) {
			COLDIGO.exibirAviso("Preencha todos os campos");
		} else {

			/*enviar esses dados ao servidor.*/

			$.ajax({
				type: "POST",
				/* indicação estática do caminho PATH*/
				url: COLDIGO.PATH + "produto/inserir",
				contentType: "application/json",

				/*transforma o objeto produto numa String em formato JSON*/
				data: JSON.stringify(produto),
				/*o envio dos dados para o servidor é feito no data*/
				success: function (msg) {/*exibe o modal e reseta o form*/
					COLDIGO.exibirAviso(msg);
					$("#addProduto").trigger("reset");
					COLDIGO.produto.buscar();
				},
				error: function () {
					COLDIGO.exibirAviso(msg);
				}
			});
		}

	}

	COLDIGO.produto.buscar = function () {

		var valorBusca = $("#campoBuscaProduto").val();

		$.ajax({

			type: "GET",
			url: COLDIGO.PATH + "produto/buscar",
			data: "valorBusca=" + valorBusca,
			success: function (dados) {
				dados = JSON.parse(dados);
				$("#listaProdutos").html(COLDIGO.produto.exibir(dados));
			},
			error: function (info) {
				COLDIGO.exibirAviso("Erro ao consultar os contatos: " + info.status + " - " + info.statusText);
			}

		});

	};
	/*!!!!!!!!!!!*/
	/*recebendo como parametro listaDeProduto que é a resposta do servidor*/
	COLDIGO.produto.exibir = function (listaDeProdutos) {
		var tabela = ""

		if (listaDeProdutos != undefined && listaDeProdutos.length > 0) {

			/*vai percorrer toda a lista*/
			for (var i = 0; i < listaDeProdutos.length; i++) {
				tabela += "<tr>" +

					"<td>" + listaDeProdutos[i].categoria + "</td>" +
					"<td>" + listaDeProdutos[i].marcaNome + "</td>" +
					"<td>" + listaDeProdutos[i].modelo + "</td>" +
					"<td>" + listaDeProdutos[i].capacidade + "</td>" +
					"<td> R$ " + COLDIGO.formatarDinheiro(listaDeProdutos[i].valor) + "</td>" +
					"<td>" +
					"<a onclick=\"COLDIGO.produto.exibirEdicao('" + listaDeProdutos[i].id + "')\"><img src='../../imgs/edit.png' alt='Editar registro' class='icone'></a> " +
					/*aqui onde é acionado o evento deletar que recebe como parametro o id que foi selecionado*/
					"<a onclick=\"COLDIGO.produto.excluir('" + listaDeProdutos[i].id + "')\"><img src='../../imgs/delete.png' alt='Excluir registro' class='icone'></a>" +
					"</td>" +
					"</tr>"
			}



		} else if (listaDeProdutos == "") {
			tabela += "<tr><td colspan='6'> Nenhum registro encontrado</td></tr>";
		}
		tabela += "</table>";

		return tabela;

	};

	COLDIGO.produto.buscar();

	/*função que vai fazer a solicitação para deletar um registro*/
	COLDIGO.produto.excluir = function (id) {
		// Show confirmation modal dialog
		if (confirm("Você tem certeza que deseja deletar?")) {
			$.ajax({
				type: "DELETE",
				/* the id is already passed in the URL */
				url: COLDIGO.PATH + "produto/excluir/" + id,
				success: function (msg) {
					COLDIGO.exibirAviso(msg);
					/* refetch the list to update it */
					COLDIGO.produto.buscar();
				},
				error: function () {
					COLDIGO.exibirAviso(msg);
				}
			});
		}
	};

	COLDIGO.produto.exibirEdicao = function (id) {
		$.ajax({
			type: "GET",
			url: COLDIGO.PATH + `produto/buscarPorId/${id}`,
			success: function (produto) {

				document.frmEditaProduto.idProduto.value = produto.id;
				document.frmEditaProduto.modelo.value = produto.modelo;
				document.frmEditaProduto.capacidade.value = produto.capacidade;
				document.frmEditaProduto.valor.value = produto.valor;

				var selCategoria = document.getElementById('selCategoriaEdicao');
				for (var i = 0; i < selCategoria.length; i++) {
					if (selCategoria.options[i].value == produto.categoria) {
						selCategoria.options[i].setAttribute("selected", "selected");
					} else {
						selCategoria.options[i].removeAttribute("selected");
					}
				}

				COLDIGO.produto.carregarMarcas(produto.marcaId);

				var modalEditaProduto = {
					title: "Editar Produto",
					height: 400,
					width: 550,
					modal: true,
					buttons: {
						"Salvar": function () {
							COLDIGO.produto.editar();
						},
						"Cancelar": function () {
							$(this).dialog("close");
						}
					},
					close: function () {
						//empty
					}
				};

				$("#modalEditaProduto").dialog(modalEditaProduto);

			},
			error: function (info) {
				COLDIGO.exibirAviso("Erro ao buscar produto para edição: " + info.status + " - " + info.statusText);
			}
		});
	};

	COLDIGO.produto.editar = function () {
		var produto = new Object();
		produto.id = document.frmEditaProduto.idProduto.value;
		produto.categoria = document.frmEditaProduto.categoria.value;
		produto.marcaId = document.frmEditaProduto.marcaId.value;
		produto.modelo = document.frmEditaProduto.modelo.value;
		produto.capacidade = document.frmEditaProduto.capacidade.value;
		produto.valor = document.frmEditaProduto.valor.value;

		$.ajax({
			type: "PUT",
			url: COLDIGO.PATH + "produto/alterar",
			data: JSON.stringify(produto),
			success: function (msg) {
				COLDIGO.exibirAviso(msg);
				COLDIGO.produto.buscar();
				$("#modalEditaProduto").dialog("close");
			},
			error: function () {
				COLDIGO.exibirAviso(msg);
			}
		});
	};

});
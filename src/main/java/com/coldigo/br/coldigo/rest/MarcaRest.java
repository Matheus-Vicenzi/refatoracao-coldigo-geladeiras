package com.coldigo.br.coldigo.rest;

import com.coldigo.br.coldigo.db.Conexao;
import com.coldigo.br.coldigo.jdbc.JDBCMarcaDAO;
import com.coldigo.br.coldigo.modelo.Marca;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/marca")
public class MarcaRest extends UtilRest {

	@GetMapping("/buscar")
	public ResponseEntity<?> buscar() {
		try {
			List<Marca> listaMarcas = new ArrayList<Marca>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			listaMarcas = jdbcMarca.buscar();
			conec.fecharConexao();

			System.err.println("Lista de marcas: " + listaMarcas);

			return this.buildResponse(listaMarcas);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@PostMapping(value = "/inserir", consumes = "application/*")
	public ResponseEntity<?> inserir(@RequestBody String json) {
		try {
			Marca marca = new Gson().fromJson(json, Marca.class);

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			boolean retorno = jdbcMarca.inserir(marca);
			String msg;

			if (retorno) {
				msg = "Marca cadastrada com sucesso";
			} else {
				msg = "Erro ao cadastrar marca";
			}

			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@GetMapping("/buscarMarca")
	public ResponseEntity<?> buscarPorNome(@RequestParam(name = "valorBusca") String nome) {
		try {
			List<Marca> listaMarcas = new ArrayList<Marca>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);

			listaMarcas = jdbcMarca.buscarPorMarca(nome);
			conec.fecharConexao();

			return this.buildResponse(listaMarcas);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluir(@PathVariable("id") int id) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);

			boolean retorno = jdbcMarca.deletar(id);

			String msg;
			if (retorno) {
				msg = "Marca excluída com sucesso";
			} else {
				msg = "Erro ao excluir marca";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);
		} catch (SQLException e) {
			System.out.println("Erro ao excluir marca, verifique se a marca está vinculada a algum produto\n\n\n\n");
			e.printStackTrace();
			return this
					.buildErrorResponse("Erro ao excluir marca, verifique se a marca está vinculada a algum produto");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@GetMapping("/buscarPorId")
	public ResponseEntity<?> buscarPorNome(@RequestParam(name = "id") int id) {
		try {
			Marca marca = new Marca();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);

			marca = jdbcMarca.buscarPorId(id);

			conec.fecharConexao();

			return this.buildResponse(marca);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@PutMapping(value = "/alterar", consumes = "application/*")
	public ResponseEntity<?> alterar(@RequestBody String json) {
		try {
			Marca marca = new Gson().fromJson(json, Marca.class);

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);

			boolean retorno = jdbcMarca.alterar(marca);
			String msg = "";

			if (retorno) {
				msg = "Marca alterada com sucesso";
			} else {
				msg = "Erro ao alterar marca";
			}

			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@PutMapping("/inativar/{id}")
	public ResponseEntity<?> inativar(@PathVariable("id") int id) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);

			boolean retorno = jdbcMarca.inativar(id);

			String msg;
			if (retorno) {
				msg = "Status alterado com sucesso";
			} else {
				msg = "Erro ao alterar status";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}
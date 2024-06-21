package com.coldigo.br.coldigo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.coldigo.br.coldigo.jdbcinterface.ProdutoDAO;
import com.coldigo.br.coldigo.modelo.Produto;

public class JDBCProdutoDAO implements ProdutoDAO {

	private Connection conexao;

	public JDBCProdutoDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public List<Produto> buscar() {

		String comando = "SELECT * FROM produtos";
		List<Produto> listProdutos = new ArrayList<Produto>();
		Produto produto = null;
		try {

			Statement stmt = conexao.createStatement();

			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {
				produto = new Produto();
				int id = rs.getInt("id");
				String categoria = rs.getString("categoria");
				String modelo = rs.getString("modelo");
				int capacidade = rs.getInt("capacidade");
				float valor = rs.getFloat("valor");
				int marcaId = rs.getInt("marcas_id");

				produto.setId(id);
				produto.setCategoria(categoria);
				produto.setModelo(modelo);
				produto.setCapacidade(capacidade);
				produto.setValor(valor);
				produto.setMarcaId(marcaId);

				listProdutos.add(produto);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listProdutos;
	}

	public boolean inserir(Produto produto) {
		String comando = "INSERT INTO produtos" + "(id, categoria, modelo, capacidade, valor, marcas_id)"
				+ "VALUES(?,?,?,?,?,?)";

		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, produto.getId());
			p.setString(2, produto.getCategoria());
			p.setString(3, produto.getModelo());
			p.setInt(4, produto.getCapacidade());
			p.setFloat(5, produto.getValor());
			p.setInt(6, produto.getMarcaId());

			p.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Produto> buscarPorNome(String nome) {

		String comando = "SELECT produtos.*, marcas.nome as marca FROM produtos "
				+ "INNER JOIN marcas on produtos.marcas_id = marcas.id ";

		if (!nome.equals("")) {
			comando += "WHERE modelo LIKE '%" + nome + "%' ";
		}
		comando += "ORDER BY categoria ASC, marcas.nome ASC, modelo ASC";

		List<Produto> listaProdutos = new ArrayList<Produto>();
		Produto produto = null;

		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {

				int id = rs.getInt("id");
				String categoria = rs.getString("categoria");
				String modelo = rs.getString("modelo");
				int capacidade = rs.getInt("capacidade");
				float valor = rs.getFloat("valor");
				int marcaId = rs.getInt("marcas_id");

				if (categoria.equals("1")) {
					categoria = "Geladeira";
				} else if (categoria.equals("2")) {
					categoria = "Freezer";
				}

				produto = new Produto();
				produto.setId(id);
				produto.setCapacidade(capacidade);
				produto.setCategoria(categoria);
				produto.setMarcaId(marcaId);
				produto.setModelo(modelo);
				produto.setValor(valor);

				listaProdutos.add(produto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaProdutos;
	}

	public boolean deletar(int id) {
		String comando = "DELETE FROM produtos WHERE id = ?";

		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Produto buscarPorId(int id) {
		String comando = "SELECT * FROM produtos WHERE produtos.id = ?";
		Produto produto = null;
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				produto = new Produto();

				String categoria = rs.getString("categoria");
				String modelo = rs.getString("modelo");
				int capacidade = rs.getInt("capacidade");
				float valor = rs.getFloat("valor");
				int marcaId = rs.getInt("marcas_id");

				produto.setId(id);
				produto.setCategoria(categoria);
				produto.setMarcaId(marcaId);
				produto.setModelo(modelo);
				produto.setCapacidade(capacidade);
				produto.setValor(valor);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produto;
	}

	public boolean alterar(Produto produto) {
		String comando = "UPDATE produtos " + "SET categoria=?, modelo=?, capacidade=?, valor=?, marcas_id=?"
				+ " WHERE id=?";
		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, produto.getCategoria());
			p.setString(2, produto.getModelo());
			p.setInt(3, produto.getCapacidade());
			p.setFloat(4, produto.getValor());
			p.setInt(5, produto.getMarcaId());
			p.setInt(6, produto.getId());
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
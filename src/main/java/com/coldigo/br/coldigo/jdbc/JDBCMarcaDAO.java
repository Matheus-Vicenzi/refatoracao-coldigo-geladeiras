package com.coldigo.br.coldigo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import com.coldigo.br.coldigo.jdbcinterface.MarcaDAO;
import com.coldigo.br.coldigo.modelo.Marca;

import java.util.ArrayList;

public class JDBCMarcaDAO implements MarcaDAO {

	private Connection conexao;

	public JDBCMarcaDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public List<Marca> buscar() {

		String comando = "SELECT * FROM marcas";
		List<Marca> listMarcas = new ArrayList<Marca>();
		Marca marca = null;

		try {

			Statement stmt = conexao.createStatement();

			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {
				marca = new Marca();
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				marca.setId(id);
				marca.setNome(nome);
				listMarcas.add(marca);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listMarcas;
	}

	public boolean inserir(Marca marca) {
		int status = 1; // 1 = ativo, 0 = inativo

		String comando = "INSERT INTO marcas (nome, status) VALUES(?,?)";

		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setString(1, marca.getNome());
			p.setInt(2, status);
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Marca> buscarPorMarca(String nome) {

		String comando = "SELECT * FROM marcas ";

		if (!nome.equals("")) {
			comando += "WHERE nome LIKE '%" + nome + "%'";
		}

		List<Marca> listaMarcas = new ArrayList<Marca>();

		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {
				Marca marca = new Marca();

				int id = rs.getInt("id");
				String nomeMarca = rs.getString("nome");
				int status = rs.getInt("status");

				marca.setId(id);
				marca.setNome(nomeMarca);
				marca.setStatus(status);

				listaMarcas.add(marca);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return listaMarcas;
	}

	public boolean deletar(int id) throws SQLException {
		String comando = "DELETE FROM marcas WHERE id = ?";

		PreparedStatement p;

		p = this.conexao.prepareStatement(comando);
		p.setInt(1, id);
		p.execute();

		return true;
	}

	public Marca buscarPorId(int id) {
		String comando = "SELECT * FROM marcas WHERE marcas.id = ?";
		Marca marca = new Marca();
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {

				String nome = rs.getString("nome");

				marca.setId(id);
				marca.setNome(nome);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return marca;
	}

	public boolean alterar(Marca marca) {
		String comando = "UPDATE marcas " + "SET nome=?" + " WHERE id=?";
		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, marca.getNome());
			p.setInt(2, marca.getId());
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean inativar(int id) {
		String comando = "UPDATE marcas "
				+ "SET status = CASE WHEN status = 0 THEN 1 "
				+ "WHEN status = 1 THEN 0 END "
				+ "WHERE id = ?;";

		PreparedStatement p;
		try {

			p = conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
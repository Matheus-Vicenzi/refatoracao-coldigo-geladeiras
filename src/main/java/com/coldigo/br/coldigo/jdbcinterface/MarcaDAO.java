package com.coldigo.br.coldigo.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import com.coldigo.br.coldigo.modelo.Marca;

public interface MarcaDAO {

	public List<Marca> buscar();

	public boolean inserir(Marca marca);

	public List<Marca> buscarPorMarca(String nome);

	public boolean deletar(int id) throws SQLException;

	public Marca buscarPorId(int id);

	public boolean alterar(Marca marca);

	public boolean inativar(int id);

}
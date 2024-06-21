package com.coldigo.br.coldigo.jdbcinterface;

import java.util.List;

import com.coldigo.br.coldigo.modelo.Produto;

public interface ProdutoDAO {

	public boolean inserir(Produto produto);

	public List<Produto> buscarPorNome(String nome);

	public boolean deletar(int id);

	public Produto buscarPorId(int id);

	public boolean alterar(Produto produto);

}
package com.coldigo.br.coldigo.modelo;

import java.io.Serializable;

public class Marca implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String nome;
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString() {
		return "Marca [id=" + id + ", nome=" + nome + ", status=" + status + "]";
	}
}

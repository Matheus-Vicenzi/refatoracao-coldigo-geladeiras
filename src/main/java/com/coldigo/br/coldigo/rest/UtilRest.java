package com.coldigo.br.coldigo.rest;

import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

public class UtilRest { // Alterar essa classe para diferentes tipos de status code

	public ResponseEntity<?> buildResponse(Object result) {

		try {
			String valorResposta = new Gson().toJson(result);
			return ResponseEntity.ok().body(valorResposta);
		} catch (Exception ex) {
			ex.printStackTrace();
			return this.buildErrorResponse(ex.getMessage());
		}

	}

	public ResponseEntity<?> buildErrorResponse(String msgErro) {
		return ResponseEntity.status(500).body(msgErro);
	}

}
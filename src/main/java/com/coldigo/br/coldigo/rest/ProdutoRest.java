package com.coldigo.br.coldigo.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coldigo.br.coldigo.db.Conexao;
import com.coldigo.br.coldigo.jdbc.JDBCProdutoDAO;
import com.coldigo.br.coldigo.modelo.Produto;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoRest extends UtilRest {

    @PostMapping(path = "/inserir", consumes = "application/*")
    public ResponseEntity<?> inserir(@RequestBody String json) {
        try {
            Produto produto = new Gson().fromJson(json, Produto.class);
            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();

            JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);

            boolean retorno = jdbcProduto.inserir(produto);
            String msg;

            if (retorno) {
                msg = "Produto cadastrado com sucesso";
            } else {
                msg = "Erro ao cadastrar produto";
            }

            conec.fecharConexao();
            return this.buildResponse(msg);

        } catch (Exception e) {
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }

    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscar() {
        try {
            List<Produto> listaProduto = new ArrayList<Produto>();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
            listaProduto = jdbcProduto.buscar();
            conec.fecharConexao();

            return this.buildResponse(listaProduto);

        } catch (Exception e) {
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") int id) {
        try {
            Produto produto = new Produto();

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
            produto = jdbcProduto.buscarPorId(id);
            conec.fecharConexao();

            return this.buildResponse(produto);

        } catch (Exception e) {
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterarProduto(@RequestBody String json) {
        try {
            Produto produto = new Gson().fromJson(json, Produto.class);

            Conexao conec = new Conexao();
            Connection conexao = conec.abrirConexao();
            JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);

            boolean retorno = jdbcProduto.alterar(produto);
            String msg;

            if (retorno) {
                msg = "Produto alterado com sucesso";
            } else {
                msg = "Erro ao alterar produto";
            }

            conec.fecharConexao();

            return this.buildResponse(msg);

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
            JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);

            boolean retorno = jdbcProduto.deletar(id);

            String msg;
            if (retorno) {
                msg = "Produto excluído com sucesso";
            } else {
                msg = "Erro ao excluir produto";
            }

            conec.fecharConexao();

            return this.buildResponse(msg);

        } catch (Exception e) {
            e.printStackTrace();
            return this.buildErrorResponse(e.getMessage());
        }
    }

}

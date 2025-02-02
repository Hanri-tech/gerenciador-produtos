package br.com.app.gerenciadorproduto.services.exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException() {
        super("Produto nao encontrado.");
    }
}

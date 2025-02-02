package br.com.app.gerenciadorproduto.services.exceptions;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException() {super("Categoria nao encontrada");}
}

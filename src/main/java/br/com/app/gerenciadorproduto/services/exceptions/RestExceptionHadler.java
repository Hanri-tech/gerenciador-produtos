package br.com.app.gerenciadorproduto.services.exceptions;

import br.com.app.gerenciadorproduto.dtos.RetornoErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHadler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    private ResponseEntity<?> categoriaNaoEncontradaHandler(CategoriaNaoEncontradaException categoriaNaoEncontradaException){
        RetornoErrorMessageDTO retornoErrorMessageDTO = new RetornoErrorMessageDTO(
                categoriaNaoEncontradaException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(retornoErrorMessageDTO);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    private ResponseEntity<?> categoriaNaoEncontradaHandler(ProdutoNaoEncontradoException produtoNaoEncontradoException){
        RetornoErrorMessageDTO retornoErrorMessageDTO = new RetornoErrorMessageDTO(
                produtoNaoEncontradoException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(retornoErrorMessageDTO);
    }
}

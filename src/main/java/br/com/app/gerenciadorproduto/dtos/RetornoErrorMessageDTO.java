package br.com.app.gerenciadorproduto.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RetornoErrorMessageDTO {
    private String erro;
    private HttpStatus status;
}

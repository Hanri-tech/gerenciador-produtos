package br.com.app.gerenciadorproduto.dtos;

import java.math.BigDecimal;

public record ProdutoDTO(
         String nome,
         BigDecimal preco,
         String descricao,
         Integer quantidade,
         int categoriaId
) {}

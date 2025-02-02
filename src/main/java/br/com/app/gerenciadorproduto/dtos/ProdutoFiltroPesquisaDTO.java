package br.com.app.gerenciadorproduto.dtos;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public record ProdutoFiltroPesquisaDTO(String nome,
                                       String descricao,
                                       Integer qtdMin,
                                       Integer qtdMax,
                                       BigDecimal precoMin,
                                       BigDecimal precoMax,
                                       Integer categoria) {
}

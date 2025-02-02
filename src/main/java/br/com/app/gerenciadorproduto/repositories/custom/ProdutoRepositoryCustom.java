package br.com.app.gerenciadorproduto.repositories.custom;

import br.com.app.gerenciadorproduto.dtos.ProdutoDTO;
import br.com.app.gerenciadorproduto.dtos.ProdutoFiltroPesquisaDTO;
import br.com.app.gerenciadorproduto.entities.Produto;

import java.util.List;

public interface ProdutoRepositoryCustom {
    List<Produto> findByFilterPesquisa(ProdutoFiltroPesquisaDTO produtoFiltroPesquisaDTO);
}

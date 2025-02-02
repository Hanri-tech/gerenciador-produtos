package br.com.app.gerenciadorproduto.repositories.custom;

import br.com.app.gerenciadorproduto.dtos.ProdutoDTO;
import br.com.app.gerenciadorproduto.dtos.ProdutoFiltroPesquisaDTO;
import br.com.app.gerenciadorproduto.entities.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProdutoRepositoryCustomImpl implements ProdutoRepositoryCustom {
    @PersistenceContext
    private EntityManager gerenciadorEntidade;

    @Override
    public List<Produto> findByFilterPesquisa(ProdutoFiltroPesquisaDTO produtoFiltroPesquisaDTO) {
        String query = "SELECT p FROM Produto  p ";
        String condicao = "WHERE ";
        Map<String, Object> parametros = new HashMap<String, Object>();

        if (produtoFiltroPesquisaDTO.categoria() != null){
            query += "JOIN p.categoria c WHERE c.id = :categoriaId ";
            parametros.put("categoriaId", produtoFiltroPesquisaDTO.categoria());
            condicao = "AND ";
        }

        if (produtoFiltroPesquisaDTO.nome() != null) {
            query += condicao + " p.nome LIKE CONCAT('%', :nome, '%') ";
            condicao = "AND ";
            parametros.put("nome", produtoFiltroPesquisaDTO.nome());
        }
        if (produtoFiltroPesquisaDTO.descricao() != null) {
            query += condicao + " p.descricao = :descricao ";
            condicao = "AND ";
            parametros.put("descricao", produtoFiltroPesquisaDTO.descricao());
        }

        if (produtoFiltroPesquisaDTO.precoMin() != null) {
            query += condicao + " p.preco >= :precoMin ";
            condicao = "AND ";
            parametros.put("precoMin", produtoFiltroPesquisaDTO.precoMin());
        }

        if (produtoFiltroPesquisaDTO.precoMax() != null) {
            query += condicao + " p.preco <= :precoMax ";
            condicao = "AND ";
            parametros.put("precoMax", produtoFiltroPesquisaDTO.precoMax());
        }

        if (produtoFiltroPesquisaDTO.qtdMin() != null) {
            query += condicao + " p.quantidade >= :quantidadeoMin ";
            condicao = "AND ";
            parametros.put("quantidadeoMin", produtoFiltroPesquisaDTO.qtdMin());
        }

        if (produtoFiltroPesquisaDTO.qtdMax() != null) {
            query += condicao + " p.quantidade <= :quantidadeMax ";
            parametros.put("quantidadeMax", produtoFiltroPesquisaDTO.qtdMax());
        }

        TypedQuery<Produto> typedQuery = gerenciadorEntidade.createQuery(query, Produto.class);

        parametros.forEach((nomeParametro, valorParametro) -> typedQuery.setParameter(nomeParametro, valorParametro));


        return typedQuery.getResultList();
    }
}

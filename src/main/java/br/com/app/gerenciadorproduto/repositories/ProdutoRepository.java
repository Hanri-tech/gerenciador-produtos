package br.com.app.gerenciadorproduto.repositories;

import br.com.app.gerenciadorproduto.entities.Categoria;
import br.com.app.gerenciadorproduto.entities.Produto;
import br.com.app.gerenciadorproduto.repositories.custom.ProdutoRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryCustom {
    Page<Produto> findByQuantidade(Integer qtd, Pageable paginacao);
}

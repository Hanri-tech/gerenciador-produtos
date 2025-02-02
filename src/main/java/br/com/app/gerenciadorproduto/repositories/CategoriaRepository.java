package br.com.app.gerenciadorproduto.repositories;

import br.com.app.gerenciadorproduto.entities.Categoria;
import br.com.app.gerenciadorproduto.repositories.custom.ProdutoRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Page<Categoria> findByNomeContaining(String nome, Pageable pageable);
}

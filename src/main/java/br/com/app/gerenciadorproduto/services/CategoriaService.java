package br.com.app.gerenciadorproduto.services;

import br.com.app.gerenciadorproduto.dtos.CategoriaDTO;
import br.com.app.gerenciadorproduto.entities.Categoria;
import br.com.app.gerenciadorproduto.repositories.CategoriaRepository;
import br.com.app.gerenciadorproduto.services.exceptions.CategoriaNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria salvarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.nome());
        return categoriaRepository.save(categoria);

    }

    public Page<Categoria> buscarTodasCategorias(Pageable paginacao) {
        return categoriaRepository.findAll(paginacao);
    }

    public Optional<Categoria> buscarCategoriaPorId(int id) {
        return categoriaRepository.findById(id);
    }

    public Categoria atualizarCategoria(int id, CategoriaDTO categoriaDTO) throws Exception {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(CategoriaNaoEncontradaException::new);

        categoria.setNome(categoriaDTO.nome());
        return categoriaRepository.save(categoria);
    }

    public void deletarCategoria(int id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(CategoriaNaoEncontradaException::new);
        categoriaRepository.delete(categoria);
    }

    public Page<CategoriaDTO> filtrarCategoriaPorNome(String nome, Pageable paginacao) {
        Page<Categoria> nomeFiltrado = categoriaRepository.findByNomeContaining(nome, paginacao);
        return nomeFiltrado.map(categoria -> new CategoriaDTO(categoria.getNome()));
    }
}

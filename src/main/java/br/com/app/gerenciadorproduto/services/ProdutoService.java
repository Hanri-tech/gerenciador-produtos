package br.com.app.gerenciadorproduto.services;

import br.com.app.gerenciadorproduto.dtos.ProdutoDTO;
import br.com.app.gerenciadorproduto.dtos.ProdutoFiltroPesquisaDTO;
import br.com.app.gerenciadorproduto.entities.Categoria;
import br.com.app.gerenciadorproduto.entities.Produto;
import br.com.app.gerenciadorproduto.repositories.CategoriaRepository;
import br.com.app.gerenciadorproduto.repositories.ProdutoRepository;
import br.com.app.gerenciadorproduto.services.exceptions.CategoriaNaoEncontradaException;
import br.com.app.gerenciadorproduto.services.exceptions.ProdutoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto salvarProduto(ProdutoDTO produtoDTO) {
        Categoria categoria = categoriaRepository.findById(produtoDTO.categoriaId())
                .orElseThrow(CategoriaNaoEncontradaException::new);
        Produto produto = new Produto(produtoDTO);
        produto.setCategoria(categoria);
        return produtoRepository.save(produto);

    }

    public Page<Produto> buscarTodosProdutos(Pageable paginacao) {
        return produtoRepository.findAll(paginacao);
    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto atualizarProduto(Long id, ProdutoDTO produtoDTO) throws Exception {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(ProdutoNaoEncontradoException::new);

        categoriaRepository.findById(produtoDTO.categoriaId()).ifPresent(produto::setCategoria);

        if (produtoDTO.nome() != null && !produtoDTO.nome().isEmpty()) {
            produto.setNome(produtoDTO.nome());
        }
        if (produtoDTO.descricao() != null && !produtoDTO.descricao().isEmpty()) {
            produto.setDescricao(produtoDTO.descricao());
        }
        if (produtoDTO.preco() != null) {
            produto.setPreco(produtoDTO.preco());
        }
        if (produtoDTO.quantidade() != null) {
            produto.setQuantidade(produtoDTO.quantidade());
        }


        return produtoRepository.save(produto);
    }

    public void deletarProduto(long id) {
        Produto categoria = produtoRepository.findById(id).orElseThrow(ProdutoNaoEncontradoException::new);
        produtoRepository.delete(categoria);
    }

    public Page<ProdutoDTO> filtrarProdutoPorQuantidade(Integer qtd, Pageable paginacao) {
        Page<Produto> produtos = produtoRepository.findByQuantidade(qtd, paginacao);
        return produtos.map(produto -> new ProdutoDTO(produto.getNome(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getQuantidade(),
                produto.getCategoria().getId()));
    }

    public List<Produto> filtrarProdutoPorAtributo(ProdutoFiltroPesquisaDTO produtoFiltroPesquisaDTO) {

        return produtoRepository.findByFilterPesquisa(produtoFiltroPesquisaDTO);
    }
}

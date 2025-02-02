package br.com.app.gerenciadorproduto.controllers;

import br.com.app.gerenciadorproduto.dtos.ProdutoDTO;
import br.com.app.gerenciadorproduto.dtos.ProdutoFiltroPesquisaDTO;
import br.com.app.gerenciadorproduto.entities.Produto;
import br.com.app.gerenciadorproduto.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController()
@RequestMapping("/api/v1/gerencia-produto/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping()
    public ResponseEntity<Produto> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
        Produto produto = produtoService.salvarProduto(produtoDTO);
        return ResponseEntity.ok(produto);

    }

    @GetMapping()
    public ResponseEntity<Page<Produto>> buscarTodosProdutos(Pageable paginacao) {
        Page<Produto> produtos = produtoService.buscarTodosProdutos(paginacao);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPoId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable("id") Long id, @RequestBody ProdutoDTO produtoDTO) throws Exception {
        Produto produto = produtoService.atualizarProduto(id, produtoDTO);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable("id") Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar")
    public ResponseEntity<Page<ProdutoDTO>> buscarProdutosPorNome(@RequestParam(value = "quantidade", required = false) Integer quantidade,
                                                                      Pageable paginacao) {
        if (quantidade == null) {
            return ResponseEntity.notFound().build();
        }

        Page<ProdutoDTO> produtoDTOS = produtoService.filtrarProdutoPorQuantidade(quantidade, paginacao);

        if (produtoDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }


        return ResponseEntity.ok(produtoDTOS);
    }

    @GetMapping("/filtros")
    public ResponseEntity<List<Produto>> pesquisaProduto(@RequestParam(value = "nome", required = false) String nome,
                                                            @RequestParam(value = "precoMin", required = false) BigDecimal precoMin,
                                                            @RequestParam(value = "precoMax", required = false) BigDecimal precoMax,
                                                            @RequestParam(value = "descricao", required = false) String descricao,
                                                            @RequestParam(value = "quantidadeMin", required = false) Integer quantidadeMin,
                                                            @RequestParam(value = "quantidadeMax", required = false) Integer quantidadeMax,
                                                            @RequestParam(value = "categoria", required = false) Integer categoria) {
        ProdutoFiltroPesquisaDTO produtoFiltroPesquisaDTO = new ProdutoFiltroPesquisaDTO(
                nome,
                descricao,
                quantidadeMin,
                quantidadeMax,
                precoMin,
                precoMax,
                categoria);
        List<Produto> categoriaDTOS = produtoService.filtrarProdutoPorAtributo(produtoFiltroPesquisaDTO);
        if (categoriaDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoriaDTOS);
    }
}

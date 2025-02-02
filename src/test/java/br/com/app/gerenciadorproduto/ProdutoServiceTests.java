package br.com.app.gerenciadorproduto;

import br.com.app.gerenciadorproduto.dtos.ProdutoDTO;
import br.com.app.gerenciadorproduto.entities.Categoria;
import br.com.app.gerenciadorproduto.entities.Produto;
import br.com.app.gerenciadorproduto.repositories.CategoriaRepository;
import br.com.app.gerenciadorproduto.repositories.ProdutoRepository;
import br.com.app.gerenciadorproduto.services.ProdutoService;
import br.com.app.gerenciadorproduto.services.exceptions.CategoriaNaoEncontradaException;
import br.com.app.gerenciadorproduto.services.exceptions.ProdutoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProdutoServiceTests {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarProduto() {
        ProdutoDTO mockProdutoDTO = new ProdutoDTO("Notebook", BigDecimal.valueOf(2500.0), "Notebook Gamer", 10, 1);
        Categoria mockCategoria = new Categoria(1, "Eletrônicos");
        Produto mockProduto = new Produto(mockProdutoDTO);
        mockProduto.setCategoria(mockCategoria);

        when(categoriaRepository.findById(Integer.valueOf(1))).thenReturn(Optional.of(mockCategoria));
        when(produtoRepository.save(any(Produto.class))).thenReturn(mockProduto);

        Produto saveProduto = produtoService.salvarProduto(mockProdutoDTO);

        assertNotNull(saveProduto);
        assertEquals("Notebook", saveProduto.getNome());
    }

    @Test
    void testBuscarTodosProdutos() {
        Pageable paginacao = PageRequest.of(0, 10);
        ProdutoDTO mockprodutoDTO = new ProdutoDTO("Notebook", BigDecimal.valueOf(2500.0), "Notebook Gamer", 10, 1);

        List<Produto> produtos = List.of(new Produto(mockprodutoDTO));
        Page<Produto> mockProdutoPage = new PageImpl<>(produtos);

        when(produtoRepository.findAll(paginacao)).thenReturn(mockProdutoPage);

        Page<Produto> resultados = produtoService.buscarTodosProdutos(paginacao);

        assertEquals(1, resultados.getTotalElements());
        assertEquals("Notebook", resultados.getContent().get(0).getNome());
    }

    @Test
    void testBuscarProdutoPorId() {
        ProdutoDTO mockprodutoDTO = new ProdutoDTO("Notebook", BigDecimal.valueOf(2500.0), "Notebook Gamer", 10, 1);

        Produto mockProduto = new Produto(mockprodutoDTO);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(mockProduto));

        Optional<Produto> resultados = produtoService.buscarProdutoPorId(1L);

        assertTrue(resultados.isPresent());
        assertEquals("Notebook", resultados.get().getNome());
    }

    @Test
    void testAtualizarProduto() throws Exception {
        ProdutoDTO mockSaveprodutoDTO = new ProdutoDTO("Notebook", BigDecimal.valueOf(2500.0), "Notebook Gamer", 10, 1);
        Produto mockSaveProduto = new Produto(mockSaveprodutoDTO);
        ProdutoDTO produtoDTO = new ProdutoDTO("Notebook Atualizado", BigDecimal.valueOf(3000.0), "Novo Modelo", 5, 1);
        Categoria categoria = new Categoria(1 , "Eletrônicos");

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(mockSaveProduto));
        when(categoriaRepository.findById(Integer.valueOf(1))).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(any(Produto.class))).thenReturn(mockSaveProduto);

        Produto atualizarProduto = produtoService.atualizarProduto(1L, produtoDTO);

        assertEquals("Notebook Atualizado", atualizarProduto.getNome());
    }

    @Test
    void testDeletarProduto() {
        ProdutoDTO mockprodutoDTO = new ProdutoDTO("Notebook", BigDecimal.valueOf(2500.0), "Notebook Gamer", 10, 1);
        Produto mockProduto = new Produto(mockprodutoDTO);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(mockProduto));
        doNothing().when(produtoRepository).delete(mockProduto);

        assertDoesNotThrow(() -> produtoService.deletarProduto(1L));
    }
}

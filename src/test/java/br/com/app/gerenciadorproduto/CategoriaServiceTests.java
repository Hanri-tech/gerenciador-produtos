package br.com.app.gerenciadorproduto;

import br.com.app.gerenciadorproduto.dtos.CategoriaDTO;
import br.com.app.gerenciadorproduto.entities.Categoria;
import br.com.app.gerenciadorproduto.repositories.CategoriaRepository;
import br.com.app.gerenciadorproduto.services.CategoriaService;
import br.com.app.gerenciadorproduto.services.exceptions.CategoriaNaoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaServiceTests {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarCategoria() {
        CategoriaDTO categoriaDTO = new CategoriaDTO("Eletrônicos");
        Categoria mockCategoria = new Categoria();
        mockCategoria.setNome(categoriaDTO.nome());

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(mockCategoria);

        Categoria salvarCategoria = categoriaService.salvarCategoria(categoriaDTO);

        assertNotNull(salvarCategoria);
        assertEquals("Eletrônicos", salvarCategoria.getNome());
    }

    @Test
    void testBuscarTodasCategorias() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Categoria> mockCategorias = List.of(new Categoria(1 , "Eletrônicos"), new Categoria(2, "Livros"));
        Page<Categoria> mockCategoriaPage = new PageImpl<>(mockCategorias);

        when(categoriaRepository.findAll(pageable)).thenReturn(mockCategoriaPage);

        Page<Categoria> resultados = categoriaService.buscarTodasCategorias(pageable);

        assertEquals(2, resultados.getTotalElements());
        assertEquals("Eletrônicos", resultados.getContent().get(0).getNome());
    }

    @Test
    void testBuscarCategoriaPorId_Encontrada() {
        Categoria categoria = new Categoria(1, "Eletrônicos");
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));

        Optional<Categoria> resultados = categoriaService.buscarCategoriaPorId(1);

        assertTrue(resultados.isPresent());
        assertEquals("Eletrônicos", resultados.get().getNome());
    }

    @Test
    void testBuscarCategoriaPorId_NaoEncontrada() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Categoria> resultados = categoriaService.buscarCategoriaPorId(1);

        assertFalse(resultados.isPresent());
    }

    @Test
    void testAtualizarCategoria() throws Exception {
        Categoria categoria = new Categoria(5, "Antigo");
        CategoriaDTO categoriaDTO = new CategoriaDTO("Atualizado");

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria categoria1 = categoriaService.atualizarCategoria(1, categoriaDTO);

        assertEquals("Atualizado", categoria1.getNome());
    }

    @Test
    void testAtualizarCategoria_NaoEncontrada() {
        CategoriaDTO categoriaDTO = new CategoriaDTO("Atualizado");
        when(categoriaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CategoriaNaoEncontradaException.class, () -> categoriaService.atualizarCategoria(1, categoriaDTO));
    }

    @Test
    void testDeletarCategoria() {
        Categoria categoria = new Categoria(1, "Eletrônicos");
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        doNothing().when(categoriaRepository).delete(categoria);

        assertDoesNotThrow(() -> categoriaService.deletarCategoria(1));
    }

    @Test
    void testDeletarCategoria_NaoEncontrada() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CategoriaNaoEncontradaException.class, () -> categoriaService.deletarCategoria(1));
    }

    @Test
    void testFiltrarCategoriaPorNome() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Categoria> categorias = List.of(new Categoria(1, "Eletrônicos"));
        Page<Categoria> mockCategoriaPage = new PageImpl<>(categorias);

        when(categoriaRepository.findByNomeContaining("Eletrônicos", pageable)).thenReturn(mockCategoriaPage);

        Page<CategoriaDTO> resultados = categoriaService.filtrarCategoriaPorNome("Eletrônicos", pageable);

        assertEquals(1, resultados.getTotalElements());
        assertEquals("Eletrônicos", resultados.getContent().get(0).nome());
    }
}

package br.com.app.gerenciadorproduto.controllers;

import br.com.app.gerenciadorproduto.dtos.CategoriaDTO;
import br.com.app.gerenciadorproduto.entities.Categoria;
import br.com.app.gerenciadorproduto.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/gerencia-produto/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping()
    public ResponseEntity<Categoria> criarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaService.salvarCategoria(categoriaDTO);
        return ResponseEntity.ok(categoria);

    }

    @GetMapping()
    public ResponseEntity<Page<Categoria>> buscarTodasCategorias(Pageable paginacao) {
        Page<Categoria> categorias = categoriaService.buscarTodasCategorias(paginacao);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPoId(@PathVariable int id) {
        return categoriaService.buscarCategoriaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable("id") int id, @RequestBody CategoriaDTO categoriaDTO) throws Exception {
        Categoria categoria = categoriaService.atualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable("id") int id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar")
    public ResponseEntity<Page<CategoriaDTO>> buscarCategoriasPorNome(@RequestParam(value = "nome", required = false) String nome,Pageable paginacao) {
        if (nome == null || nome.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Page<CategoriaDTO> categoriaDTOS = categoriaService.filtrarCategoriaPorNome(nome, paginacao);
        return ResponseEntity.ok(categoriaDTOS);
    }
}

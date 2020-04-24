package com.eacj.osworksapi.api.controller;

import com.eacj.osworksapi.domain.model.Cliente;
import com.eacj.osworksapi.domain.repository.ClienteRepository;
import com.eacj.osworksapi.domain.service.CadastroClienteService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private CadastroClienteService cadastroClienteService;
    
    @GetMapping
    public List<Cliente> listar(){
        //return clienteRepository.findByNomeContaining("nome");
        //return clienteRepository.findByNome("nome");
        return clienteRepository.findAll();
    }
    
    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        
        if(cliente.isPresent())
            return ResponseEntity.ok(cliente.get());
        
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente){
        return cadastroClienteService.salvar(cliente);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long id, @RequestBody Cliente cliente){
        if(!clienteRepository.existsById(id))
            return ResponseEntity.notFound().build();
        
        cliente.setId(id);
        cliente = cadastroClienteService.salvar(cliente);
        
        return ResponseEntity.ok(cliente);
    }
    
    //Para retornar um response entity sem corpo, passar como tipo o Void
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> remover(@PathVariable Long id){
        if(!clienteRepository.existsById(id))
            return ResponseEntity.notFound().build();
        
        cadastroClienteService.remover(id);
        return ResponseEntity.noContent().build();
    }
    
}

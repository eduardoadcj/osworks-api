package com.eacj.osworksapi.api.controller;

import com.eacj.osworksapi.domain.model.Cliente;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    
    @GetMapping("/clientes")
    public List<Cliente> listar(){
        
        var cliente1 = new Cliente();
        cliente1.setId(1l);
        cliente1.setNome("João");
        cliente1.setEmail("joao@email.com");
        cliente1.setTelefone("44 44444-4444");
        
        var cliente2 = new Cliente();
        cliente2.setId(2l);
        cliente2.setNome("Maria");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("44 44444-4444");
        
        return Arrays.asList(cliente1, cliente2);
        
    }
    
}

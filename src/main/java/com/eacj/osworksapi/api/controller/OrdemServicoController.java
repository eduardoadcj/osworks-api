
package com.eacj.osworksapi.api.controller;

import com.eacj.osworksapi.api.model.OrdemServicoInputModel;
import com.eacj.osworksapi.api.model.OrdemServicoModel;
import com.eacj.osworksapi.domain.model.OrdemServico;
import com.eacj.osworksapi.domain.repository.OrdemServicoRepository;
import com.eacj.osworksapi.domain.service.GestaoOrdemServicoService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
    
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;
    
    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServicoService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInputModel ordemServicoInput) {
        OrdemServico ordemServico = toEntity(ordemServicoInput);
        return toModel(gestaoOrdemServicoService.criar(ordemServico));
    }
    
    @GetMapping
    public List<OrdemServicoModel> listar(){
        return toCollectionModel(ordemServicoRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long id){
        Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(id);
        
        if(ordemServico.isPresent())
            return ResponseEntity.ok(toModel(ordemServico.get()));
            
        return ResponseEntity.notFound().build();
        
    }
    
    @PutMapping("/{ordemServicoId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long ordemServicoId){
        gestaoOrdemServicoService.finalizar(ordemServicoId);
    }
    
    private OrdemServicoModel toModel(OrdemServico ordemServico){
        return modelMapper.map(ordemServico, OrdemServicoModel.class);
    }
    
    private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico){
        return ordensServico.stream()
                .map(ordemServico -> toModel(ordemServico))
                .collect(Collectors.toList());
    }
    
    private OrdemServico toEntity(OrdemServicoInputModel ordemServicoInputModel){
        return modelMapper.map(ordemServicoInputModel, OrdemServico.class);
    }
    
}

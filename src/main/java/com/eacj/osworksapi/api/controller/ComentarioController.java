
package com.eacj.osworksapi.api.controller;

import com.eacj.osworksapi.api.model.ComentarioInputModel;
import com.eacj.osworksapi.api.model.ComentarioModel;
import com.eacj.osworksapi.domain.exception.EntidadeNaoEncontradaException;
import com.eacj.osworksapi.domain.model.Comentario;
import com.eacj.osworksapi.domain.model.OrdemServico;
import com.eacj.osworksapi.domain.repository.OrdemServicoRepository;
import com.eacj.osworksapi.domain.service.GestaoOrdemServicoService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
    
    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServicoService;
    
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioModel adicionar(@PathVariable Long ordemServicoId,
            @Valid @RequestBody ComentarioInputModel comentarioInputModel){
        
        Comentario comentario = gestaoOrdemServicoService.adicionarComentario(ordemServicoId,
                comentarioInputModel.getDescricao());
        
        return toModel(comentario);
        
    }
    
    @GetMapping
    public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
        return toCollectionModel(ordemServico.getComentarios());
    }
    
    private ComentarioModel toModel(Comentario comentario){
        return modelMapper.map(comentario, ComentarioModel.class);
    }
    
    private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios){
        return comentarios.stream()
                .map((comentario) -> toModel(comentario))
                .collect(Collectors.toList());
    }
    
}

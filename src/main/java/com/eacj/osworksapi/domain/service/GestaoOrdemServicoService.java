
package com.eacj.osworksapi.domain.service;

import com.eacj.osworksapi.domain.exception.NegocioException;
import com.eacj.osworksapi.domain.model.Cliente;
import com.eacj.osworksapi.domain.model.OrdemServico;
import com.eacj.osworksapi.domain.model.StatusOrdemServico;
import com.eacj.osworksapi.domain.repository.ClienteRepository;
import com.eacj.osworksapi.domain.repository.OrdemServicoRepository;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestaoOrdemServicoService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;
    
    public OrdemServico criar(OrdemServico ordemServico){
        
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado"));
        
        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());
        return ordemServicoRepository.save(ordemServico);
    }
    
}

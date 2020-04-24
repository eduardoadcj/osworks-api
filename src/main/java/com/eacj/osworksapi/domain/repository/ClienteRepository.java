
package com.eacj.osworksapi.domain.repository;

import com.eacj.osworksapi.domain.model.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
    List<Cliente> findByNome(String nome);
    List<Cliente> findByNomeContaining(String nome);
    
}

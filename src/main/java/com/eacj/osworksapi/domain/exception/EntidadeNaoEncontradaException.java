
package com.eacj.osworksapi.domain.exception;

import com.eacj.osworksapi.domain.exception.NegocioException;

public class EntidadeNaoEncontradaException extends NegocioException{
    
    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
    
}

/* Exceção lançada quando algo não existe no banco (usuário, produto, pedido...). O GlobalExceptionHandler a converte em erro 404. */

package com.umamifoods.api.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Admin;
import br.unitins.topicos1.model.Cliente;
import br.unitins.topicos1.model.Usuario;

public record UsuarioResponseDTO(
    String username,
    String nome
) {
    public static UsuarioResponseDTO valueOfCliente(Cliente cliente) {
        return new UsuarioResponseDTO(
                cliente.getUsuario().getUsername(),
                cliente.getNome()
            );
    }
    
    public static UsuarioResponseDTO valueOfAdmin(Admin admin) {
        return new UsuarioResponseDTO(
                admin.getUsuario().getUsername(),
                admin.getNome()
            );
    }
    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getUsername(),
                usuario.getUsername()
            );
    }
    
}
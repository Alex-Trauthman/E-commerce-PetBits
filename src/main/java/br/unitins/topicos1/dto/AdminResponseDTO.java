package br.unitins.topicos1.dto;

import java.util.List;

import br.unitins.topicos1.model.Admin;



public record AdminResponseDTO(
    Long id,
    String nome,
    String email,
    String username, 
    Double salario,
    String cargo,
    List<TelefoneResponseDTO> telefones
) {
    public static AdminResponseDTO valueOf(Admin admin) {
        List<TelefoneResponseDTO> lista = admin.getListaTelefone()
                                            .stream()
                                            .map(TelefoneResponseDTO::valueOf)
                                            .toList();
        return new AdminResponseDTO(
            admin.getId(), 
            admin.getNome(), 
            admin.getEmail(),
            admin.getUsuario().getUsername(),
            admin.getSalario(),
            admin.getCargo(),
            lista);
    }
    
}
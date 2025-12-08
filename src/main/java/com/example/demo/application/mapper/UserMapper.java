package com.example.demo.application.mapper;

import com.example.demo.application.dto.RegisterDTO;
import com.example.demo.application.dto.UserDTO;
import com.example.demo.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Converte de DTO de Registro para Entidade (Usado no criar conta)
    public User toEntity(RegisterDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        // Nota: A senha e a role serão tratadas no Service por segurança
        return user;
    }

    // Converte de Entidade para DTO de Resposta (Para retornar dados do usuário sem
    // senha)
    // Você precisará criar o UserDTO simples se ainda não tiver
    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }
        // Supondo que você crie um UserDTO com id, name, email e role
        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRole());
    }
}
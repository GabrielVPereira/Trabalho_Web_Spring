package com.example.demo.application.mapper;

import com.example.demo.application.dto.RegisterDTO;
import com.example.demo.application.dto.UserDTO;
import com.example.demo.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

   
    public User toEntity(RegisterDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
       
        return user;
    }


    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }

        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRole());
    }
}

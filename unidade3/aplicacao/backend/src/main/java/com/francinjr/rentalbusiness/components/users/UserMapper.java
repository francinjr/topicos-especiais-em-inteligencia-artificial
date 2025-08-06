package com.francinjr.rentalbusiness.components.users;


import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static BuscarUserDto userToBuscarUserDto(User user){
        BuscarUserDto dto = new BuscarUserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setIsPasswordChanged(user.getIsPasswordChanged());
        return dto;
    }

    public static List<BuscarUserDto> userListToBuscarUserDto(List<User> users){
        List<BuscarUserDto> dtos = new ArrayList<>();
        for(User user: users){
            dtos.add(userToBuscarUserDto(user));
        }
        return dtos;
    }

    public static User saveUserDtoToUser(SalvarUserDto dto){
        User entity = new User();
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        return entity;
    }

    public static User loginDtoToUser(LoginDto dto){
        User entity = new User();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
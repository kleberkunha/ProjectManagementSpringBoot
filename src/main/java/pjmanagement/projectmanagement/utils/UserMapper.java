package pjmanagement.projectmanagement.utils;

import org.springframework.stereotype.Component;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.entities.UserEntity;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setEmail(userEntity.getEmail());
        userDto.setUsername(userEntity.getUsername());
        return userDto;
    }
}
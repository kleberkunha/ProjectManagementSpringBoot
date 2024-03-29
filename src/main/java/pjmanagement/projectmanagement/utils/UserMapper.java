package pjmanagement.projectmanagement.utils;

import org.springframework.stereotype.Component;
import pjmanagement.projectmanagement.dto.UserDto;
import pjmanagement.projectmanagement.entities.UserEntity;

@Component
public class UserMapper {

    public static UserDto toDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setRole(String.valueOf(userEntity.getRole()));
        userDto.setCreatedAt(userEntity.getCreatedAt());
        userDto.setUpdatedAt(userEntity.getUpdatedAt());
        return userDto;
    }
}
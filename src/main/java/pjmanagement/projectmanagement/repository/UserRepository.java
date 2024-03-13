package pjmanagement.projectmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjmanagement.projectmanagement.entities.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByUsername(String username);
    List<UserEntity> findAll();

}

package pjmanagement.projectmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pjmanagement.projectmanagement.entities.ProjectEntity;
import pjmanagement.projectmanagement.entities.UserEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

}

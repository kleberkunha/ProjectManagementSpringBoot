package pjmanagement.projectmanagement.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;


}

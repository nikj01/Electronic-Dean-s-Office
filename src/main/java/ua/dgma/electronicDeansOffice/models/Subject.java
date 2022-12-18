package ua.dgma.electronicDeansOffice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "subject")
@Data
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Subject name cannot be empty")
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "subject")
    private List<Grade> grades;
}

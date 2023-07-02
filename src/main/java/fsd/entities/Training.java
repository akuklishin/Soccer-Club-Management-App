package fsd.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "trainings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Training {

    public enum Position{
        GENERAL, GOALKEEPER, DEFENDER, ATTACKER
    }

    public enum Deleted{
        NO, YES
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 1000)
    @Size(min=10, max = 1000)
    @NotEmpty(message = "Description Required")
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(length = 3000)
    @Size(min=5, max = 3000)
    private String notes;

    @Enumerated(EnumType.STRING)
    private Deleted deleted = Deleted.NO;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;


}

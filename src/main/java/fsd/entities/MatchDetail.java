package fsd.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "matchdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MatchDetail {

    public enum Event{
        GOAL, ASSIST, YELLOW, RED
    }

    public enum Deleted{
        NO, YES
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Event event;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id", nullable = false)
    private Match match;

    @NotNull
    @Min(1)
    @Max(90)
    @Range(min = 1, max = 90)
    private int minute;

    @Enumerated(EnumType.STRING)
    private  Deleted deleted = Deleted.NO;

}

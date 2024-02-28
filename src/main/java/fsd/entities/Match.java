package fsd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "matches")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Match {

    public enum Result{
        WIN, DRAW, LOSE
    }

    public enum Deleted{
        NO, YES
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    @Column(length = 50)
    @Size(min = 2, max = 50)
    private String opponentName;

    @NotNull
    @Min(0)
    private int ourScore;

    @NotNull
    @Min(0)
    private int opponentScore;

    @Enumerated(EnumType.STRING)
    public Deleted deleted = Deleted.NO;

    @Enumerated(EnumType.STRING)
    private Result result;

    @OneToMany(mappedBy = "match")
    @JsonIgnore
    private List<MatchDetail> matchDetails;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Date='" + date + "'" +
                ", Opponent='" + opponentName + "'" +
                ", Our score='" + ourScore + "'" +
                ", Opponent Score='" + opponentScore + "'" +
                ", Result='" + result + "'" +
                "}";
    }
}

package fsd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {

    public enum Role{
        ADMIN, COACH, PLAYER
    }

    public enum Position{
        ADMIN, GENERAL, GOALKEEPER, DEFENDER, ATTACKER
    }

    public enum Deleted{
        NO, YES
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50)
    @Size(min=2, max = 50)
    private String firstName;

    @NotNull
    @Column(length = 50)
    @Size(min=2, max = 50)
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Position position;

    @NotNull
    @Column(length = 100)
    @Size(min=8, max = 100)
    private String password;
    
    @Column(length = 100)
    private String repeatPassword;

    @NotNull
    @Column(unique = true, length = 50)
    @Email
    private String email;

    private String imagePath;

    @Enumerated(EnumType.STRING)
    public Deleted deleted = Deleted.NO;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Training> trainings;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<MatchDetail> matchDetails;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first name='" + firstName + "'" +
                ", last name='" + lastName + "'" +
                ", role='" + role + "'" +
                ", position='" + position + "'" +
                ", email='" + email + "'" +
                "}";
    }

}

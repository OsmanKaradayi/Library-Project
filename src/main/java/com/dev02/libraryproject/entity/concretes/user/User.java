package com.dev02.libraryproject.entity.concretes.user;

import com.dev02.libraryproject.entity.concretes.business.Loan;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2, max = 30, message = "(${validatedValue}) {min} and {max} lengths allowed!")
    private String firstName;

    @Column(nullable = false)
    @Size(min = 2, max = 30, message = "(${validatedValue}) {min} and {max} lengths allowed!")
    private String lastName;

    @Column(nullable = false)
    @Size(min = -2, max = 2, message = "(${validatedValue}) {min} and {max} lengths allowed!")
    private int score = 0;

    @Column(nullable = false)
    @Size(min = 10, max = 100, message = "(${validatedValue}) {min} and {max} lengths allowed!")
    private String address;

    @Column(nullable = false)
    @Size(min = 12, max = 12, message = "Your phone number should be 12 characters long")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Please enter a valid phone number in the format 999-999-9999")
    private String phone;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Your birthday can not be in the future")
    private LocalDate birthDate;

    @Email
    @Column(nullable = false)
    @Size(min = 10, max = 80, message = "(${validatedValue}) {min} and {max} lengths allowed!")
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createDate;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }

    @Column(nullable = false)
    private String resetPasswordCode;

    @Column(nullable = false)
    private Boolean builtIn;

    @OneToMany(mappedBy = "userId",cascade = CascadeType.REMOVE)
    private List<Loan> loanList;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Role> roles;

}

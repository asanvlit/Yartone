package ru.asanvlit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.asanvlit.dto.enums.AccountRole;
import ru.asanvlit.dto.enums.AccountState;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class AccountEntity extends AbstractEntity {

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(320)")
    private String email;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(30)")
    private String username;

    @Column(columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(columnDefinition = "VARCHAR(150)")
    private String biography;

    private LocalDate birthdate;

    @Column(name = "password_hash", columnDefinition = "VARCHAR(80)")
    private String passwordHash;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private AccountRole role;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private AccountState state;

    @OneToOne
    @JoinColumn(name = "photo_id")
    private FileInfoEntity profilePhoto;

    @OneToMany(mappedBy = "author")
    private Set<PostEntity> posts;

    @OneToMany(mappedBy = "account", cascade = {PERSIST, REFRESH, REMOVE})
    private Set<ConfirmCodeEntity> codes;
}


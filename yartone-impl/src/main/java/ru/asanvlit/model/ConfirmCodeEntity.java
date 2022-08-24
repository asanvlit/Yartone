package ru.asanvlit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "confirm_code")
public class ConfirmCodeEntity extends AbstractEntity {

    @Column(nullable = false, columnDefinition = "VARCHAR(64)")
    private String code;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "code_type_id", nullable = false)
    private ConfirmCodeTypeEntity confirmCodeType;
}

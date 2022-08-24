package ru.asanvlit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.asanvlit.dto.enums.ConfirmCodeType;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "confirm_code_type")
public class ConfirmCodeTypeEntity extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "code_type", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private ConfirmCodeType confirmCodeType;

    @Column(name = "valid_time", nullable = false, columnDefinition = "FLOAT8")
    private Double validTime;
}

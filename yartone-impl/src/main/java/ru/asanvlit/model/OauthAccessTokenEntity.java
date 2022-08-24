package ru.asanvlit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class OauthAccessTokenEntity extends AbstractEntity {

    @Column(name = "token", nullable = false, unique = true, columnDefinition = "VARCHAR(200)")
    private String accessToken;
}


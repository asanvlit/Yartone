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
@Table(name = "vk_account_access_token")
public class VkOauthAccountAccessTokenEntity extends OauthAccessTokenEntity {

    @Column(name = "vk_account_id", columnDefinition = "BIGINT")
    private Long vkAccountId;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
}


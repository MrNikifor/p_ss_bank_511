package com.bank.profile.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "account_details_id")
@Data
@NoArgsConstructor
@NotNull
public class AccountDetailsId {

    @Id
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "profile_id")
    private Long profileId;
}

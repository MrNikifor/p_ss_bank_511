package com.bank.profile.entity;

import com.bank.profile.entity.abstracts.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "registration", schema = "profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @Size(max = 166)
    private String country;

    @Size(max = 160)
    private String region;

    @Size(max = 160)
    private String city;

    @Size(max = 160)
    private String district;

    @Size(max = 230)
    private String locality;

    @Size(max = 230)
    private String street;

    @Size(max = 20)
    private String houseNumber;

    @Size(max = 20)
    private String houseBlock;

    @Size(max = 40)
    private String flatNumber;

    @Column(nullable = false)
    private Long index;
}

package com.bank.profile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "passport")
@Data
@NoArgsConstructor
@NotNull
public class Passport {

    @Id
    private Long id;

    private Integer series;

    private Long number;

    @Column(name = "last_name")
    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 255)
    @Column(name = "middle_name", nullable = true)
    private String middleName;

    @Size(max = 3)
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(max = 480)
    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "issued_by")
    private String issuedBy;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(name = "division_code")
    private Integer divisionCode;

    @Column(name = "expiration_date", nullable = true)
    private LocalDate expirationDate;

    @Column(name = "registration_id")
    private Long registrationId;

    @AllArgsConstructor
    @Getter
    public enum Gender {
        ЖЕН("ЖЕН"),
        МУЖ("МУЖ");

        private final String value;
    }
}

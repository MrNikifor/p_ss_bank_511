package com.bank.publicinfo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "license")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Указывает, что поле будет храниться как большой объект (BLOB)
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photo; // Фотография лицензии в виде массива байтов

    @ManyToOne
    @JoinColumn(name = "bank_details_id", nullable = false) // Связь с bank_details
    private BankDetails bankDetails; // Ссылка на bank_details, к которым относится лицензия
}


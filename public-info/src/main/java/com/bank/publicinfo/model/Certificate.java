package com.bank.publicinfo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Указывает, что поле будет храниться как большой объект (BLOB)
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photo; // Фотография сертификата в виде массива байтов

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_details_id", nullable = false) // Связь с bank_details
    private BankDetails bankDetails; // Ссылка на банковские детали, к которым относится сертификат
}


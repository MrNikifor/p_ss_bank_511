package com.bank.publicinfo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_details")
public class BankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор банковских деталей

    @Column(name = "bik", unique = true, nullable = false)
    private long bik; // БИК (Банковский Идентификационный Код)

    @Column(name = "inn", unique = true, nullable = false)
    private long inn; // ИНН (Идентификационный Номер Налогоплательщика)

    @Column(name = "kpp", unique = true, nullable = false)
    private long kpp; // КПП (Код Причины Поставки)

    @Column(name = "cor_account", unique = true, nullable = false)
    private int corAccount; // Корреспондентский счет банка

    @Column(name = "city", nullable = false)
    private String city; // Город, в котором расположен банк

    @Column(name = "joint_stock_company", nullable = false)
    private String jointStockCompany; // Название акционерного общества

    @Column(name = "name", nullable = false)
    private String name; // Название банка

    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<License> licenses = new HashSet<>(); // Связь с лицензиями банка

    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Certificate> certificates = new HashSet<>(); // Связь с сертификатами банка
}



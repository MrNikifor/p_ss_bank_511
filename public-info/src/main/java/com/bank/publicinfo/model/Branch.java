package com.bank.publicinfo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор филиала

    @Column(name = "address", nullable = false)
    private String address; // Адрес филиала

    @Column(name = "phoneNumber", unique = true, nullable = false)
    private Long phoneNumber; // Номер телефона филиала (уникальный)

    @Column(name = "city", nullable = false)
    private String city; // Город, в котором расположен филиал

    @Column(name = "start_of_work", nullable = false)
    private LocalTime startOfWork; // Время открытия филиала

    @Column(name = "end_of_work", nullable = false)
    private LocalTime endOfWork; // Время закрытия филиала

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ATM> atms = new HashSet<>(); // Связь с банкоматами, принадлежащими этому
}
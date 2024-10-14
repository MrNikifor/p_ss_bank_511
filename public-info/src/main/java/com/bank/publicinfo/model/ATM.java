package com.bank.publicinfo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "atm")
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // Уникальный идентификатор банкомата

    @Column(name = "address", nullable = false)
    private String address; // Адрес банкомата

    @Column(name = "start_of_work")
    private LocalTime startOfWork; // Время открытия банкомата

    @Column(name = "end_of_work")
    private LocalTime endOfWork; // Время закрытия банкомата

    @Column(name = "all_hours", nullable = false)
    private Boolean allHours; // Поле, указывающее, работает ли банкомат круглосуточно (true) или нет (false)

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @NotNull(message = "Branch is required")
    private Branch branch; // Связь с филиалом, к которому принадлежит банкомат
}



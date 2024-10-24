package com.bank.antifraud.util;

import com.bank.antifraud.model.SuspiciousAccountTransfers;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import com.bank.antifraud.repository.SuspicionsPhoneTransfersRepository;
import com.bank.antifraud.repository.SuspiciousAccountTransfersRepository;
import com.bank.antifraud.repository.SuspiciousCardTransfersRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Util {

    private final SuspiciousAccountTransfersRepository repositoryAcc;
    private final SuspicionsPhoneTransfersRepository repositoryPh;
    private final SuspiciousCardTransfersRepository repositoryCard;

    public Util(SuspiciousAccountTransfersRepository repositoryAcc, SuspicionsPhoneTransfersRepository repositoryPh, SuspiciousCardTransfersRepository repositoryCard) {
        this.repositoryAcc = repositoryAcc;
        this.repositoryPh = repositoryPh;
        this.repositoryCard = repositoryCard;
    }


    @PostConstruct
    private void post() {

        Long id = 1L;

        SuspiciousAccountTransfers accountTransfers = SuspiciousAccountTransfers.builder()
                .id(id)
                .isBlocked(false)
                .isSuspicious(false)
                .blockedReason("frod")
                .suspiciousReason("frod")
                .build();



        SuspiciousCardTransfers cardTransfers = SuspiciousCardTransfers.builder()
                .id(id)
                .isBlocked(false)
                .isSuspicious(false)
                .blockedReason("frod")
                .suspiciousReason("frod")
                .build();

        SuspiciousPhoneTransfers phoneTransfers = SuspiciousPhoneTransfers.builder()
                .id(id)
                .isBlocked(false)
                .isSuspicious(false)
                .blockedReason("frod")
                .suspiciousReason("frod")
                .build();

        repositoryAcc.save(accountTransfers);
        repositoryCard.save(cardTransfers);
        repositoryPh.save(phoneTransfers);



    }


}

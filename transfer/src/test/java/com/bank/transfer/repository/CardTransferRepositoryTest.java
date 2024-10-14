package com.bank.transfer.repository;

import com.bank.transfer.entity.CardTransferEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class CardTransferRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CardTransferRepository cardTransferRepository;

    private CardTransferEntity cardTransferEntity;

    @BeforeEach
    void setUp() {

        cardTransferEntity = CardTransferEntity.builder()
                .cardNumber(2200330044005500L)
                .amount(BigDecimal.valueOf(12000.00))
                .purpose("test1")
                .accountDetailsId(120L)
                .build();

        entityManager.persist(cardTransferEntity);
        entityManager.flush();
    }

    @Test
    public void testFindByCardNumber_ShouldReturnCardTransferEntity() {

        Optional<CardTransferEntity> result = cardTransferRepository.findByCardNumber(2200330044005500L);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getCardNumber()).isEqualTo(2200330044005500L);
    }

    @Test
    public void testFindByCardNumber_ShouldReturnEmptyOptional() {
        Optional<CardTransferEntity> result = cardTransferRepository.findByCardNumber(1234123412341234L);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void testFindById_ShouldReturnCardTransferEntity() {

        Optional<CardTransferEntity> result = cardTransferRepository.findById(cardTransferEntity.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getCardNumber()).isEqualTo(2200330044005500L);
        assertThat(result.get().getAmount()).isEqualTo("12000.0");
        assertThat(result.get().getPurpose()).isEqualTo("test1");
        assertThat(result.get().getAccountDetailsId()).isEqualTo(120L);
    }

    @Test
    public void testDeleteById_CardTransferEntityShouldNotBeFound() {

        cardTransferRepository.deleteById(cardTransferEntity.getId());

        Optional<CardTransferEntity> result = cardTransferRepository.findById(cardTransferEntity.getId());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void testUpdateCardTransferEntity_ShouldUpdateCardTransferEntity() {

        CardTransferEntity existCardTransferEntity = cardTransferRepository.findById(cardTransferEntity.getId()).get();

        existCardTransferEntity.setAmount(BigDecimal.valueOf(20000.00));

        CardTransferEntity savedCardTransferEntity = cardTransferRepository.save(existCardTransferEntity);

        assertThat(savedCardTransferEntity.getAmount()).isEqualTo("20000.0");
    }

    @Test
    public void testSaveCardTransferEntity_ShouldSaveCardTransferEntity() {

        CardTransferEntity newCardTransferEntity = CardTransferEntity.builder()
                .cardNumber(4400550066007700L)
                .amount(BigDecimal.valueOf(9000.00))
                .purpose("test2")
                .accountDetailsId(1L)
                .build();

        CardTransferEntity savedCardTransferEntity = cardTransferRepository.save(newCardTransferEntity);

        assertThat(savedCardTransferEntity).isNotNull();
        assertThat(savedCardTransferEntity.getId()).isNotNull();
        assertThat(savedCardTransferEntity.getCardNumber()).isEqualTo(4400550066007700L);
        assertThat(savedCardTransferEntity.getAmount()).isEqualTo("9000.0");
        assertThat(savedCardTransferEntity.getPurpose()).isEqualTo("test2");
        assertThat(savedCardTransferEntity.getAccountDetailsId()).isEqualTo(1L);
    }
}

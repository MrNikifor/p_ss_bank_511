package com.bank.transfer.repository;

import com.bank.transfer.entity.AccountTransferEntity;
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
public class AccountTransferRepositoryTest {

    @Autowired
    private AccountTransferRepository accountTransferRepository;

    @Autowired
    private TestEntityManager entityManager;

    private AccountTransferEntity accountTransferEntity;

    @BeforeEach
    void setUp() {

        accountTransferEntity = AccountTransferEntity.builder()
                .accountNumber(123456789L)
                .amount(BigDecimal.valueOf(5000.00))
                .purpose("Test1")
                .accountDetailsId(1L)
                .build();

        entityManager.persist(accountTransferEntity);
        entityManager.flush();
    }

    @Test
    public void testFindByAccountNumber_ShouldReturnAccountTransferEntity() {

        Optional<AccountTransferEntity> result = accountTransferRepository.findByAccountNumber(123456789L);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getAccountNumber()).isEqualTo(123456789L);
    }

    @Test
    public void testFindByAccountNumber_ShouldReturnEmptyOptional() {

        Optional<AccountTransferEntity> result = accountTransferRepository.findByAccountNumber(987654321L);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void testFindById_ShouldReturnAccountTransferEntity() {

        Optional<AccountTransferEntity> result = accountTransferRepository.findById(1L);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getAccountNumber()).isEqualTo(123456789L);
        assertThat(result.get().getAmount()).isEqualTo("5000.0");
        assertThat(result.get().getPurpose()).isEqualTo("Test1");
        assertThat(result.get().getAccountDetailsId()).isEqualTo(1L);
    }

    @Test
    public void testDeleteById_AccountTransferEntityShouldNotBeFound() {

        accountTransferRepository.deleteById(accountTransferEntity.getId());

        Optional<AccountTransferEntity> result = accountTransferRepository.findById(1L);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void testUpdateAccountTransferEntity_ShouldUpdateAccountTransferEntity() {

        AccountTransferEntity existEntity = accountTransferRepository.findById(accountTransferEntity.getId()).get();

        existEntity.setAmount(BigDecimal.valueOf(15000.00));

        AccountTransferEntity savedEntity = accountTransferRepository.save(existEntity);

        assertThat(savedEntity.getAmount()).isEqualTo("15000.0");
    }

    @Test
    public void testSaveAccountTransferEntity_ShouldSaveAccountTransferEntity() {

        AccountTransferEntity newEntity = AccountTransferEntity.builder()
                .accountNumber(103050709L)
                .amount(BigDecimal.valueOf(1000.00))
                .purpose("Test2")
                .accountDetailsId(2L)
                .build();

        AccountTransferEntity savedEntity = accountTransferRepository.save(newEntity);

        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isNotNull();
        assertThat(savedEntity.getAccountNumber()).isEqualTo(103050709L);
        assertThat(savedEntity.getAmount()).isEqualTo("1000.0");
        assertThat(savedEntity.getPurpose()).isEqualTo("Test2");
        assertThat(savedEntity.getAccountDetailsId()).isEqualTo(2L);
    }
}

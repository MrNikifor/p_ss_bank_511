package com.bank.transfer.repository;

import com.bank.transfer.entity.PhoneTransferEntity;
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
public class PhoneTransferRepositoryTest {

    @Autowired
    private PhoneTransferRepository phoneTransferRepository;

    @Autowired
    private TestEntityManager entityManager;

    private PhoneTransferEntity phoneTransferEntity;

    @BeforeEach
    void setUp() {

        phoneTransferEntity = PhoneTransferEntity.builder()
                .phoneNumber(89059067788L)
                .amount(BigDecimal.valueOf(7000.00))
                .purpose("test1")
                .accountDetailsId(1L)
                .build();

        entityManager.persist(phoneTransferEntity);
        entityManager.flush();
    }

    @Test
    public void testFindById_ShouldReturnPhoneTransferEntity() {

        Optional<PhoneTransferEntity> result = phoneTransferRepository.findById(phoneTransferEntity.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getPhoneNumber()).isEqualTo(89059067788L);
        assertThat(result.get().getAmount()).isEqualTo("7000.0");
        assertThat(result.get().getPurpose()).isEqualTo("test1");
        assertThat(result.get().getAccountDetailsId()).isEqualTo(1L);
    }

    @Test
    public void testDeleteById_PhoneTransferEntityShouldNotBeFound() {

        phoneTransferRepository.deleteById(phoneTransferEntity.getId());

        Optional<PhoneTransferEntity> result = phoneTransferRepository.findById(phoneTransferEntity.getId());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void testUpdatePhoneTransferEntity_ShouldUpdatePhoneTransferEntity() {

        PhoneTransferEntity existEntity = phoneTransferRepository.findById(phoneTransferEntity.getId()).get();

        existEntity.setAmount(BigDecimal.valueOf(9100.00));

        PhoneTransferEntity savedEntity = phoneTransferRepository.save(existEntity);

        assertThat(savedEntity.getAmount()).isEqualTo("9100.0");
    }

    @Test
    public void testSavePhoneTransferEntity_ShouldSavePhoneTransferEntity() {

        PhoneTransferEntity newEntity = PhoneTransferEntity.builder()
                .phoneNumber(89077079988L)
                .amount(BigDecimal.valueOf(17000.00))
                .purpose("test2")
                .accountDetailsId(2L)
                .build();

        PhoneTransferEntity savedEntity = phoneTransferRepository.save(newEntity);

        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isNotNull();
        assertThat(savedEntity.getPhoneNumber()).isEqualTo(89077079988L);
        assertThat(savedEntity.getAmount()).isEqualTo("17000.0");
        assertThat(savedEntity.getPurpose()).isEqualTo("test2");
        assertThat(savedEntity.getAccountDetailsId()).isEqualTo(2L);
    }
}

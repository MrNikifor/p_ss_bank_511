package com.bank.transfer.service;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransferEntity;
import com.bank.transfer.exception.EntityAlreadyExistsException;
import com.bank.transfer.exception.EntityNotFoundException;
import com.bank.transfer.exception.RequiredFieldEmptyException;
import com.bank.transfer.exception.UniqueFieldEmptyException;
import com.bank.transfer.mapper.CardTransferMapper;
import com.bank.transfer.repository.CardTransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса {@link CardTransferService}.
 * Этот сервис обрабатывает операции, связанные с переводами по картам.
 */
@Slf4j
@Service
public class CardTransferServiceImpl implements CardTransferService {

    private final CardTransferRepository cardTransferRepository;

    private final CardTransferMapper cardTransferMapper = CardTransferMapper.INSTANCE;

    /**
     * Конструктор для создания нового экземпляра CardTransferServiceImpl с указанным репозиторием.
     *
     * @param cardTransferRepository репозиторий для выполнения операций с базой данных
     */
    @Autowired
    public CardTransferServiceImpl(CardTransferRepository cardTransferRepository) {
        this.cardTransferRepository = cardTransferRepository;
    }

    /**
     * Получает все переводы по картам.
     *
     * @return список {@link CardTransferDTO}, представляющий все переводы по картам
     */
    @Override
    public List<CardTransferDTO> getAllCardTransfers() {

        log.info("Getting all card transfers");

        List<CardTransferEntity> cardTransfersListEntity = cardTransferRepository.findAll();

        return cardTransferMapper.toDTOList(cardTransfersListEntity);
    }

    /**
     * Получает перевод по карте по его ID.
     *
     * @param id ID перевода по карте для получения
     * @return {@link CardTransferDTO}, представляющий перевод по карте
     * @throws EntityNotFoundException если перевод по карте с указанным ID не найден
     */
    @Override
    public CardTransferDTO getCardTransferById(Long id) {

        log.info("Getting card transfer by id: {}", id);

        CardTransferEntity cardTransferEntity = cardTransferRepository.findById(id)
                .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "An object with this ID was not found, ID: " + id,
                                    "CardTransferServiceImpl.getCardTransferById"));

        return cardTransferMapper.toDTO(cardTransferEntity);
    }

    /**
     * Создает новый перевод по карте.
     *
     * @param cardTransferDTO DTO, представляющий перевод по карте для создания
     * @return {@link CardTransferDTO}, представляющий созданный перевод по карте
     * @throws UniqueFieldEmptyException если номер карты пуст
     * @throws EntityAlreadyExistsException если перевод по карте с таким же номером карты уже существует
     * @throws RequiredFieldEmptyException если обязательные поля пусты
     */
    @Transactional
    @Override
    public CardTransferDTO createCardTransfer(CardTransferDTO cardTransferDTO) {

        log.info("Creating card transfer: {}", cardTransferDTO);

        if (cardTransferDTO.getCardNumber() == null) {

            throw new UniqueFieldEmptyException("An empty unique field for CardTransferEntity was passed",
                    "CardTransferServiceImpl.createCardTransfer");

        } else if (uniqueCardTransferEntity(cardTransferDTO) != null) {

            throw new EntityAlreadyExistsException("An object with the same card number already exists",
                    "CardTransferServiceImpl.createCardTransfer");

        } else if (cardTransferDTO.getAmount() == null || cardTransferDTO.getAccountDetailsId() == null) {

            throw new RequiredFieldEmptyException("An empty required field has been passed",
                    "CardTransferServiceImpl.createCardTransfer");
        }

        CardTransferEntity cardTransferEntity = cardTransferMapper.toEntity(cardTransferDTO);
        CardTransferEntity savedCardTransferEntity = cardTransferRepository.save(cardTransferEntity);

        return cardTransferMapper.toDTO(savedCardTransferEntity);
    }

    /**
     * Обновляет существующий перевод по карте.
     *
     * @param cardTransferDTO DTO, представляющий обновленный перевод по карте
     * @param id ID перевода по карте для обновления
     * @return {@link CardTransferDTO}, представляющий обновленный перевод по карте
     * @throws EntityNotFoundException если перевод по карте с указанным ID не найден
     * @throws EntityAlreadyExistsException если перевод по карте с таким же номером карты уже существует с другим ID
     */
    @Transactional
    @Override
    public CardTransferDTO updateCardTransfer(CardTransferDTO cardTransferDTO, Long id) {

        log.info("Updating card transfer: {}", cardTransferDTO);

        CardTransferEntity existingCardTransferEntity = cardTransferRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "An object with this ID was not found for editing, ID: " + id,
                                "CardTransferServiceImpl.updateCardTransfer"));

        CardTransferEntity checkUniqueEntity = uniqueCardTransferEntity(cardTransferDTO);

        if (checkUniqueEntity != null && !checkUniqueEntity.getId().equals(id)) {

            throw new EntityAlreadyExistsException(
                    "An object with the same card number already exists with a different ID",
                    "CardTransferServiceImpl.updateCardTransfer");
        }

        cardTransferMapper.updateEntityFromDTO(cardTransferDTO, existingCardTransferEntity);

        CardTransferEntity savedCardTransferEntity = cardTransferRepository.save(existingCardTransferEntity);

        return cardTransferMapper.toDTO(savedCardTransferEntity);
    }

    /**
     * Удаляет перевод по карте по его ID.
     *
     * @param id ID перевода по карте для удаления
     * @return {@link CardTransferDTO}, представляющий удаленный перевод по карте
     * @throws EntityNotFoundException если перевод по карте с указанным ID не найден
     */
    @Transactional
    @Override
    public CardTransferDTO deleteCardTransfer(Long id) {

        log.info("Deleting card transfer: {}", id);

        CardTransferEntity existsCardTransfer = cardTransferRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "An object with this ID was not found for deletion, ID: " + id,
                        "CardTransferServiceImpl.deleteCardTransfer"));

        cardTransferRepository.deleteById(id);

        return cardTransferMapper.toDTO(existsCardTransfer);
    }

    /**
     * Проверяет, существует ли перевод по карте с указанным номером карты.
     *
     * @param cardTransferDTO DTO, представляющий перевод по карте для проверки
     * @return существующий {@link CardTransferEntity}, если найден, иначе null
     */
    private CardTransferEntity uniqueCardTransferEntity(CardTransferDTO cardTransferDTO) {
        Optional<CardTransferEntity> cardTransferEntity =
                cardTransferRepository.findByCardNumber(cardTransferDTO.getCardNumber());

        return cardTransferEntity.orElse(null);
    }
}

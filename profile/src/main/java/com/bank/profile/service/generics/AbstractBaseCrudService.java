package com.bank.profile.service.generics;

import com.bank.profile.entity.abstracts.AbstractEntity;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.repository.generics.BaseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Slf4j
public abstract class AbstractBaseCrudService<
        ENTITY extends AbstractEntity,
        DTO,
        REPOSITORY extends BaseRepository<ENTITY>,
        MAPPER extends BaseMapper<ENTITY, DTO>> implements BaseCrudService<DTO> {

    protected final REPOSITORY repository;
    protected final MAPPER mapper;
    protected final DTO dto;

    @Override
    public List<DTO> readAll() {
        log.info("Service: Логирование чтения всех записей типа {}", dto.getClass().getSimpleName());
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public DTO read(Long id) {
        log.info("Service: Логирование чтения записи типа {} по id: {}", dto.getClass().getSimpleName(), id);
        return mapper.toDto(repository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public DTO create(DTO dto) {
        log.info("Service: Логирование создания записи {}", dto.getClass().getSimpleName());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public DTO update(DTO dto) {
        log.info("Service: Логирование изменения записи {}", dto.getClass().getSimpleName());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Service: Логирование удаления записи типа {} по id: {}", dto.getClass().getSimpleName(), id);
        repository.deleteById(id);
    }
}

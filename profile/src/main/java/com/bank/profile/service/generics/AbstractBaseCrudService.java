package com.bank.profile.service.generics;

import com.bank.profile.entity.abstracts.AbstractEntity;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.repository.generics.BaseRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@AllArgsConstructor
@Data
public abstract class AbstractBaseCrudService<
        ENTITY extends AbstractEntity,
        DTO,
        REPOSITORY extends BaseRepository<ENTITY>,
        MAPPER extends BaseMapper<ENTITY, DTO>> implements BaseCrudService<DTO> {

    protected final REPOSITORY repository;
    protected final MAPPER mapper;

    @Override
    public DTO read(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow());
    }

    @Override
    public List<DTO> readAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    @Transactional
    public DTO create(DTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public DTO update(DTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Class<ENTITY> getEntityType() {
        return (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}

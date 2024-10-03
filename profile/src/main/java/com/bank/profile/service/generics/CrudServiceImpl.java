package com.bank.profile.service.generics;

import com.bank.profile.mappers.BaseMapper;
import com.bank.profile.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrudServiceImpl<ENTITY, DTO, ID> implements CrudService<DTO> {

    private Map<Class<ENTITY>, BaseRepository<ENTITY, Long>> repositories;
    private Map<Class<ENTITY>, BaseMapper<ENTITY, DTO>> mappers;

    private Class<?> getEntityClass() {
        return (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    //CRUD

    @Override
    public DTO read(Long id) {
        BaseRepository<ENTITY, Long> repository = repositories.get(getEntityClass());
        BaseMapper<ENTITY, DTO> mapper = mappers.get(getEntityClass());
        return mapper.toDto(repository.findById(id).orElseThrow());
    }

    @Override
    public List<DTO> readAll() {
        BaseRepository<ENTITY, Long> repository = repositories.get(getEntityClass());
        BaseMapper<ENTITY, DTO> mapper = mappers.get(getEntityClass());
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    @Transactional
    public DTO create(DTO dto) {
        BaseRepository<ENTITY, Long> repository = repositories.get(getEntityClass());
        BaseMapper<ENTITY, DTO> mapper = mappers.get(getEntityClass());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public DTO update(DTO dto) {
        BaseRepository<ENTITY, Long> repository = repositories.get(getEntityClass());
        BaseMapper<ENTITY, DTO> mapper = mappers.get(getEntityClass());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        BaseRepository<ENTITY, Long> repository = repositories.get(getEntityClass());
        repository.deleteById(id);
    }
}

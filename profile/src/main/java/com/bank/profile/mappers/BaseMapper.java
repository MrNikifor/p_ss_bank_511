package com.bank.profile.mappers;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BaseMapper<ENTITY, DTO> {
    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

    List<DTO> toDtoList(List<ENTITY> entity);
}

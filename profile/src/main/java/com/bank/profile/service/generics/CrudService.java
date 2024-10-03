package com.bank.profile.service.generics;

import org.springframework.stereotype.Service;

import java.util.List;

public interface CrudService<DTO> {
    DTO create(DTO entity);
    DTO read(Long id);
    DTO update(DTO entity);
    void delete(Long id);
    List<DTO> readAll();
}

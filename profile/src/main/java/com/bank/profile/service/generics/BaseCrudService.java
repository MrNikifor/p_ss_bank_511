package com.bank.profile.service.generics;

import java.util.List;

public interface BaseCrudService<DTO> {
    DTO create(DTO entity);
    DTO read(Long id);
    DTO update(DTO entity);
    void delete(Long id);
    List<DTO> readAll();
}

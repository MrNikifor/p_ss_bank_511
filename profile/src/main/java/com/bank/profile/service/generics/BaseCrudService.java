package com.bank.profile.service.generics;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface BaseCrudService<DTO> {
    DTO create(DTO entity) throws ValidationException;
    DTO read(Long id);
    DTO update(DTO entity) throws ValidationException;
    void delete(Long id);
    List<DTO> readAll();
}

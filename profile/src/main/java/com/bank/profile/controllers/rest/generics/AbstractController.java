package com.bank.profile.controllers.rest.generics;

import com.bank.profile.entity.abstracts.AbstractEntity;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.service.generics.BaseCrudService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.ValidationException;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractController<
        ENTITY extends AbstractEntity,
        DTO,
        SERVICE extends BaseCrudService<DTO>,
        MAPPER extends BaseMapper<ENTITY, DTO>> implements BaseController<ENTITY,DTO> {

    protected final SERVICE service;
    protected final MAPPER mapper;

    @Override
    @GetMapping("")
    public ResponseEntity<List<DTO>> showAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/show")
    public ResponseEntity<DTO> showById(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(@RequestParam(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @PostMapping("/new")
    public ResponseEntity<HttpStatus> save(@RequestBody DTO dto) {
        try {
            service.create(dto);
        } catch (ValidationException e) {
            return new ResponseEntity("Ошибка при создании сущности: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @PostMapping("/update")
    public ResponseEntity<HttpStatus> edit(@RequestBody DTO dto) {
        try {
            service.update(dto);
        } catch (ValidationException e) {
            return new ResponseEntity("Ошибка при обновлении сущности: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

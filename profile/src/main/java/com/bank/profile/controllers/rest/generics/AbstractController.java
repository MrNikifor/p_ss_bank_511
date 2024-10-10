package com.bank.profile.controllers.rest.generics;

import com.bank.profile.entity.abstracts.AbstractEntity;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.service.generics.BaseCrudService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Slf4j
public abstract class AbstractController<
        ENTITY extends AbstractEntity,
        DTO,
        SERVICE extends BaseCrudService<DTO>,
        MAPPER extends BaseMapper<ENTITY, DTO>> implements BaseController<ENTITY, DTO> {

    protected final SERVICE service;
    protected final MAPPER mapper;
    protected final DTO dto;

    @Override
    @GetMapping("")
    public ResponseEntity<List<DTO>> showAll() {
        log.info("Controller: Логирование чтения всех записей типа {}", dto.getClass().getSimpleName());
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/show")
    public ResponseEntity<DTO> showById(@RequestParam(value = "id") Long id) {
        log.info("Controller: Логирование чтения записи типа {} по id: {}", dto.getClass().getSimpleName(), id);
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(@RequestParam(value = "id") Long id) {
        log.info("Controller: Логирование удаления записи типа {} по id: {}", dto.getClass().getSimpleName(), id);
        service.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @PostMapping("/new")
    public ResponseEntity<HttpStatus> save(@RequestBody DTO dto) {
        log.info("Controller: Логирование создания записи {}", dto.getClass().getSimpleName());
        service.create(dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @PostMapping("/update")
    public ResponseEntity<HttpStatus> edit(@RequestBody DTO dto) {
        log.info("Controller: Логирование изменения записи {}", dto.getClass().getSimpleName());
        service.update(dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

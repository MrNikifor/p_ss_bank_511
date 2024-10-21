package com.bank.profile.controllers.rest.generics;

import com.bank.profile.entity.abstracts.AbstractEntity;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.service.generics.BaseCrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
public abstract class AbstractController<ENTITY extends AbstractEntity, DTO, SERVICE extends BaseCrudService<DTO>, MAPPER extends BaseMapper<ENTITY, DTO>> implements BaseController<ENTITY, DTO> {

    protected final SERVICE service;
    protected final MAPPER mapper;

    @Override
    @GetMapping("")
    @Tag(name = "Получение всех записей", description = "Получение всех записей типа предпоследнего эндпоинта")
    public ResponseEntity<List<DTO>> showAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/show")
    @Tag(name = "Получение одной записи", description = "Получение одной конкретной записи типа предпоследнего эндпоинта (id указывается в параметрах get запроса)")
    public ResponseEntity<DTO> showById(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete")
    @Tag(name = "Удаление одной записи", description = "Удаление одной конкретной записи типа предпоследнего эндпоинта (id указывается в параметрах get запроса)")
    public ResponseEntity<HttpStatus> deleteById(@RequestParam(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @SneakyThrows
    @PostMapping("/new")
    @Tag(name = "Создание одной записи", description = "Создание одной конкретной записи типа предпоследнего эндпоинта (id не указывается)")
    public ResponseEntity<String> save(@RequestBody DTO dto) {
        service.create(dto);
        return ResponseEntity.ok("Успешно создано");
    }

    @Override
    @SneakyThrows
    @PutMapping("/update")
    @Tag(name = "Изменение одной записи", description = "Изменение одной конкретной записи типа предпоследнего эндпоинта (id указывается в теле post запроса)")
    public ResponseEntity<String> edit(@RequestBody DTO dto) {
        service.update(dto);
        return ResponseEntity.ok("Успешно обновлено");
    }
}

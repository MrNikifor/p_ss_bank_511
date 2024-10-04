package com.bank.profile.controllers.rest.generics;

import com.bank.profile.entity.AbstractEntity;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.service.generics.BaseCrudService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
public abstract class AbstractController<
        ENTITY extends AbstractEntity,
        DTO,
        SERVICE extends BaseCrudService<DTO>,
        MAPPER extends BaseMapper<ENTITY, DTO>> implements BaseController<ENTITY, DTO> {

    protected final SERVICE service;
    protected final MAPPER mapper;

    @Override
    public ResponseEntity<List<DTO>> showAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @Override
    @RequestMapping("/show")
    public ResponseEntity<DTO> showById(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    @Override
    @RequestMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(Long id) {
        service.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @RequestMapping("/new")
    public ResponseEntity<HttpStatus> save(DTO dto) {
        service.create(dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @RequestMapping("/update")
    public ResponseEntity<HttpStatus> edit(DTO dto) {
        service.update(dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

package com.bank.profile.controllers.rest.generics;

import com.bank.profile.entity.abstracts.AbstractEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BaseController<ENTITY extends AbstractEntity, DTO> {

    @GetMapping
    ResponseEntity<List<DTO>> showAll();

    @GetMapping
    ResponseEntity<DTO> showById(@RequestParam(value = "id") Long id);

    @GetMapping
    ResponseEntity<HttpStatus> deleteById(@RequestParam(value = "id") Long id);

    @PostMapping
    ResponseEntity<HttpStatus> save(@RequestBody DTO dto);

    @PostMapping
    ResponseEntity<HttpStatus> edit(@RequestBody DTO dto);

}

package com.bank.history.service;

import com.bank.history.dto.HistoryDTO;

import java.util.List;

public interface HistoryService {

    Long add(HistoryDTO history);

    HistoryDTO getById(Long id);

    void update(HistoryDTO history);

    void deleteById(Long id);

    List<HistoryDTO> findAll();
}

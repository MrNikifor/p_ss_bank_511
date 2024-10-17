package com.bank.history.service;

import com.bank.history.dto.HistoryDTO;
import com.bank.history.entity.History;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository repository;
    private final HistoryMapper historyMapper;

    @Override
    @Transactional
    public Long add(HistoryDTO history) {
        return repository.save(historyMapper.map(history)).getId();
    }

    @Override
    @Transactional
    public HistoryDTO getById(Long id) {
        History history = repository.findById(id).orElseThrow(() -> new HistoryNotFoundException(id));
        return historyMapper.map(history);
    }

    @Override
    @Transactional
    public void update(HistoryDTO history) {
        repository.save(historyMapper.map(history));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<HistoryDTO> findAll() {
        List<History> histories = repository.findAll();
        return histories.stream()
                .map(historyMapper::map)
                .collect(Collectors.toList());
    }
}

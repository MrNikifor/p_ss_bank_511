package service;

import com.bank.history.dto.HistoryDTO;
import com.bank.history.entity.History;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import com.bank.history.service.HistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {

    @Mock
    private HistoryRepository repository;

    @Mock
    private HistoryMapper historyMapper;

    @InjectMocks
    private HistoryServiceImpl historyService;

    private HistoryDTO historyDTO;
    private History history;

    @BeforeEach
    public void setUp() {
        historyDTO = new HistoryDTO();
        history = new History();
        history.setId(1L);
    }

    @Test
    public void testAdd() {
        when(historyMapper.map(historyDTO)).thenReturn(history);
        when(repository.save(history)).thenReturn(history);

        Long id = historyService.add(historyDTO);

        assertEquals(1L, id);
        verify(repository).save(history);
    }

    @Test
    public void testGetById_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(history));
        when(historyMapper.map(history)).thenReturn(historyDTO);

        HistoryDTO result = historyService.getById(1L);

        assertNotNull(result);
        assertEquals(historyDTO, result);
    }

    @Test
    public void testGetById_NotFound() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(HistoryNotFoundException.class, () -> {
            historyService.getById(id);
        });

        assertEquals("History not found with id: " + id, exception.getMessage());
        verify(repository).findById(id);
    }

    @Test
    public void testUpdate() {
        when(historyMapper.map(historyDTO)).thenReturn(history);

        historyService.update(historyDTO);

        verify(repository).save(history);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(repository).deleteById(1L);

        historyService.deleteById(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    public void testFindAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(history));
        when(historyMapper.map(history)).thenReturn(historyDTO);

        List<HistoryDTO> result = historyService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}

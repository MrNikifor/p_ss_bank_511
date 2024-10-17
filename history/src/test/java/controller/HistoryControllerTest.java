package controller;

import com.bank.history.HistoryApplication;
import com.bank.history.controller.HistoryController;
import com.bank.history.dto.HistoryDTO;
import com.bank.history.service.HistoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HistoryController.class)
@ContextConfiguration(classes = HistoryApplication.class)
public class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoryServiceImpl historyService;

    private ObjectMapper objectMapper;
    private HistoryDTO historyDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        historyDTO = new HistoryDTO();
        historyDTO.setId(1L);
    }

    @Test
    void addHistoryShouldReturnCreated() throws Exception {
        Long expectedId = 1L;

        when(historyService.add(Mockito.any(HistoryDTO.class))).thenReturn(expectedId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedId.toString()));
    }

    @Test
    public void getHistoryById_ShouldReturnOk() throws Exception {
        when(historyService.getById(1L)).thenReturn(historyDTO);

        mockMvc.perform(get("/api/history/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(historyDTO)));
    }

    @Test
    public void updateHistory_ShouldReturnOk() throws Exception {
        mockMvc.perform(put("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(historyDTO)))
                .andExpect(status().isOk());

        verify(historyService).update(historyDTO);
    }

    @Test
    public void deleteHistoryById_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/history/1"))
                .andExpect(status().isNoContent());

        verify(historyService).deleteById(1L);
    }

    @Test
    public void getAllHistories_ShouldReturnOk() throws Exception {
        List<HistoryDTO> histories = Arrays.asList(new HistoryDTO(), new HistoryDTO());
        when(historyService.findAll()).thenReturn(histories);

        mockMvc.perform(get("/api/history"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(histories)));
    }
}

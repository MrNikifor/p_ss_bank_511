package com.bank.history.mapper;

import com.bank.history.dto.HistoryDTO;
import com.bank.history.entity.History;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    HistoryDTO map(History history);

    History map(HistoryDTO historyDTO);
}

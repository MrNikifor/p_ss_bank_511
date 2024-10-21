package com.bank.history.dto;

import lombok.Data;

@Data
public class HistoryDTO {

    private long id;
    private long transferAuditId;
    private long profileAuditId;
    private long accountAuditId;
    private long antiFraudAuditId;
    private long publicBankInfoAuditId;
    private long authorizationAuditId;
}

package com.bank.history.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "history", schema = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "transfer_audit_id")
    private long transferAuditId;

    @Column(name = "profile_audit_id")
    private long profileAuditId;

    @Column(name = "account_audit_id")
    private long accountAuditId;

    @Column(name = "anti_fraud_audit_id")
    private long antiFraudAuditId;

    @Column(name = "public_bank_info_audit_id")
    private long publicBankInfoAuditId;

    @Column(name = "authorization_audit_id")
    private long authorizationAuditId;

    public History() {
    }

    public History(long id,
                   long transferAuditId,
                   long profileAuditId,
                   long accountAuditId,
                   long antiFraudAuditId,
                   long publicBankInfoAuditId,
                   long authorizationAuditId) {
        this.id = id;
        this.transferAuditId = transferAuditId;
        this.profileAuditId = profileAuditId;
        this.accountAuditId = accountAuditId;
        this.antiFraudAuditId = antiFraudAuditId;
        this.publicBankInfoAuditId = publicBankInfoAuditId;
        this.authorizationAuditId = authorizationAuditId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTransferAuditId() {
        return transferAuditId;
    }

    public void setTransferAuditId(long transferAuditId) {
        this.transferAuditId = transferAuditId;
    }

    public long getProfileAuditId() {
        return profileAuditId;
    }

    public void setProfileAuditId(long profileAuditId) {
        this.profileAuditId = profileAuditId;
    }

    public long getAccountAuditId() {
        return accountAuditId;
    }

    public void setAccountAuditId(long accountAuditId) {
        this.accountAuditId = accountAuditId;
    }

    public long getAntiFraudAuditId() {
        return antiFraudAuditId;
    }

    public void setAntiFraudAuditId(long antiFraudAuditId) {
        this.antiFraudAuditId = antiFraudAuditId;
    }

    public long getPublicBankInfoAuditId() {
        return publicBankInfoAuditId;
    }

    public void setPublicBankInfoAuditId(long publicBankInfoAuditId) {
        this.publicBankInfoAuditId = publicBankInfoAuditId;
    }

    public long getAuthorizationAuditId() {
        return authorizationAuditId;
    }

    public void setAuthorizationAuditId(long authorizationAuditId) {
        this.authorizationAuditId = authorizationAuditId;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", transferAuditId=" + transferAuditId +
                ", profileAuditId=" + profileAuditId +
                ", accountAuditId=" + accountAuditId +
                ", antiFraudAuditId=" + antiFraudAuditId +
                ", publicBankInfoAuditId=" + publicBankInfoAuditId +
                ", authorizationAuditId=" + authorizationAuditId +
                '}';
    }
}

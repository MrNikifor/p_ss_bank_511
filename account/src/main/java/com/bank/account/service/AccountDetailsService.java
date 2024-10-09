package com.bank.account.service;

import com.bank.account.entity.AccountDetails;

import java.util.List;

public interface AccountDetailsService {
    AccountDetails saveAccountDetails(AccountDetails accountDetails);
    AccountDetails updateAccountDetails(AccountDetails accountDetails);
    AccountDetails getAccountDetails(Long id);
    List<AccountDetails> getAllAccountDetails();
    void deleteAccountDetails(AccountDetails accountDetails);
}

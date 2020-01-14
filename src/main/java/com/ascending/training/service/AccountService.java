/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.service;

import com.ascending.training.model.Account;

import java.util.List;

public interface AccountService {
    boolean save(Account account, String employeeName);
    List<Account> getAccounts();
    Account getAccountById(int id);
    public void transferMoney(int accountFrom, int accountTo, double totalMoney);
}

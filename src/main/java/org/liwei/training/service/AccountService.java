/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.service;

import org.liwei.training.model.Account;
import org.liwei.training.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired private AccountDao accountDao;

    public boolean save(Account account, String employeeName) {
        return accountDao.save(account, employeeName);
    }

    public List<Account> getAccounts() {
        return accountDao.getAccounts();
    }

    public Account getAccountById(int id) {
        return accountDao.getAccountById(id);
    }
}

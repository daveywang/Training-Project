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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/* SCOPE_SINGLETON is default scope, it can be omitted */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccountServiceImpl implements AccountService {
    //@Autowired
    private AccountDao accountDao;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public boolean save(Account account, String employeeName) {
        return accountDao.save(account, employeeName);
    }

    @Override
    public List<Account> getAccounts() {
        return accountDao.getAccounts();
    }

    @Override
    public Account getAccountById(int id) {
        return accountDao.getAccountById(id);
    }

    @Override
    public void transferMoney(int accountFrom, int accountTo, double totalMoney) {
        accountDao.transferMoney(accountFrom, accountTo, totalMoney);
    }
}

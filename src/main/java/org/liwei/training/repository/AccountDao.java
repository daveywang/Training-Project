/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.model.Account;

import java.util.List;

public interface AccountDao {
    boolean save(Account account, String employeeName);
    List<Account> getAccounts();
    Account getAccountById(int id);
    public void transferMoney(int accountFrom, int accountTo, double totalMoney);
}

/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.repository;

import org.liwei.training.model.Account;
import org.liwei.training.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AccountDaoTest {
    private AccountDao accountDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void init() {
        accountDao = new AccountDaoImpl();
    }

    @Test
    public void getAccounts() {
        List<Account> accounts = accountDao.getAccounts();
        int expectedNumOfDept = 6;

        for (Account account : accounts) {
            System.out.println(account);
        }

        Assert.assertEquals(expectedNumOfDept, accounts.size());
    }

    @Test
    public void getEmployeeById() {
        int id = 2;
        Account account = accountDao.getAccountById(id);
        Assert.assertEquals(id, account.getId());
        logger.debug(account.toString());
        Employee employee = account.getEmployee();
        logger.debug(employee.getEmail());
    }
}
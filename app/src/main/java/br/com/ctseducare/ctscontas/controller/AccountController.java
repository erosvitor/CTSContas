package br.com.ctseducare.ctscontas.controller;

import android.content.Context;

import java.util.List;

import br.com.ctseducare.ctscontas.dao.AccountDao;
import br.com.ctseducare.ctscontas.model.Account;
import br.com.ctseducare.ctscontas.dto.AccountDTO;

public class AccountController {

    private AccountDao dao;

    public AccountController(Context context) {
        this.dao = new AccountDao(context);
    }

    public void insertOrUpdate(Account account) {
        dao.insertOrUpdate(account);
    }

    public List<AccountDTO> list() {
        return dao.list();
   }

}

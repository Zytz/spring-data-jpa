package com.john.service;

import com.john.dao.AccountDao;
import com.john.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author:wenwei
 * @date:2020/03/15
 * @description:
 */
@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    /**
     * 是否允许登录
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username,String password){
        Account account = new Account();
        account.setUsername(username);
        Example example = Example.of(account);
        Optional<Account> optionalAccount = accountDao.findOne(example);
       if(optionalAccount.isPresent()){
           Account localAccount = optionalAccount.get();
           if(localAccount.getPassword().equals(password)){
               return true;
           }
       }
       return false;

    }


    public boolean checkToken(String token) {
        Account account = new Account();
        account.setUsername(token);
        Example example = Example.of(account);
        Optional<Account> optionalAccount = accountDao.findOne(example);
        if(optionalAccount.isPresent()){
         return true;
        }
        return false;
    }
}

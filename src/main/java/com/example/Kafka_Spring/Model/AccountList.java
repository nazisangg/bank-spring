package com.example.Kafka_Spring.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountList {

    private static AccountList instance;
    private AccountList (){
        this.accountMap = new HashMap<>();
    }

    public static synchronized AccountList getInstance() {
        if (instance == null) {
            instance = new AccountList();
        }
        return instance;
    }

    private HashMap<String, Account> accountMap;


    public HashMap<String, Account> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(HashMap<String, Account> accountMap) {
        this.accountMap = accountMap;
    }

    public void addNewAccount(Account account){
        String name = account.getName();
        this.accountMap.put(name, account);
    }

    public void deleteAccount(String name){
        this.accountMap.remove(name);
    }

    public List<String> getNameList(){
        return new ArrayList<String>(this.accountMap.keySet());
    }

    public Boolean ifAccountExist(String name){
        return this.getNameList().contains(name);
    }
}

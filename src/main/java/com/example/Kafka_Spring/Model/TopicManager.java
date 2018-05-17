package com.example.Kafka_Spring.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TopicManager {
    private static TopicManager instance;
    private TopicManager (){
        this.topicInformationMap = new HashMap<>();
    }

    public static synchronized TopicManager getInstance() {
        if (instance == null) {
            instance = new TopicManager();
        }
        return instance;
    }

    private HashMap<String, TopicInformation> topicInformationMap;



    public HashMap<String, TopicInformation> getAccountMap() {
        return topicInformationMap;
    }

    public void setAccountMap(HashMap<String, TopicInformation> accountMap) {
        this.topicInformationMap = accountMap;
    }

    public void addNewAccount(TopicInformation topicInformation){
        String name = topicInformation.getName();
        this.topicInformationMap.put(name, topicInformation);
    }

    public void deleteAccount(String name){
        this.topicInformationMap.remove(name);
    }

    public List<String> getNameList(){
        return new ArrayList<String>(this.topicInformationMap.keySet());
    }

    public Boolean ifAccountExist(String name){
        return this.getNameList().contains(name);
    }
}

package ru.yalabuniversity.homework.lecture3.datedmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap{
    private Map<String, String> keyValueMap;
    private Map<String, Date> keyDateMap;

    public DatedMapImpl() {
        keyValueMap = new HashMap<>();
        keyDateMap = new HashMap<>();
    }
    @Override
    public void put(String key, String value) {
        Date date = new Date();
        keyValueMap.put(key, value);
        keyDateMap.put(key, date);
    }

    @Override
    public String get(String key) {
        return keyValueMap.getOrDefault(key, null);
    }

    @Override
    public boolean containsKey(String key) {
        return keyValueMap.containsKey(key);
    }

    @Override
    public void remove(String key) {
        keyValueMap.remove(key);
        keyDateMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return keyValueMap.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return keyDateMap.getOrDefault(key, null);
    }
}

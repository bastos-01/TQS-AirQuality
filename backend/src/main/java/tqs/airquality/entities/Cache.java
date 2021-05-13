package tqs.airquality.entities;

import java.util.HashMap;
import java.util.Map;

public class Cache {

    private int hits = 0;
    private int misses = 0;
    private int requests = 0;

    private Map<String, City> cacheMap = new HashMap<>();
    private Map<String, Long> timeToLive = new HashMap<>();

    private long cleanTime;


    public Cache(int cleanTime) {
        this.cleanTime = cleanTime;
        cleaningByTime();
    }

    public int getHits() {
        return hits;
    }


    public int getMisses() {
        return misses;
    }

    public int getRequests() {
        return requests;
    }

    public boolean addValue(String key, City city){
        long maxTime = System.currentTimeMillis() + this.cleanTime * 1000;
        timeToLive.put(key, maxTime);
        cacheMap.put(key, city);
        return true;
    }

    public boolean deleteValue(String key){
        if(cacheMap.containsKey(key)){
            cacheMap.remove(key);
            timeToLive.remove(key);
            return true;
        }
        return false;
    }

    public City getCityFromCache(String key){
        if(cacheMap.containsKey(key) && timeToLive.get(key) > System.currentTimeMillis()){
            this.hits++;
            this.requests++;
            return cacheMap.get(key);
        }
        this.requests++;
        this.misses++;
        return null;
    }

    public Thread cleaningByTime(){
        Thread thread = new Thread(){
            @Override
            public void run(){
                while (true){

                    for(String key: cacheMap.keySet()){
                        if(timeToLive.get(key) < System.currentTimeMillis()){
                            deleteValue(key);
                        }
                    }
                    try {
                        Thread.sleep(cleanTime * 1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }


                }
            }
        };
        thread.start();
        return thread;
    }

    public void clearCache(){
        this.cacheMap.clear();
        this.timeToLive.clear();
    }

    public int getCacheSize(){
       return this.cacheMap.size();
    }

    public boolean containsCity(String key){
        return this.cacheMap.containsKey(key);
    }

}

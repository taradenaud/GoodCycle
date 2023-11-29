package com.example.seg2105;

public class awardlistitem {
    private String name;
    private String awardName;
    private String awardDetails;
    private String results;
    private String EventName;

    public awardlistitem ( String name, String awardName,String awardDetails, String results, String eventName){
        this.name = name;
        this.awardName = awardName;
        this.awardDetails = awardDetails;
        this.results = results;
        this.EventName = eventName;
    }

    public String getName() {
        return name;
    }

    public String getAwardName() {
        return awardName;
    }

    public String getAwardDetails() {
        return awardDetails;
    }
    public String getResults() {
        return results;
    }

    public String getEventName(){
        return EventName;
    }

}


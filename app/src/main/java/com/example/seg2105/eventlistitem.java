package com.example.seg2105;

public class eventlistitem {
        private String name;
        private String type;
        private String location;
        private String level;
        private String pace;
        private String participantLimit;
        private String regfee;
        private String clubusrname;

        public eventlistitem ( String name, String type, String location, String level, String pace, Integer participantLimit, String regfee, String ClubusrName){
            this.name = name;
            this.type = type;
            this.location = location;
            this.level = "Level: "+level;
            this.pace = "Pace: "+pace;
            this.participantLimit = "Participant Limit: "+String.valueOf(participantLimit);
            this.regfee = "Registration Fee: "+regfee;
            //this.clubusrname = clubusrname;
        }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getLevel() {
        return level;
    }

    public String getPace() {
        return pace;
    }

    public String getParticipantLimit() {
        return participantLimit;
    }

    public String getRegfee() {
        return regfee;
    }

    public String getClubusrname(){return clubusrname;}

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setPace(String pace) {
        this.pace = pace;
    }

    public void setParticipantLimit(String participantLimit) {
        this.participantLimit = participantLimit;
    }

    public void setRegfee(String regfee) {
        this.regfee = regfee;
    }

    public void setClubusrname(String clubusrname) {
        this.clubusrname = clubusrname;
    }
}

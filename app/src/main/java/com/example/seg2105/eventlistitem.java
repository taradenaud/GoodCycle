package com.example.seg2105;

public class eventlistitem {
        private String name;
        private String type;
        private String location;
        private String level;
        private String pace;
        private String participantLimit;
        private String regfee;

        public eventlistitem ( String name, String type, String location, String level, String pace, String participantLimit, String regfee){
            this.name = name;
            this.type = type;
            this.location = location;
            this.level = "Level: "+level;
            this.pace = "Pace: "+pace;
            this.participantLimit = "Participant Limit: "+participantLimit;
            this.regfee = "Registration Fee: "+regfee;
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
}

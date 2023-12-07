package com.example.seg2105;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    eventlistitem event1 = new eventlistitem("Winter Race", "Group Ride", "The Glebe", "7", "10km/h", 20, "0$", "gccadmin");
    ArrayList<String> types = new ArrayList<>();

    @Test
    public void testSignUpParticipant(){
        SignUp.regAccount("cenafan2000", "john@hotmail.com", "easypassword123","Participant");
    }

    @Test
    public void testSignUpAdmin(){
        SignUp.regAccount("undertaker", "something@gmail.com", "password","Admin");
    }

    @Test
    public void testSignUpClubOwner(){
        SignUp.regAccount("reymysterio", "oscar@gmail.com", "easierpassword12","Club Owner", "Cycling Smackdown");
    }

    @Test
    public void testrate(){
        ClubRatingPage.rate(4.5F, "Great environement super welcoming to begginers", "gccadmin");
    }

    @Test
    public void TestdeleteUser(){
        AdminManageUsers.delete("mattl");
    }

    @Test
    public void TestdeleteEvent() {
        Admin_events.deleteEvent("gccadmin","Fun");
    }

    @Test
    public void testCreateEvent() {
        eventCreation.CreateEvent("gccadmin","Winter Race", "Group Ride", "The Glebe", "7", "10km/h",  "0$", 20);
    }

    @Test
    public void testModifyEvent() {
        Admin_events.changeEventDetail("gccadmin","December Snow Ride","level", "6");
    }

    @Test
    public void testRegisterEvent(){
        Event_Discovery.Register("gccadmin","mattl","December Snow Ride");
    }

    @Test
    public void testaddEventType(){
        AdminEventTypes.AddEventType("Solo Rides");
    }

    @Test
    public void testChangeEventType(){
        AdminEventTypes.AlterEventType("Group Rides", "Solo Rides" );
    }


}
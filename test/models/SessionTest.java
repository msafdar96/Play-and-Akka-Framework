package models;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases for model class Session
 * @author group
 */
public class SessionTest {

    Session session;
    Search search;
    String sessionID;
    String keyword;

    @Before
    public void setup(){
        session = new Session();
        this.setupMockData();
    }

    public void setupMockData(){
        sessionID = "userID123";
        keyword = "react";
        search = new Search(keyword);
    }

    @Test
    public void session(){
        session.setSession(sessionID, search);
        assertTrue(session.getSession(sessionID)!=null);
        assertEquals(session.getSession(sessionID),search);
    }

}

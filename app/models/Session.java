package models;

import java.util.HashMap;
/**
 * Model class for session details and the search class object for maintaining sessions
 * @author group
 */
public class Session {
    private HashMap<String,Search> sessions = new HashMap<String,Search>();

    /**
     * this is a default constructor
     */
    public Session(){

    }

    /**
     * This method is used to put the session and search variable together
     * @param session
     * @param search
     */
    public void setSession(String session, Search search){
        sessions.put(session, search);
    }

    /**
     *This method returns the search class object for the given session
     * @param session
     * @return search class object
     */
    public Search getSession(String session){
        return sessions.get(session);
    }



}

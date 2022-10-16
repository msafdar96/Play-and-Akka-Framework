package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for model class Project
 * @author Anusha Reddy
 */
public class UserTest {
    User user;
    String name;
    List<Project> userProject;
    String role;
    String language;
    String hourly_rate;
    String registration_date;
    String limited_account;
    String display_name;
    String chosen_role;
    List<String> userLocation;
    String country;
    String currency;
    String email;
    String ownerID;
    String previewDescription;
    String title;
    String type;
    Date timeSubmitted;
    List<String> skills;
    ObjectMapper map;
    JsonNode details;
    JsonNode skillsN = null;
    JsonNode projects = null;
    JsonNode project1 = null;
    JsonNode project2 = null;



    @Before
    public void setup(){
        this.setupMockData();
        user = new User();
        user = new User(name, userProject, role, hourly_rate, language,
                registration_date, limited_account, display_name, chosen_role,
                userLocation, country, currency, email);
        map = new ObjectMapper();
        details = map.convertValue(user, JsonNode.class);
    }

    public void setupMockData(){
        ownerID = "23232323";
        previewDescription = "need to login";
        title = "Java Fx Basic Task";
        type = "fixed";
        timeSubmitted = new Date();
        skills = Arrays.asList("C++","Java");
        name = "John";
        userProject = Arrays.asList(new Project(ownerID, previewDescription,
                timeSubmitted, title, type, skills));
        role = "employer";
        hourly_rate = "10.5";
        language = "en";
        registration_date = new Date().toString();
        limited_account = "1000";
        display_name = "John234";
        chosen_role = "employee";
        userLocation = Arrays.asList("Montreal");
        country = "Canada";
        currency = "CAD";
        email = "test@test.com";
        skillsN = Json.newArray()
                .add(Json.newObject().put("name","React.js"))
                .add(Json.newObject().put("name","Node.js"))
                .add(Json.newObject().put("name","JavaScript"));
        project1 = Json.newObject()
                .put("owner_id", 2487292)
                .put("time_submitted", 1647732665)
                .put("title", "NFT App Install for NFT minting website")
                .put("type", "Fixed")
                .put("preview_description","I need to install a landing page and minting app (React) to create an NFT minting website.")
                .set("jobs",skillsN);
        project2 = Json.newObject()
                .put("owner_id", 2487293)
                .put("time_submitted", 1647732665)
                .put("title", "This is a test")
                .put("type", "Fixed")
                .put("preview_description","This is a test. Need a developer urgently! " +
                        "Answer following questions before bidding: 1) Do you have experience in this field?" +
                        "2) How good you are in coding?")
                .set("jobs",skillsN);

        projects = Json.newArray()
                .add(project1)
                .add(project2);
    }

    @Test
    public void name(){
        user.setName(name);
        assertTrue(user.getName()!=null);
        assertEquals(user.getName(),name);
    }

    @Test
    public void role(){
        user.setRole(role);
        assertTrue(user.getRole()!=null);
        assertEquals(user.getRole(), role);
    }

    @Test
    public void hourly_rate(){
        user.setHourly_rate(hourly_rate);
        assertTrue(user.getHourly_rate()!=null);
        assertEquals(user.getHourly_rate(), hourly_rate);
    }

    @Test
    public void chosen_role(){
        user.setChosen_role(chosen_role);
        assertTrue(user.getChosen_role()!=null);
        assertEquals(user.getChosen_role(), chosen_role);
    }

    @Test
    public void registration_date(){
        user.setRegistration_date(registration_date);
        assertTrue(user.getRegistration_date()!=null);
        assertEquals(user.getRegistration_date(), registration_date);
    }

    @Test
    public void display_name(){
        user.setDisplay_name(display_name);
        assertTrue(user.getDisplay_name()!=null);
        assertEquals(user.getDisplay_name(), display_name);
    }

    @Test
    public void limited_account(){
        user.setLimited_account(limited_account);
        assertTrue(user.getLimited_account()!=null);
        assertEquals(user.getLimited_account(), limited_account);
    }

    @Test
    public void country(){
        user.setCountry(country);
        assertTrue(user.getCountry()!=null);
        assertEquals(user.getCountry(), country);
    }

    @Test
    public void currency(){
        user.setCurrency(currency);
        assertTrue(user.getCurrency()!=null);
        assertEquals(user.getCurrency(), currency);
    }

    @Test
    public void email(){
        user.setEmail(email);
        assertTrue(user.getEmail()!=null);
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void userProject(){
        assertTrue(user.getUserProject()!=null);
        assertEquals(user.getUserProject(), userProject);
    }

    @Test
    public void userLocation(){
        user.setUserLocation(userLocation);
        assertTrue(user.getUserLocation()!=null);
        assertEquals(user.getUserLocation(), userLocation);
    }

    @Test
    public void details(){
       user.setDetails(details);
    }

    @Test
    public void saveProjects(){
       List<Project> savedProjects =  user.saveProjects(ownerID, projects);
       assertTrue(savedProjects.size()>0);
       assertEquals(savedProjects.get(0).getOwnerID(),"2487292");
    }






}

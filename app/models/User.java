package models;

import com.fasterxml.jackson.databind.JsonNode;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * User class uses for storing user information.
 * @author Anusha Reddy
 */
public class User {

    private String name;
    private List<Project> userProject = new ArrayList<>();
    private String role;
    private String language;
    private String hourly_rate;
    private String registration_date;
    private String limited_account;
    private String display_name;
    private String chosen_role;
    private List<String> userLocation = new ArrayList<>();
    private LinkedHashMap<String, String> userStatus = new LinkedHashMap<String,String>();
    private String country;
    private String currency;
    private String email;



    public User(){
    }

    /**
     * This is a constructor to initialize user object
     * @param name an initialize user name
     * @param userProject initialize user projects
     * @param role initialize the user role
     * @param hourly_rate initialize hourly rate
     * @param language initialize the language
     * @param registration_date initialize the registration date
     * @param limited_account initialize the limited account
     * @param display_name initialize the display name
     * @param chosen_role initialize the chosen role
     * @param userLocation initialize the userLocation.
     * @param country initialize the country of the user
     * @param currency initialize the currency of the user.
     * @param email initialize the email of the user.
     */
    public User(String name, List<Project> userProject, String role, String hourly_rate, String language, String registration_date, String limited_account, String display_name,String chosen_role, List<String> userLocation, String country, String currency, String email ) {
        this.name = name;
        this.userProject = userProject;
        this.role = role;
        this.hourly_rate = hourly_rate;
        this.language = language;
        this.registration_date = registration_date;
        this.chosen_role = chosen_role;
        this.limited_account = limited_account;
        this.display_name = display_name;
        this.userLocation = userLocation;
        this.country = country;
        this.currency = currency;
        this.email = email;

    }

    /**
     * get person name
     * @return person name
     */
    public String getName() {
        return name.replace('\"', ' ');
    }

    /**
     * To set a name of the user
     * @param name a new username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get user role
     * @return user role
     */
    public String getRole(){
        return role.replace('\"', ' ');
    }

    /**
     * To set the role of the user
     * @param role of user
     */
    public void setRole(String role){
        this.role = role;
    }

    /**
     * get hourly rate of user
     * @return hourly rate
     */
    public String getHourly_rate(){
        return hourly_rate.replace('\"', ' ');
    }

    /**
     * To set hourly rate for the user
     * @param hourly_rate
     */
    public void setHourly_rate(String hourly_rate){
        this.hourly_rate = hourly_rate;
    }

    /**
     * get chosen role of the user
     * @return chosen role
     */
    public String getChosen_role(){
        return chosen_role.replace('\"', ' ');
    }

    /**
     * To set chosen role of the user
     * @param chosen_role of the user
     */
    public void setChosen_role(String chosen_role){
        this.chosen_role = chosen_role;
    }

    /**
     * get the registration date of the user.
     * @return registration date
     */
    public String getRegistration_date(){
        return registration_date.replace('\"', ' ');
    }

    /**
     * set the registration date of the user
     * @param registration_date
     */
    public void setRegistration_date(String registration_date){
        this.registration_date = registration_date;
    }

    /**
     * get the display name of the user
     * @return display name
     */
    public String getDisplay_name(){
        return display_name.replace('\"', ' ');
    }

    /**
     * set the display name of the user
     * @param display_name
     */
    public void setDisplay_name(String display_name){
        this.display_name = display_name;
    }

    /**
     * get the limited account of the user
     * @return limited account details
     */
    public String getLimited_account(){
        return limited_account.replace('\"', ' ');
    }

    /**
     * set the limited account of the user
     * @param limited_account
     */
    public void setLimited_account(String limited_account){
        this.limited_account = limited_account;
    }

    /**
     * get the country of the user
     * @return country name details
     */


    public String getCountry(){
        return country.replace('\"', ' ');
    }

    /**
     * set the country name of the user
     * @param country
     */
    public void setCountry(String country)
    {
        this.country = country;
    }

    /**
     * get the currency details of the user
     * @return currency
     */

    public String getCurrency()
    {
        return currency.replace('\"', ' ');
    }

    /**
     * set the currency details of the user
     * @param currency
     */
    public void setCurrency(String currency){
        this.currency = currency;
    }

    /**
     * get the Email status of the user
     * @return email status
     */
    public String getEmail(){
        return email;
    }

    /**
     * set the email status of the user
     * @param email
     */
    public void setEmail(String email){
        this.email = email;
    }
    /**
     * get the list of the projects of the user
     * @return list of the projects
     */
    public List<Project> getUserProject() {
        return userProject;
    }

    /**
     * get the location of the user
     * @return location of user
     */
    public List<String> getUserLocation(){
        return userLocation;
    }

    /**
     * set the user's location
     * @param userLocation
     */
    public void setUserLocation(List<String> userLocation){
        this.userLocation = userLocation;
    }

    /**
     * set the user details of the user
     * @param user_details
     */
    public void setDetails(JsonNode user_details){
        name = user_details.get("username")!=null?user_details.get("username").asText():"";
        role = user_details.get("role")!=null?user_details.get("role").asText():"";
        hourly_rate = user_details.get("hourly_rate")!=null?user_details.get("hourly_rate").toString():"";
        display_name = user_details.get("display_name")!=null?user_details.get("display_name").toString():"";
        limited_account = user_details.get("limited_account")!=null?user_details.get("limited_account").toString():"";
        registration_date = user_details.get("registration_date")!=null?user_details.get("registration_date").toString():"";
        chosen_role = user_details.get("chosen_role")!=null?user_details.get("chosen_role").toString():"";
        country = user_details.get("location")!=null?user_details.get("location").get("country").get("name").toString():"";
        currency = user_details.get("primary_currency")!=null?user_details.get("primary_currency").get("name").toString():"";
        email = user_details.get("status")!=null?user_details.get("status").get("email_verified").toString():"";
    }

    /**
     * save projects details of the user
     * @param id of the user
     * @param projects of the user.
     */
    public List<Project> saveProjects(String id, JsonNode projects){
        List<Project> userproject = StreamSupport
                .stream(projects.spliterator(),true)
                .parallel()
                .map(e->{
                    String projectId = e.get("id").toString();
                    String ownerID = e.get("owner_id").toString();
                    Date timeSubmitted = null;
                    timeSubmitted = new Date(e.get("time_submitted").asLong());
                    String title = e.get("title").textValue();
                    String type = e.get("type").textValue();
                    List<String> skills = StreamSupport
                            .stream(e.get("jobs").spliterator(),true)
                            .parallel()
                            .map(el->el.get("name").textValue())
                            .collect(Collectors.toList());
                    return new Project(projectId, ownerID, timeSubmitted, title, type, skills);

                })
                .limit(10)
                .collect(Collectors.toList());

        return userproject;

    }



}

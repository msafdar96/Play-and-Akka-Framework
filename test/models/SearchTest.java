package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.SearchController;
import helper.HttpApiCall;
import models.*;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import play.api.http.MediaRange;
import play.api.mvc.Request;
import play.data.FormFactory;
import play.i18n.Lang;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.libs.typedmap.TypedKey;
import play.libs.typedmap.TypedMap;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import play.mvc.Result;
import static play.mvc.Http.Status.OK;

import play.test.Helpers;
import scala.Int;

import javax.annotation.processing.Completion;
import java.net.http.HttpRequest;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.testServer;

public class SearchTest {

    @Mock
    private FormFactory formFactory;
    @Mock
    private MessagesApi messagesApi;
    @Mock
    private WSClient wsClient;
    @InjectMocks
    private HttpApiCall httpApiCall;
    @Mock
    private Session session;
    @Mock
    WSResponse wsResponse;
    @InjectMocks
    private SearchController searchController;

    private Http.Request request;
    List<String> test = Arrays.asList("A","b");
    private String userID = null;
    @Mock
    private CompletionStage<List<String>> mockSearch;
    private JsonNode skills = null;
    private JsonNode projects = null;
    private JsonNode project1 = null;
    private JsonNode project2 = null;
    @Mock
    private Search search;
    @Mock
    private User user;
    @Mock
    private String keyword;

    @Before
    public void setup(){
        keyword = "react";
        search = new Search();
        search = new Search(keyword);
//        searchController = new SearchController();
        wsClient = play.test.WSTestClient.newClient(9000);
        httpApiCall = new HttpApiCall(wsClient);
        this.setupMockData();
        mockSearch = CompletableFuture.completedStage(test);
        mockSearch.thenApplyAsync(e->e);

        userID = "987351";

    }

    public void setupMockData(){
        skills = Json.newArray()
                .add(Json.newObject().put("name","React.js"))
                .add(Json.newObject().put("name","Node.js"))
                .add(Json.newObject().put("name","JavaScript"));
        project1 = Json.newObject()
                .put("id", "1")
                .put("owner_id", 2487292)
                .put("time_submitted", 1647732665)
                .put("title", "NFT App Install for NFT minting website")
                .put("type", "Fixed")
                .put("preview_description","I need to install a landing page and minting app (React) to create an NFT minting website.")
                .set("jobs",skills);
        project2 = Json.newObject()
                .put("id","2")
                .put("owner_id", 2487293)
                .put("time_submitted", 1647732665)
                .put("title", "This is a test")
                .put("type", "Fixed")
                .put("preview_description","This is a test. Need a developer urgently! " +
                        "Answer following questions before bidding: 1) Do you have experience in this field?" +
                        "2) How good you are in coding?")
                .set("jobs",skills);

        projects = Json.newArray()
                .add(project1)
                .add(project2);
    }

    /**
     * This test case is used to test the save projects methods
     * @author group
     * @throws Exception
     */
    @Test
    public void saveProjects() throws Exception{
        CompletionStage<List<Project>> save = search.saveProjects(keyword, projects);
        List<Project> result = save.toCompletableFuture().get();
        assertTrue(result!=null);
        assertTrue(search.getSearchedItems().size()>0);
        assertTrue(search.getSearchedItems().containsKey(keyword));
        assertTrue(!search.getSearchedItems().keySet().isEmpty());
        assertEquals(search.getSearchedItems().keySet().toArray()[0],keyword);
        assertEquals(search.getSearchedItems().get(keyword).get(0).getOwnerID(),"2487292");
        assertEquals(search.getSearchedItems().get(keyword).get(1).getOwnerID(),"2487293");
        assertEquals(search.getSearchedItems().get(keyword).get(0).getTitle(),"NFT App Install for NFT minting website");
        assertEquals(search.getSearchedItems().get(keyword).get(1).getTitle(),"This is a test");

    }

    /**
     * used to test method
     * @author Anusha Reddy
     */

    @Test
    public void saveProjectsForIndividualUser(){
        user = new User();
        List<Project> data = user.saveProjects(userID,projects);
        assertTrue(data.size()>0);
        assertEquals(data.get(0).getOwnerID(),"2487292");
        assertEquals(data.get(1).getOwnerID(),"2487293");
        assertEquals(data.get(1).getTitle(),"This is a test");
        assertEquals(data.get(0).getTitle(),"NFT App Install for NFT minting website");
    }

    /**
     * used to test method
     * @author Anusha Reddy
     */
    @Test
    public void testUser(){
        User user = new User("John", null, "employer", "10.5","en","123343433","1000","John234","employe", null,"India", "Indian Rupee", "true");

        user.setName("John");
        user.setRegistration_date("123343433");
        user.setChosen_role("employer");
        user.setHourly_rate("10.5");
        user.setDisplay_name("John234");
        user.setLimited_account("1000");
        user.setRole("employer");
        user.setUserLocation(null);
        user.setCountry("India");
        user.setCurrency("Indian Rupee");
        user.setEmail("true");
        User user1 = new User();
        assertEquals("John", user.getName());
        assertEquals("123343433", user.getRegistration_date());
        assertEquals("employer", user.getChosen_role());
        assertEquals("1000", user.getLimited_account());
        assertEquals("10.5", user.getHourly_rate());
        assertEquals("employer",user.getRole());
        assertEquals("India",user.getCountry());
        assertEquals("Indian Rupee",user.getCurrency());
        assertEquals("true",user.getEmail());
        user1.setName("Ali");
        user1.setRegistration_date("334343434");
        user1.setChosen_role("freelancer");
        user1.setDisplay_name("freelance1");
        user1.setLimited_account("19889");
        user1.setHourly_rate("56");
        user1.setRole("freelance employee");
        user1.setUserLocation(null);
        user1.setCountry("United States");
        user1.setCurrency("US Dollars");
        user1.setEmail("true");
        assertEquals("Ali", user1.getName());
        assertEquals("334343434", user1.getRegistration_date());
        assertEquals("freelancer", user1.getChosen_role());
        assertEquals("19889", user1.getLimited_account());
        assertEquals("56", user1.getHourly_rate());
        assertEquals("freelance employee",user1.getRole());
        assertEquals("United States",user1.getCountry());
        assertEquals("US Dollars",user1.getCurrency());
        assertEquals("true",user1.getEmail());
        //assertEquals("null", user1.getUserLocation());

    }
    /**
     * used to test method
     * @author Anusha Reddy
     */
    @Test
    public void TestProject(){
        ArrayList<String> skills = new ArrayList<>(Arrays.asList("C++","Java"));
        Date datesubmitted = new Date(2323223232L);
        Project project1 = new Project("123","1234675", new Date() , "Dummy", "Large Scale" , new ArrayList<>(Arrays.asList("C++","Java")));
        Project project2 = new Project();
        project2.setOwnerID("345555555");
        project2.setSkills(skills);
        project2.setTimeSubmitted(datesubmitted);
        project2.setTitle("Dummy1");
        project2.setType("Small");
        assertEquals("345555555", project2.getOwnerID());
        assertEquals(skills,project2.getSkills());
        assertEquals("Dummy1",project2.getTitle());
        assertEquals("Small",project2.getType());
        assertEquals(datesubmitted,project2.getTimeSubmitted());

    }

    /**
     * This test case is used to test the save projects methods
     * @author Mariam Safdar
     * @throws Exception
     */
    @Test
    public void skillProjects() throws Exception{
        CompletionStage<List<Project>> getProjects = search.skillProjects(projects);
        List<Project> projects = getProjects.toCompletableFuture().get();
        assertTrue(projects.size()!=0);
        assertEquals(projects.get(0).getOwnerID(),"2487292");
        assertEquals(projects.get(1).getOwnerID(),"2487293");
        assertEquals(projects.get(0).getTitle(),"NFT App Install for NFT minting website");
        assertEquals(projects.get(1).getTitle(),"This is a test");

    }

    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getProject() throws Exception{
        CompletionStage<List<Project>> save = search.saveProjects(keyword, projects);
        List<Project> result = save.toCompletableFuture().get();
        CompletionStage<Project> getProject = search.getProject(keyword,0);
        Project project = getProject.toCompletableFuture().get();
        assertTrue(project!=null);
        assertEquals(project.getOwnerID(),"2487292");
        assertEquals(project.getTitle(),"NFT App Install for NFT minting website");
        assertEquals(project.getPreviewDescription(),"I need to install a landing page and minting app (React) to create an NFT minting website.");
        getProject = search.getProject(keyword,1);
        project = getProject.toCompletableFuture().get();
        assertEquals(project.getOwnerID(),"2487293");
        assertEquals(project.getTitle(),"This is a test");
        assertEquals(project.getPreviewDescription(),"This is a test. Need a developer urgently! " +
                "Answer following questions before bidding: 1) Do you have experience in this field?" +
                "2) How good you are in coding?");
    }

    /**
     * used to test method
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void getKeyword() throws Exception{
        CompletionStage<List<Project>> save = search.saveProjects(keyword, projects);
        List<Project> result = save.toCompletableFuture().get();
        search.setKeyword(this.keyword);
        String keyword = search.getKeyword();
        assertEquals(keyword,this.keyword);
        assertTrue(keyword.length()>0);
    }

    @Test
    public void testKeyword(){
        search.setKeyword("android");
        assertTrue(search.getKeyword()!=null);
        assertEquals(search.getKeyword(), "android");
    }



}


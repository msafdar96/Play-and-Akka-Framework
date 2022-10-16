package controllers;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helper.HttpApiCall;
import models.*;
import org.eclipse.jetty.client.api.Request;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import play.Application;
import play.DefaultApplication;
import play.api.http.MediaRange;
import play.api.inject.guice.GuiceApplicationBuilder;
import play.api.mvc.Call;
import play.data.FormFactory;
import play.i18n.Lang;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.libs.typedmap.TypedKey;
import play.libs.typedmap.TypedMap;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import play.mvc.Result;

import static java.rmi.Naming.bind;
import static play.mvc.Http.Status.OK;

import play.test.Helpers;
import play.test.WithApplication;
import scala.Int;

import javax.annotation.processing.Completion;
import javax.inject.Inject;
import java.net.http.HttpRequest;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static play.test.Helpers.*;


public class SearchControllerTest{

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
    @Mock String url;

    @Mock
    private Materializer materializer;
    @Mock
    ActorSystem actorSystem;

    int index;

    @Before
    public void setup() {
        wsClient = play.test.WSTestClient.newClient(9000);
        actorSystem =  mock(ActorSystem.class);
        materializer =  mock(Materializer.class);
        searchController = new SearchController(actorSystem, materializer, wsClient);
        httpApiCall = new HttpApiCall(wsClient);
        keyword = "react";
        index = 1;
        mockSearch = CompletableFuture.completedStage(test);
        mockSearch.thenApplyAsync(e -> e);
        userID = "987351";
        url = "http://localhost:9000";

    }

    /**
     * This test case is used to test the index method of the controller
     * @author Sahran Khuwaja (Description Readability)
     * @throws Exception
     */
    @Test
    public void index() throws Exception{
        Result result = searchController.index();
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        assertTrue(contentAsString(result).toLowerCase().contains("welcome"));

    }

    /**
     * This test case is used to test the search method of the controller
     * @author group
     * @throws Exception
     */
    @Test
    public void search() throws Exception{

    }

    /**
     * This test case is used to test the projectStats method of the controller
     * @author Mohammed Misbah Uddin Shareef
     * @throws Exception
     */
    @Test
    public void projectStats() throws Exception{
        Result result = searchController.projectStats(keyword, index);
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
    }

    /**
     * This test case is used to test the globalStats method of the controller
     * @author Mohammed Misbah Uddin Shareef
     * @throws Exception
     */
    @Test
    public void globalStats() throws Exception{
        Result result = searchController.globalStats(keyword);
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());

    }

    /**
     * This test case is used to test the showReadability method of the controller
     * @author Sahran Khuwaja
     * @throws Exception
     */
    @Test
    public void showReadability() throws Exception{
        Result result = searchController.showReadability(keyword, index);
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        assertTrue(contentAsString(result).toLowerCase().contains("description readability"));
    }

    /**
     * This test case is used to test the userDetails method of the controller
     * @author Anusha Reddy
     * @throws Exception
     */
    @Test
    public void userDetails() throws Exception{

    }

    /**
     * This test case is used to test the skillProjects method of the controller
     * @author Mariam Safdar
     * @throws Exception
     */
    @Test
    public void skillProjects() throws Exception{
        Result result = searchController.index();
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        assertTrue(contentAsString(result).toLowerCase().contains("welcome"));
    }

//    /**
//     * This test case is used to test the createSession method of the controller
//     * @author group
//     * @throws Exception
//     */
//    @Test
//    public void createSession() throws Exception{
//
//    }

    /**
     * This test case is used to test the generateRandomSessionID method of the controller
     * @author group
     * @throws Exception
     */
    @Test
    public void generateRandomSessionID() throws Exception{
        String id = searchController.generateRandomSessionID();
        assertTrue(id.length()!=0);
        assertEquals(id.length(),30);
    }









}


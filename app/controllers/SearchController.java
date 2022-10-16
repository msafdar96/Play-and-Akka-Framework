package controllers;

import actors.*;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import com.fasterxml.jackson.databind.JsonNode;
import helper.HttpApiCall;
import models.*;
import play.i18n.MessagesApi;
import play.data.FormFactory;

import play.libs.streams.ActorFlow;
import play.libs.ws.WSClient;
import play.mvc.Controller;

import play.mvc.Http;
import play.mvc.Result;

import play.mvc.WebSocket;
import views.html.Search.*;
import views.html.DescriptionReadability.*;
import views.html.User.userdetails;
import views.html.WordStats.*;
import views.html.Skills.*;
import javax.inject.Inject;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This controller contains multiple methods to handle HTTP requests
 * to for the application search and other pages .
 * @author group
 */
public class SearchController extends Controller {

    @Inject
    FormFactory formFactory;
    @Inject
    MessagesApi messagesApi;
    @Inject
    WSClient wsClient;

    @Inject
    private Materializer materializer;

    ActorSystem actorSystem;
    HttpApiCall httpApiCall;
    Session session = new Session();
    int userID = 0;

    @Inject
    public SearchController(ActorSystem actorSystem, Materializer materializer, WSClient wsClient){
       this.actorSystem = actorSystem;
       this.materializer = materializer;
       this.wsClient = wsClient;
       actorSystem.actorOf(RealTimeActor.props(), "RealTimeActor");
    }

    /**
     * This index controller is responsible for handling the search request and managing the session
     *  done asynchronously by <code>CompletionStage and CompletableFuture.supplyAsync()</code> methods
     *  via websockets and akka actors. This displays the HTML page which contains welcome message and
     *  a search field for the user to search
     * @author Group
     * @return Result, as HTTP Response.
     */
    public Result index(){
       // String searchSocketURL = routes.SearchController.search().webSocketURL(request);
        return ok(index.render ("FreeLancelot","Welcome to FreeLancelot"));
    }

    /**
     * Creates websocket connection for search page
     * Fetch the project details from freelancer api asynchronously by calling
     * HttpApiCall Helper class and pass this details to search method to save details asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method and then send the
     * fetched results to index page using
     * <code>CompletableFuture.thenApply </code> via websockets and actors
     * @author Sahran Khuwaja
     * @return WebSocket
     */
    public WebSocket search(){
        httpApiCall = new HttpApiCall(wsClient);
        Search search = new Search();
        return WebSocket.Json.accept(request -> ActorFlow
                .actorRef(webSocket->SearchActor.props(webSocket, httpApiCall, search),
                        actorSystem, materializer));
    }

    /**
     * Creates websocket connection for search page and description readability page
     * Fetch the project details from freelancer api asynchronously by calling
     * HttpApiCall Helper class and pass this details to search method to save details asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method and then send the
     * fetched results to index page using
     * <code>CompletableFuture.thenApply </code> via websockets and actors
     * @author Sahran Khuwaja
     * @return WebSocket
     */
    public WebSocket descriptionReadability(){
        httpApiCall = new HttpApiCall(wsClient);
        DescriptionReadability descriptionReadability = new DescriptionReadability();
        return WebSocket.Json.accept(request -> ActorFlow
                .actorRef(webSocket-> DescriptionReadabilityActor.props(webSocket, httpApiCall, descriptionReadability),
                        actorSystem, materializer));
    }

    /**
     * @author Sahran Khuwaja
     * Fetch the project readability details from Search object asynchronously by receiving
     * the individual project readability asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method and then redirects the
     * fetched results to readability page using
     * <code>CompletableFuture.thenApply </code>
     * @param keyword
     * @param index
     * @return CompletableFuture of type Result a play.mvc.Result value, representing the HTTP response.
     */
    public Result showReadability(String keyword, Integer index){
        return ok(readability.render("FreeLancelot",keyword));
    }

    /**
     * Creates websocket connection for stats page
     * Fetch the project details from freelancer api asynchronously by calling
     * HttpApiCall Helper class and pass this details to search method to save details asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method and then send the
     * fetched results to index page using
     * <code>CompletableFuture.thenApply </code> via websockets and actors
     * @author Misbah
     * @return WebSocket
     */
    public WebSocket stats(){
        httpApiCall = new HttpApiCall(wsClient);
        GlobalStats globalStats = new GlobalStats();
        return WebSocket.Json.accept(request -> ActorFlow
                .actorRef(webSocket-> StatsActor.props(webSocket, httpApiCall, globalStats),
                        actorSystem, materializer));
    }

    /**
     * @author Mohammed Misbah Uddin Shareef
     * Fetch the project words stats details from Search object asynchronously by receiving
     * the individual project words stats asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method and then redirects the
     * fetched results to project stats page using
     * <code>CompletableFuture.thenApply </code>
     * @param keyword
     * @param index
     * @return CompletableFuture of type Result a play.mvc.Result value, representing the HTTP response.
     */

    public Result projectStats(String keyword, Integer index){

        return ok(projectstats.render(keyword));
    }

    /**
     * @author Mohammed Misbah Uddin Shareef
     * Fetch the Global details from Search object asynchronously by receiving
     * the global words stats which were calculated  asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method and then redirects the
     * fetched results to Global stats page using
     * <code>CompletableFuture.thenApply </code>
     * @param keyword
     * @return CompletableFuture of type Result a play.mvc.Result value, representing the HTTP response.
     */
    public Result globalStats(String keyword){

        return ok(globalstats.render(keyword));

    }

    /**
     * Creates websocket connection for Skills page
     * Fetch the projects related to selected Skills from freelancer api asynchronously by calling
     * HttpApiCall Helper class and pass this details to skills method to save details asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method and then send the
     * fetched results to index page using
     * <code>CompletableFuture.thenApply </code> via websockets and actors
     * @author Mariam Safdar
     * @return WebSocket
     */

    public WebSocket skills(){
        httpApiCall = new HttpApiCall(wsClient);
        Search skillset = new Search();
        return WebSocket.Json.accept(request -> ActorFlow
                .actorRef(webSocket-> SkillsActor.props(webSocket, httpApiCall, skillset),
                        actorSystem, materializer));

    }

    /**
     * @author Mariam Safdar
     * Fetch the projects from specific skill asynchronously by receiving
     * the projects asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method and then redirects the
     * fetched results to skills details page using
     * <code>CompletableFuture.thenApply </code>
     * @param keyword
     * @return CompletableFuture of type Result a play.mvc.Result value, representing the HTTP response.
     */
    public Result projectSkills(String keyword){

        return ok(skillProject.render("FreeLancelot","Welcome to FreeLancelot"));

    }

    /**
     * Creates websocket connection for user page
     * Fetch the project details from freelancer api asynchronously by calling
     * HttpApiCall Helper class and pass this details to search method to save details asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method and then send the
     * fetched results to index page using
     * <code>CompletableFuture.thenApply </code> via websockets and actors
     * @author Sahran Khuwaja
     * @return WebSocket
     */
    public WebSocket user(){
        httpApiCall = new HttpApiCall(wsClient);
        User user = new User();
        return WebSocket.Json.accept(request -> ActorFlow
                .actorRef(webSocket-> UserActor.props(webSocket, httpApiCall, user),
                        actorSystem, materializer));
    }

    /**
     * @author Anusha Reddy
     * Fetch the user details from user object asynchronously by receiving
     * the user details and projects asynchronously using
     * <code>CompletableFuture.supplyAsync</code> method and then redirects the
     * fetched results to user details page
     * @param id
     * @return CompletableFuture of type Result a play.mvc.Result value, representing the HTTP response.
     */

    public Result userDetails(String id){
        return ok(userdetails.render(id));
    }



//    /**
//     * this method creates session when ever required for the new session.
//     * @author Group
//     * @param request
//     * @return the new session details to index page
//     */
//    public Result createSession(Http.Request request){
//        userID++;
//        String sessionUserID = this.generateRandomSessionID() + userID;
//        Search search = new Search();
//        session.setSession(sessionUserID,search);
//        return redirect(routes.SearchController.index())
//                .addingToSession(request, "userID", sessionUserID);
//    }

    /**
     * generates uniques random sessions id
     * @author Group
     * @return new session id
     */


    public String generateRandomSessionID() {
        int lowerLimit = 97;
        int upperLimit = 122;
        Random random = new Random();
        StringBuffer sf = new StringBuffer(30);
        for (int i = 0; i < 30; i++) {
            int randomChar = lowerLimit
                    + (int)(random.nextFloat()
                    * (upperLimit - lowerLimit + 1));
            sf.append((char)randomChar);
        }
        return sf.toString();
    }
}

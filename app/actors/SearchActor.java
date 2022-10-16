package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import actors.RealTimeActor.UpdateData;

import helper.HttpApiCall;
import models.DescriptionReadability;
import models.Project;
import models.Search;
import play.libs.Json;

import util.JsonResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Test cases for model class DescriptionReadability
 * @author Group
 */
public class SearchActor extends AbstractActor {

    ActorRef webSocket;
    HttpApiCall httpApiCall;
    Search search;

    /**
     * Constructor to create instance of the actor.
     *
     * @param webSocket Reference of websocket actor
     * @param httpApiCall
     * @param search
     * @author Group
     */
    public SearchActor(ActorRef webSocket, HttpApiCall httpApiCall, Search search) {
        this.webSocket = webSocket;
        this.httpApiCall = httpApiCall;
        this.search = search;

    }

    /**
     * Factory method to create instance of User Search Actor
     *
     * @param webSocket   Reference of websocket actor
     * @param httpApiCall WSClient to fetch data from freelancer api
     * @param search      Search model class
     * @return Props Props
     * @author Group
     */
    public static Props props(ActorRef webSocket, HttpApiCall httpApiCall, Search search) {
        return Props.create(SearchActor.class, webSocket, httpApiCall, search);
    }

    /**
     * It registers the current actor reference to RealTimeActor
     *
     * @author Group
     */
    @Override
    public void preStart() {
        context().actorSelection("/user/RealTimeActor/")
                .tell(new RealTimeActor.Register(), self());
    }

    /**
     * It de-registers current actor reference from RealTimeActor
     *
     * @author Group
     */
    @Override
    public void postStop() {
        context().actorSelection("/user/RealTimeActor/")
                .tell(new RealTimeActor.DeRegister(), self());
    }

    /**
     * It receives messages and decides action based on message type.
     *
     * @author Group
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(UpdateData.class, updatedData -> sendUpdatedData())
                .match(ObjectNode.class, search -> sendNewData(search.get("keyword").textValue()))
                .build();
    }

    /**
     * This method sends new search results when queried by user fetched from freelancer api.
     *
     * @param keyword Keyword for searching projects
     * @author Sahran Khuwaja
     */
    private void sendNewData(String keyword) {
        String searchKeyword = keyword.toLowerCase();
        if (searchKeyword.isEmpty()) {
            String error = "Search field is empty!";
            webSocket.tell(JsonResponse.createJsonResponse(null, searchKeyword, true, error, true, false), self());
        } else if (search.getSearchedItems().containsKey(searchKeyword)) {
            String error = "Search results for '" + searchKeyword
                    + "' has already been displayed!";
            webSocket.tell(JsonResponse.createJsonResponse(null, searchKeyword, true, error, true, false), self());
        } else {
            CompletionStage<JsonNode> projects = httpApiCall.searchProjects(searchKeyword)
                    .thenApplyAsync(e -> e.asJson().findPath("projects"));

            CompletionStage<List<DescriptionReadability>> previewDescriptionOfAllProjects = projects
                    .thenApplyAsync(e -> {
                        List<DescriptionReadability> data = StreamSupport
                                .stream(e.spliterator(), true)
                                .parallel()
                                .map(el -> new DescriptionReadability(el.get("preview_description").asText()))
                                .collect(Collectors.toList());
                        return data;
                    })
                    .thenApplyAsync(e -> {
                        JsonNode jsonData = Json.toJson(e);
                        webSocket.tell(JsonResponse.createJsonResponse(jsonData, searchKeyword, true, null, true, true), self());
                        return e;
                    });

            CompletionStage<List<Project>> saveProjects = projects
                    .thenApplyAsync(e -> search.saveProjects(searchKeyword, e))
                    .thenComposeAsync(e -> e.thenApplyAsync(el -> {
                        JsonNode jsonData = Json.toJson(el);
                        webSocket.tell(JsonResponse.createJsonResponse(jsonData, searchKeyword, true, null, true, false), self());
                        return el;
                    }));


        }

    }

    /**
     * This method send new tweets data if available to users via websocket.
     *
     * @author Sahran Khuwaja
     */
    private void sendUpdatedData() {

        Set<String> searchedItems = search.getSearchedItems().keySet();
        searchedItems.stream()
                .parallel()
                .forEach(keyword -> {
                    CompletionStage<JsonNode> projects = httpApiCall.searchProjects(keyword)
                            .thenApplyAsync(e -> e.asJson().findPath("projects"))
                            .thenApplyAsync(e -> {
                               // System.out.println(e.get(0).get("id").toString().equals(search.getSearchedItems().get(keyword).get(0).getId()));
                                if (!e.get(0).get("id").toString().equals(search.getSearchedItems().get(keyword).get(0).getId())) {

                                    CompletionStage<List<DescriptionReadability>> previewDescriptionOfAllProjects = CompletableFuture
                                            .supplyAsync(()->{
                                                List<DescriptionReadability> data = StreamSupport
                                                        .stream(e.spliterator(), true)
                                                        .parallel()
                                                        .map(el -> new DescriptionReadability(el.get("preview_description").asText()))
                                                        .collect(Collectors.toList());
                                                return data;
                                            })
                                            .thenApplyAsync(el -> {
                                                JsonNode jsonData = Json.toJson(el);
                                                webSocket.tell(JsonResponse.createJsonResponse(jsonData, keyword, true, null, false, true), self());
                                                return el;
                                            });
                                    CompletionStage<List<Project>> saveProjects = search.saveProjects(keyword, e)
                                            .thenApplyAsync(el -> {
                                                JsonNode jsonData = Json.toJson(el);
                                                webSocket.tell(JsonResponse.createJsonResponse(jsonData, keyword, true, null, false, false), self());
                                                return el;
                                            });
                                }
                                return e;
                            });
                });


    }

}

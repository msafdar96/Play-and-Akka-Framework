package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helper.HttpApiCall;
import models.DescriptionReadability;
import models.GlobalStats;
import models.Search;
import play.libs.Json;
import util.JsonResponse;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class StatsActor extends AbstractActor {


    ActorRef webSocket;
    HttpApiCall httpApiCall;
    GlobalStats globalStats;

    /**
     * Constructor to create instance of the actor.
     *
     * @param webSocket Reference of websocket actor
     * @author Misbah
     */

    public StatsActor(ActorRef webSocket, HttpApiCall httpApiCall, GlobalStats globalStats) {
        this.webSocket = webSocket;
        this.httpApiCall = httpApiCall;
        this.globalStats = globalStats;
    }

    /**
     * Factory method to create instance of User Search Actor
     *
     * @param webSocket   Reference of websocket actor
     * @param httpApiCall WSClient to fetch data from freelancer api
     * @param globalStats DescriptionReadability model class
     * @return Props Props
     * @author Misbah
     */

    public static Props props(ActorRef webSocket, HttpApiCall httpApiCall, GlobalStats globalStats) {
        return Props.create(StatsActor.class, webSocket, httpApiCall, globalStats);
    }

    /**
     * It registers the current actor reference to RealTimeActor
     *
     * @author Misbah
     */

    @Override
    public void preStart() {
        context().actorSelection("/user/RealTimeActor/")
                .tell(new RealTimeActor.Register(), self());
    }

    /**
     * It de-registers current actor reference from RealTimeActor
     *
     * @author Misbah
     */

    @Override
    public void postStop() {
        context().actorSelection("/user/RealTimeActor/")
                .tell(new RealTimeActor.DeRegister(), self());
    }

    /**
     * It receives messages and decides action based on message type.
     *
     * @author Misbah
     */

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(RealTimeActor.UpdateData.class, updatedData -> sendUpdatedData())
                .match(ObjectNode.class, data -> stats(data))
                .build();
    }

    private void stats(ObjectNode data) {
        if (data.get("global").asBoolean()) {
            this.calculateGlobalStats(data);
        } else {
            this.calculateStats(data);
        }

    }

    /**
     * This method sends new search results when queried by user fetched from freelancer api.
     *
     * @param data Keyword for searching projects
     * @author Sahran Khuwaja
     */

    private void calculateStats(ObjectNode data) {
        String previewDescription = data.get("data").get(0).get("previewDescription").asText();
        CompletionStage<Map<String, Integer>> stats = CompletableFuture.supplyAsync(() -> {
            return globalStats.getWStats(previewDescription);
        }).thenApplyAsync(e -> {
            Integer uwords = e.size();
            DescriptionReadability descriptionReadability = new DescriptionReadability();
            Integer twords = descriptionReadability.getWords(previewDescription).length;
            Integer characters = Arrays.stream(descriptionReadability.getWords(previewDescription)).mapToInt(String::length).sum();
            Integer sentences = descriptionReadability.getSentences(previewDescription).length;
            float charsPerWord = 0;
            float wordsPerSent = 0;
            float charsPerSent = 0;
            if (twords != 0) {
                charsPerWord = (float) characters / twords;
            }
            if (sentences != 0) {
                wordsPerSent = (float) twords / sentences;
                charsPerSent = (float) characters / sentences;
            }

            ObjectNode wordStats = Json.newObject();
            wordStats.putPOJO("wordStats", e);
            wordStats.put("twords", twords);
            wordStats.put("uwords", uwords);
            wordStats.put("sentences", sentences);
            wordStats.put("characters", characters);
            wordStats.put("wordsPerSent", wordsPerSent);
            wordStats.put("charsPerSent", charsPerSent);
            wordStats.put("charsPerWord", charsPerWord);
            JsonNode jsonData = Json.toJson(wordStats);
            webSocket.tell(JsonResponse.createJsonResponse(jsonData, "", true, null, true, false), self());
            return e;
        });

    }

    /**
     * This method sends new search results when queried by user fetched from freelancer api.
     *
     * @param data Keyword for searching projects
     * @author Sahran Khuwaja
     */

    private void calculateGlobalStats(ObjectNode data) {
        Search search = new Search();
        String keyword = data.get("keyword").asText();
        CompletionStage<JsonNode> projects = httpApiCall.searchProjects(data.get("keyword").asText())
                .thenApplyAsync(e -> e.asJson().findPath("projects"));

        CompletionStage<Boolean> stats = projects
                .thenApplyAsync(e->{
                    return globalStats.globalWStats(keyword,e);
                })
                .thenComposeAsync(e -> e.thenApplyAsync(el->{
                    JsonNode jsonData = Json.toJson(globalStats.globalStats);
                    webSocket.tell(JsonResponse.createJsonResponse(jsonData, "", true, null, true, false), self());
                    return el;
                }));

    }

    /**
     * This method send new tweets data if available to users via websocket.
     *
     * @author Group
     */

    private void sendUpdatedData() {


    }

}

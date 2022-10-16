package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helper.HttpApiCall;
import models.DescriptionReadability;
import play.libs.Json;

import util.JsonResponse;

import java.util.concurrent.CompletionStage;

/**
 * DescriptionReadabilityActor
 * @author Sahran Khuwaja
 */
public class DescriptionReadabilityActor extends AbstractActor {
    ActorRef webSocket;
    HttpApiCall httpApiCall;
    DescriptionReadability descriptionReadability;

    /**
     * Constructor to create instance of the actor.
     *
     * @param webSocket Reference of websocket actor
     * @param httpApiCall
     * @param descriptionReadability
     * @author Group
     */
    public DescriptionReadabilityActor(ActorRef webSocket, HttpApiCall httpApiCall, DescriptionReadability descriptionReadability) {
        this.webSocket = webSocket;
        this.httpApiCall = httpApiCall;
        this.descriptionReadability = descriptionReadability;
    }

    /**
     * Factory method to create instance of User Search Actor
     *
     * @param webSocket              Reference of websocket actor
     * @param httpApiCall            WSClient to fetch data from freelancer api
     * @param descriptionReadability DescriptionReadability model class
     * @return Props Props
     * @author Sahran Khuwaja
     */
    public static Props props(ActorRef webSocket, HttpApiCall httpApiCall, DescriptionReadability descriptionReadability) {
        return Props.create(DescriptionReadabilityActor.class, webSocket, httpApiCall, descriptionReadability);
    }

    /**
     * It registers the current actor reference to RealTimeActor
     *
     * @author Sahran Khuwaja
     */
    @Override
    public void preStart() {
        context().actorSelection("/user/RealTimeActor/")
                .tell(new RealTimeActor.Register(), self());
    }

    /**
     * It de-registers current actor reference from RealTimeActor
     *
     * @author Sahran Khuwaja
     */
    @Override
    public void postStop() {
        context().actorSelection("/user/RealTimeActor/")
                .tell(new RealTimeActor.DeRegister(), self());
    }

    /**
     * It receives messages and decides action based on message type.
     *
     * @author Sahran Khuwaja
     */
    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(RealTimeActor.UpdateData.class, updatedData -> sendUpdatedData())
                .match(ObjectNode.class, data -> calculateReadability(data, data.get("isNew").asBoolean()))
                .build();
    }

    /**
     * This method sends new search results when queried by user fetched from freelancer api.
     *
     * @param data Keyword for searching projects
     * @param isNew
     * @author Sahran Khuwaja
     */
    private void calculateReadability(ObjectNode data, boolean isNew) {
        if (data.get("individual").asBoolean()) {
            this.calculateReadabilityOfASpecificProject(data.get("keyword").asText(), data.get("data"), isNew);
        } else {
            this.calculateReadabilityOfAllProjects(data.get("keyword").asText(), data.get("data"), isNew);
        }
    }

    /**
     * This method sends new search results when queried by user fetched from freelancer api.
     *
     * @param keyword Keyword for searching projects
     * @param project
     * @param isNew
     * @author Sahran Khuwaja
     */

    private void calculateReadabilityOfASpecificProject(String keyword, JsonNode project, boolean isNew){

        CompletionStage<Boolean> calculateDescriptionReadabilityOfEachProject = descriptionReadability
                .calculateDescriptionReadabilityOfEachProject(keyword, project);
        CompletionStage<Boolean> readability =  calculateDescriptionReadabilityOfEachProject.
                thenApplyAsync(el -> {
                    JsonNode jsonData = Json.toJson(descriptionReadability);
                    webSocket.tell(JsonResponse.createJsonResponse(jsonData, keyword, true, null, isNew, false), self());
                    return el;
                });
    }

    /**
     * This method sends new search results when queried by user fetched from freelancer api.
     *
     * @param keyword Keyword for searching projects
     * @param projects
     * @param isNew
     * @author Sahran Khuwaja
     */

    private void calculateReadabilityOfAllProjects(String keyword, JsonNode projects, boolean isNew) {
        CompletionStage<Boolean> calculateDescriptionReadabilityOfAllProjects = descriptionReadability
                .calculateDescriptionReadabilityOfAllProjects(keyword, projects);

        CompletionStage<Boolean> calculateDescriptionReadabilityOfEachProject = descriptionReadability
                .calculateDescriptionReadabilityOfEachProject(keyword, projects);

        CompletionStage<Boolean> readability = calculateDescriptionReadabilityOfAllProjects
                .thenApplyAsync(e -> calculateDescriptionReadabilityOfEachProject).
                thenComposeAsync(e -> e.thenApplyAsync(el -> {
                    JsonNode jsonData = Json.toJson(descriptionReadability);
                    webSocket.tell(JsonResponse.createJsonResponse(jsonData, keyword, true, null, isNew, false), self());
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

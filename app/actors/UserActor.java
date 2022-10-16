package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helper.HttpApiCall;
import models.DescriptionReadability;
import models.Project;
import models.Search;
import models.User;
import play.libs.Json;
import util.JsonResponse;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserActor extends AbstractActor {
    ActorRef webSocket;
    HttpApiCall httpApiCall;
    User user;

    /**
     * Constructor to create instance of the actor.
     *
     * @param webSocket   Reference of websocket actor
     * @param httpApiCall
     * @param user
     * @author Sahran Khuwaja
     */
    public UserActor(ActorRef webSocket, HttpApiCall httpApiCall, User user) {
        this.webSocket = webSocket;
        this.httpApiCall = httpApiCall;
        this.user = user;

    }

    /**
     * Factory method to create instance of User Search Actor
     *
     * @param webSocket   Reference of websocket actor
     * @param httpApiCall WSClient to fetch data from freelancer api
     * @param user      Search model class
     * @return Props Props
     * @author Group
     */
    public static Props props(ActorRef webSocket, HttpApiCall httpApiCall, User user) {
        return Props.create(UserActor.class, webSocket, httpApiCall, user);
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
                .match(ObjectNode.class, user -> sendNewData(user.get("ownerID").textValue()))
                .build();
    }

    /**
     * This method sends new search results when queried by user fetched from freelancer api.
     *
     * @param ownerID Keyword for searching projects
     * @author Sahran Khuwaja
     */
    private void sendNewData(String ownerID) {
        CompletionStage<Boolean> result1 = httpApiCall.searchUserId(ownerID)
                .thenApplyAsync(e -> e.asJson().findPath("result"))
                .thenApplyAsync(
                        e -> {
                            user.setDetails(e);
                            JsonNode jsonData = Json.toJson(user);
                            webSocket.tell(JsonResponse.createJsonResponse(jsonData, ownerID, true, null, true, false), self());
                            return true;
                        });

        CompletionStage<Boolean> result2 = httpApiCall.searchUserProjects(ownerID)
                .thenApplyAsync(e -> e.asJson().findPath("projects"))
                .thenApplyAsync(
                        e -> {
                            List<Project> projects = user.saveProjects(ownerID, e);
                            JsonNode jsonData = Json.toJson(projects);
                            webSocket.tell(JsonResponse.createJsonResponse(jsonData, ownerID, true, null, true, false), self());
                            return true;
                        });
    }

}

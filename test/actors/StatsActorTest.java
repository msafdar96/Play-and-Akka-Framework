package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helper.HttpApiCall;
import models.GlobalStats;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WSClient;


import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class StatsActorTest {

    static ActorSystem system;
    ActorRef ref;

    @Inject
    WSClient wsClient;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }


    @Test
    public void test() {

        new TestKit(system){
            {
                wsClient = play.test.WSTestClient.newClient(9000);
                HttpApiCall httpApiCall  = new HttpApiCall(wsClient);
                GlobalStats globalStats = new GlobalStats();
                ActorRef sampleRef = null;

                JsonNodeFactory factory = JsonNodeFactory.instance;
                ObjectNode root = factory.objectNode();
                root.put("keyword", "hello");

                JsonNode skills = Json.newArray()
                        .add(Json.newObject().put("name","React.js"))
                        .add(Json.newObject().put("name","Node.js"))
                        .add(Json.newObject().put("name","JavaScript"));


                JsonNode project1 = Json.newObject()
                        .put("id", "1")
                        .put("owner_id", 2487292)
                        .put("time_submitted", 1647732665)
                        .put("title", "NFT App Install for NFT minting website")
                        .put("type", "Fixed")
                        .put("preview_description","I need to install a landing page and minting app (React) to create an NFT minting website.")
                        .set("jobs",skills);

                JsonNode project2 = Json.newObject()
                        .put("id","2")
                        .put("owner_id", 2487293)
                        .put("time_submitted", 1647732665)
                        .put("title", "This is a test")
                        .put("type", "Fixed")
                        .put("preview_description","This is a test. Need a developer urgently! " +
                                "Answer following questions before bidding: 1) Do you have experience in this field?" +
                                "2) How good you are in coding?")
                        .set("jobs",skills);

                JsonNode projects = Json.newArray()
                        .add(project1)
                        .add(project2);

                ActorRef searchActor = system.actorOf(StatsActor.props(sampleRef, httpApiCall, globalStats), "User-actor");
                searchActor.tell(project2, ActorRef.noSender());


//                ActorRef timerActor = system.actorOf(RealTimeActor.props(), "timeActor");
//                RealTimeActor.Notify n = new RealTimeActor.Notify();
//                RealTimeActor.UpdateData ud = new RealTimeActor.UpdateData();


//                searchActor.tell(root, ActorRef.noSender());

//                root.put("keyword", "");
//                searchActor.tell(root, ActorRef.noSender());

                expectNoMessage();
                assertEquals("\"hello\"", root.get("keyword").toString());

            }
        };



    }

}
package actors;


import static org.junit.Assert.assertEquals;
import models.Search;
import helper.HttpApiCall;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import play.libs.ws.WSClient;

import javax.inject.Inject;

public class SearchActorTest {

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

//        final TestKit testProbe =
        new TestKit(system){
            {
                wsClient = play.test.WSTestClient.newClient(9000);
                HttpApiCall httpApiCall  = new HttpApiCall(wsClient);
                Search search = new Search();
                ActorRef sampleRef = null;
                ActorRef searchActor = system.actorOf(SearchActor.props(sampleRef, httpApiCall, search), "User-actor");
                ActorRef timerActor = system.actorOf(RealTimeActor.props(), "timeActor");
                // final ObjectNode root = mapper.createObjectNode();

                JsonNodeFactory factory = JsonNodeFactory.instance;
                ObjectNode root = factory.objectNode();
                root.put("keyword", "java");
                searchActor.tell(root, ActorRef.noSender());
                RealTimeActor.UpdateData ud = new RealTimeActor.UpdateData();
                System.err.println(searchActor);

                expectNoMessage();
//        RegisterMsg greeting = testProbe.expectMsgClass(RegisterMsg.class);
                assertEquals("\"java\"", root.get("keyword").toString());

            }
        };



    }

}
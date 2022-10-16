package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helper.HttpApiCall;
import models.Search;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.libs.ws.WSClient;

import javax.inject.Inject;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class RealTimeActorTest {

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

                Set<ActorRef> childActors = new HashSet<>();
                childActors.add(searchActor);

                ActorRef timerActor = system.actorOf(RealTimeActor.props(), "timeActor");
                RealTimeActor.Notify n = new RealTimeActor.Notify();
                RealTimeActor.UpdateData ud = new RealTimeActor.UpdateData();

                JsonNodeFactory factory = JsonNodeFactory.instance;
                ObjectNode root = factory.objectNode();
                root.put("keyword", "");
                searchActor.tell(root, ActorRef.noSender());
                timerActor.tell(childActors, ActorRef.noSender());

//                root.put("keyword", "");
//                searchActor.tell(root, ActorRef.noSender());

                expectNoMessage();
                assertEquals("\"\"", root.get("keyword").toString());

            }
        };



    }

}
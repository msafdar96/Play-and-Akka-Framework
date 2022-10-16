package actors;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.Props;
import scala.concurrent.duration.Duration;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RealTimeActor
 * @author Sahran Khuwaja
 */
public class RealTimeActor extends AbstractActorWithTimers {

    Set<ActorRef> childActors = new HashSet<>();

    /**
     * This method helps other actors to register themselves.
     * @author Sahran Khuwaja
     * @version 1.0.0
     */
    public static class Register {

    }

    /**
     * This method helps other actors to de-register themselves.
     *
     * @author Sahran Khuwaja
     * @version 1.0.0
     */
    public static class DeRegister {

    }

    /**
     * This method helps to notify register actors to push new data.
     *
     * @author Sahran Khuwaja
     * @version 1.0.0
     */
    public static final class Notify {

    }

    /**
     * This class protocol helps registered actors to recognize that they need to push new data.
     *
     * @author Sahran Khuwaja
     * @version 1.0.0
     */
    public static final class UpdateData {

    }

    /**
     * Factory method to create instance of Real Time Actor.
     *
     * @return Props "Instance of Real Time Actor"
     * @author Sahran Khuwaja
     */
    public static Props props() {
        return Props.create(RealTimeActor.class,() -> new RealTimeActor());
    }

    /**
     * This method is used to send refresh notification after 10 seconds
     *
     * @author Sahran Khuwaja
     */

    @Override
    public void preStart() {
        getTimers().startPeriodicTimer("Timer", new Notify(), Duration.create(10, TimeUnit.SECONDS));
    }

    /**
     * It receives messages and decides action based on message type.
     *
     * @author Sahran Khuwaja
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Register.class, message -> childActors.add(sender()))
                .match(Notify.class, message -> notifyRegisteredActors())
                .match(DeRegister.class, message -> childActors.remove(sender()))
                .build();
    }

    /**
     * It notifies all registered child actors to push new data.
     *
     * @author Sahran Khuwaja
     */
    private void notifyRegisteredActors() {
        UpdateData updateData = new UpdateData();
        childActors.forEach(actor -> actor.tell(updateData, self()));
    }


}

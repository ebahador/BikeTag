package concordia.assignment1.btag.microControllerModels;

import concordia.assignment1.btag.testModel.State;

public class Accelerometer extends Sensor {

    private final State velocity;

    public Accelerometer(String sensorName, long sensorID, State velocity) {
        super(sensorName, sensorID);
        this.velocity = velocity;
    }

    public State getVelocity() {
        return velocity;
    }

}

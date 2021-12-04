package concordia.assignment1.btag.microControllerModels;

import concordia.assignment1.btag.testModel.State;

public class GEO extends Sensor {
    private final State x, y, z;
    private final String orientation;

    public GEO(String sensorName, long sensorID, State x1, State y1, State z1, String h) {
        super(sensorName, sensorID);
        this.x = x1;
        this.y = y1;
        this.z = z1;
        this.orientation = h;
    }

    public State getX() {
        return x;
    }

    public State getY() {
        return y;
    }

    public State getZ() {
        return z;
    }

    public String getOrientation() {
        return orientation;
    }

}

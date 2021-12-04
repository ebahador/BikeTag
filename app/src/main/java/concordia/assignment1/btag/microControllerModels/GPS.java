package concordia.assignment1.btag.microControllerModels;

import concordia.assignment1.btag.testModel.State;

public class GPS extends Sensor{
    private State latitude;
    private State longitude;

    public GPS(String sensorName, long sensorID) {
        super(sensorName, sensorID);
    }

    public GPS(String sensorName, long sensorID, State latitude, State longitude) {
        super(sensorName, sensorID);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public State getLatitude() {
        return latitude;
    }

    public State getLongitude() {
        return longitude;
    }

}

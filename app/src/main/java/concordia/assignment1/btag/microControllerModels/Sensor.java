package concordia.assignment1.btag.microControllerModels;

public class Sensor {
    protected String sensorName;
    protected long sensorID;

    public Sensor(String sensorName, long sensorID) {
        this.sensorName = sensorName;
        this.sensorID = sensorID;
    }

    public String getSensorName() {
        return sensorName;
    }

    public long getSensorID() {
        return sensorID;
    }

}
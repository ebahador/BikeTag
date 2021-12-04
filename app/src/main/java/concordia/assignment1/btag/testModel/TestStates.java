package concordia.assignment1.btag.testModel;

import java.util.ArrayList;
import java.util.List;

import concordia.assignment1.btag.microControllerModels.Accelerometer;
import concordia.assignment1.btag.microControllerModels.GEO;
import concordia.assignment1.btag.microControllerModels.GPS;


public class TestStates {

    public TestStates() {
    }

    private final int i = 0;
    private final GEO geoDef = new GEO("GEO", i, State.X1, State.Y1, State.Z1, "H");
    private final GPS gpsDef = new GPS("GPS", i, State.LAT1, State.LONG1);
    private final Accelerometer acDef = new Accelerometer("Accelerometer", i, State.VELO1);

    private final GEO geo2 = new GEO("GEO", i, State.X2, State.Y2, State.Z2, "H");
    private final GPS gps2 = new GPS("GPS", i, State.LAT2, State.LONG2);
    private final Accelerometer ac2 = new Accelerometer("Accelerometer", i, State.VELO2);


    public final List aggregateDef() {
        List<String> concatDef = new ArrayList<>();
        concatDef.add(geoDef.toString());
        concatDef.add(gpsDef.toString());
        concatDef.add(acDef.toString());
        return concatDef;
    }

    public final List aggregate2() {
        List<String> concat2 = new ArrayList<>();
        concat2.add(geo2.toString());
        concat2.add(gps2.toString());
        concat2.add(ac2.toString());
        return concat2;
    }

}
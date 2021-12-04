package concordia.assignment1.btag.testModel;

public enum State {
    LAT1(45.49379360378642), LONG1(-73.58299394697445),
    X1(0.0), Y1(0.0), Z1(0.0),
    VELO1(0.0), HORIZONTAL,

    LAT2(45.495830), LONG2(-73.578098),
    X2(2.1), Y2(1.4), Z2(0.2),
    VELO2(4.2145), VERTICAL;

    private double stateVal;

    State(double v) {
        this.stateVal = v;
    }

    public double getStateVal() {
        return stateVal;
    }

    State() {
    }

}
package concordia.assignment1.btag.observerPattern;

public abstract class Subject {
    private boolean state;

    public Subject() {
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public abstract void notifySubscribers(boolean state);
}
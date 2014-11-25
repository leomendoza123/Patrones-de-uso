package cajaDeBolas.Strategy;

public class Context {

    Strategy pather;
    int balls = 5;

    public Context(Strategy c, int cantidad) {
        balls = cantidad;
        this.pather = c;
    }

    public void setStrategy(Strategy c) {
        this.pather = c;
    }

    public void inicio() {
        pather.start(balls);
    }
}

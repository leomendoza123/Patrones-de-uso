package cajaDeBolas.Strategy;

public class Context {
	Strategy pather;
        int balls  = 100; 
 
	public Context( Strategy c )
	{
		this.pather = c;
	}
 
	public void setStrategy(Strategy c) {
		this.pather = c;
	}
 
	public void inicio()
	{
		pather.start(balls);
	}
}



package cajaDeBolas.Strategy;

 
public class StrategyPrototype implements Strategy{
	@Override
	public void start(int balls) {
		System.out.println("Estrategia A");
	}
}
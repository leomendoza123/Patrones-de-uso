package cajaDeBolas.Strategy;

public class Main {	
	public static void main(String args[])
	{
		Strategy Flyweight = new StrategyFlyweight();
		Context context = new Context(Flyweight);
		context.inicio();
 
		Strategy None = new StrategyNone();
		context.setStrategy(None);
		context.inicio(); 
                        
                Strategy Prototype = new StrategyPrototype();
		context.setStrategy(Prototype);
		context.inicio(); 
                        
 
	}
}
 
 

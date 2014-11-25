package cajaDeBolas.Strategy;

public class Main {	
public static void main(String args[])
	{
                int cantidad = 30; 
		Strategy Flyweight = new StrategyFlyweight();
		Context context = new Context(Flyweight, cantidad);
		context.inicio();
 
		Strategy None = new StrategyNone();
		context.setStrategy(None);
		context.inicio(); 
                        
                Strategy Prototype = new StrategyPrototype();
		context.setStrategy(Prototype);
		context.inicio(); 
 
	}
}
 
 

package cajaDeBolas.Flyweight;


import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
// Menu acts as a factory and cache for CoffeeFlavour flyweight objects
class FlyweightFactory {
    
  private Map<Color, FlyweightBallAperiences> aperiences = new HashMap<Color, FlyweightBallAperiences>();
  java.util.Random ram = new Random(); 
  
  FlyweightBallAperiences lookup(Color color) {
    if (!aperiences.containsKey(color))
      aperiences.put(color, new FlyweightBallAperiences(6*ram.nextInt(5)+5, color));
    return aperiences.get(color);
  }

  int totalAperiencesMade() {
    return aperiences.size();
  }
}
 
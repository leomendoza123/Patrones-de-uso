package cajaDeBolas.Prototype;

import cajaDeBolas.Flyweight.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class PrototypeFactory {

    private Map<String, PrototypeAperiences> prototypes;
    java.util.Random ram;

    public PrototypeFactory() {
        this.prototypes = new HashMap<String, PrototypeAperiences>();
        this.ram = new Random();
        prototypes.put(Appearance.RED, new PrototypeAperiences(6 * ram.nextInt(5) + 5, Color.red));
        prototypes.put(Appearance.YELLOW, new PrototypeAperiences(6 * ram.nextInt(5) + 5, Color.yellow));
        prototypes.put(Appearance.BLUE, new PrototypeAperiences(6 * ram.nextInt(5) + 5, Color.blue));
        prototypes.put(Appearance.GREEN, new PrototypeAperiences(6 * ram.nextInt(5) + 5, Color.GREEN));
        prototypes.put(Appearance.ORANGE, new PrototypeAperiences(6 * ram.nextInt(5) + 5, Color.orange));
    }

    PrototypeAperiences lookup(String color) {
        try {

            return prototypes.get(color).clone();
       
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(PrototypeFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
  

    }

    int totalAperiencesMade() {
        return prototypes.size();
    }

    public static class Appearance {

        public static final String RED = "red";
        public static final String YELLOW = "yellow";
        public static final String BLUE = "blue";
        public static final String GREEN = "green";
        public static final String ORANGE = "orange";
    }

}

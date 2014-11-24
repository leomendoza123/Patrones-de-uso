/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cajaDeBolas.Flyweight;

import java.awt.Color;

/**
 *
 * @author Leonardo
 */
public class FlyweightBallAperiences {

    public float radius;         // Ball's radius (package access)
    public Color color;  // Ball's color

    public FlyweightBallAperiences(float radius, Color color) {
        this.radius = radius;
        this.color = color;
    }
}

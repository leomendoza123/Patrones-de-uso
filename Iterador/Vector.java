package Iterador;

import java.util.ArrayList;

public class Vector<T> {
    ArrayList<T> _datos; 
 
    public Vector(int tamaño){ 
        _datos = new ArrayList(tamaño);
    }    
 
    public T getValor(int pos){ 
        return _datos.get(pos); 
    }
 
    public void setValor(int pos, T valor){ 
        _datos.set(pos, valor);
    }
 
    public int dimension(){ 
        return _datos.size(); 
    }
 
    public IteradorVector iterador(){
        return new IteradorVector(this); 
    }
}

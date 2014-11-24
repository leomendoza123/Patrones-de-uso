package Iterador;

import java.util.ArrayList;

public class IteradorVector<T> {

    private ArrayList<T> _vector;
    private int _posicion;

    public IteradorVector(Vector vector) {
        _vector = vector._datos;
        _posicion = 0;
    }

    public boolean hasNext() {
        if (_posicion < _vector.size()) {
            return true;
        } else {
            return false;
        }
    }

    public Object next() {
        T valor = _vector.get(_posicion);
        _posicion++;
        return valor;
    }
}

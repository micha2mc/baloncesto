package model;

import java.io.Serializable;

public class Jugador implements Serializable {
    private int id;
    private String nombre;
    private int votos;

    public Jugador(int id, String nombre, int votos) {
        this.id = id;
        this.nombre = nombre;
        this.votos = votos;
    }

    public Jugador() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
}

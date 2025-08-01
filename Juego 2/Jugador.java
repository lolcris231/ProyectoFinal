package model;

public class Jugador {
 private String nombre;
 private Ficha ficha;

 public Jugador(String nombre, Ficha ficha) {
     this.nombre = nombre;
     this.ficha = ficha;
 }

 public String getNombre() {
     return nombre;
 }

 public Ficha getFicha() {
     return ficha;
 }
}

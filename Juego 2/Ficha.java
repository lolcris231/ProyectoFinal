package model;

public enum Ficha {
	BLANCA('Y'), ROJA('R'), VACIA('-');

	private final char simbolo;

	Ficha(char simbolo) {
		this.simbolo = simbolo;
	}

	public char getSimbolo() {
		return simbolo;
	}
}

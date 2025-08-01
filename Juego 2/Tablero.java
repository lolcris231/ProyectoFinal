package model;

import java.util.Arrays;

public class Tablero {
	public static final int FILAS = 6;
	public static final int COLUMNAS = 7;
	private Ficha[][] tablero;

	public Tablero() {
		tablero = new Ficha[FILAS][COLUMNAS];
		inicializarJuego();
	}

	public void inicializarJuego() {
		for (int i = 0; i < FILAS; i++) {
			Arrays.fill(tablero[i], Ficha.VACIA);
		}
	}

	public int colocarFicha(int columna, Ficha ficha) {
		if (columna < 0 || columna >= COLUMNAS) {
			return -1; // Columna inválida
		}

		for (int fila = FILAS - 1; fila >= 0; fila--) {
			if (tablero[fila][columna] == Ficha.VACIA) {
				tablero[fila][columna] = ficha;
				return fila; // Retorna la fila donde se colocó
			}
		}
		return -1; // Columna llena
	}

	public Ficha getFicha(int fila, int columna) {
		if (isValid(fila, columna)) {
			return tablero[fila][columna];
		}
		return Ficha.VACIA; // O manejar como error, dependiendo del caso
	}

	public boolean comprobarGanador(Ficha jugadorFicha, int ultimaFila, int ultimaColumna) {
		// Comprobar horizontalmente
		if (checkDirection(jugadorFicha, ultimaFila, ultimaColumna, 0, 1) || // Derecha
				checkDirection(jugadorFicha, ultimaFila, ultimaColumna, 0, -1)) { // Izquierda
			return true;
		}

		// Comprobar verticalmente (solo hacia abajo)
		if (checkDirection(jugadorFicha, ultimaFila, ultimaColumna, 1, 0)) {
			return true;
		}

		// Comprobar diagonalmente (arriba-izquierda a abajo-derecha)
		if (checkDirection(jugadorFicha, ultimaFila, ultimaColumna, 1, 1) || // Abajo-derecha
				checkDirection(jugadorFicha, ultimaFila, ultimaColumna, -1, -1)) { // Arriba-izquierda
			return true;
		}

		// Comprobar diagonalmente (arriba-derecha a abajo-izquierda)
		if (checkDirection(jugadorFicha, ultimaFila, ultimaColumna, 1, -1) || // Abajo-izquierda
				checkDirection(jugadorFicha, ultimaFila, ultimaColumna, -1, 1)) { // Arriba-derecha
			return true;
		}

		return false;
	}

	private boolean checkDirection(Ficha ficha, int startRow, int startCol, int dRow, int dCol) {
		int count = 0;
		int currentRow = startRow;
		int currentCol = startCol;

		// Contar hacia atrás desde la ficha inicial
		while (isValid(currentRow, currentCol) && tablero[currentRow][currentCol] == ficha) {
			count++;
			currentRow -= dRow;
			currentCol -= dCol;
		}

		// Reiniciar y contar hacia adelante desde la ficha inicial
		count = 0;
		currentRow = startRow;
		currentCol = startCol;
		while (isValid(currentRow, currentCol) && tablero[currentRow][currentCol] == ficha) {
			count++;
			currentRow += dRow;
			currentCol += dCol;
		}

		return count >= 4;
	}

	private boolean isValid(int fila, int columna) {
		return fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS;
	}

	public boolean estaLleno() {
		for (int j = 0; j < COLUMNAS; j++) {
			if (tablero[0][j] == Ficha.VACIA) {
				return false;
			}
		}
		return true;
	}
}

package juego1;


public class Tablero {

	public int[][] tablero;
	public int posPiratai = 0, posPirataj = 0;
	public int posTesoroi = 0, posTesoroj = 0;
	public int temp = 0;
	public int vidasPerdidas = 0;
	public int numMov = 0;

	public Tablero() {
	}

	public void inicializarTablero(int dificultad) {
		tablero = new int[dificultad][dificultad];
		vidasPerdidas = 0;
		numMov = 0;

		for (int i = 0; i < dificultad; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				tablero[i][j] = 0; //el cero representa la arena
			}
		}

		// Agua (bordes) representado por 1
		for (int i = 0; i < dificultad; i++) {
			tablero[i][0] = 1;
			tablero[0][i] = 1;
			tablero[i][dificultad - 1] = 1;
			tablero[dificultad - 1][i] = 1;
		}

		// Posición del pirata
		
		do {
			posPiratai = 1 + (int) (Math.random() * (dificultad - 2)); 
			//el multiplicar al randomico la dificultad menos 2 ayuda a identificar una posición central para evitar que toque los bordes superior e inferior
			posPirataj = 1 + (int) (Math.random() * (dificultad - 2));
			//algo parecido pasa con la operación correspondiente a posPirataj
		} while (tablero[posPiratai][posPirataj] != 0);

		tablero[posPiratai][posPirataj] = 2; //el 2 representa al pirata

		// Posición del tesoro
		do {
			posTesoroi = 1 + (int) (Math.random() * (dificultad - 2));
			posTesoroj = 1 + (int) (Math.random() * (dificultad - 2));
		} while ((posTesoroi == posPiratai && posTesoroj == posPirataj) || tablero[posTesoroi][posTesoroj] != 0);

		tablero[posTesoroi][posTesoroj] = 3; //el 3 representa el tesoro
	}

	public int[][] getTablero() {
		return tablero;
	}

	public void setTablero(int[][] tablero) {
		this.tablero = tablero;
	}

	public int getPosPiratai() {
		return posPiratai;
	}

	public void setPosPiratai(int posPiratai) {
		this.posPiratai = posPiratai;
	}

	public int getPosPirataj() {
		return posPirataj;
	}

	public void setPosPirataj(int posPirataj) {
		this.posPirataj = posPirataj;
	}

	public int getPosTesoroi() {
		return posTesoroi;
	}

	public void setPosTesoroi(int posTesoroi) {
		this.posTesoroi = posTesoroi;
	}

	public int getPosTesoroj() {
		return posTesoroj;
	}

	public void setPosTesoroj(int posTesoroj) {
		this.posTesoroj = posTesoroj;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getVidasPerdidas() {
		return vidasPerdidas;
	}

	public void setVidasPerdidas(int vidasPerdidas) {
		this.vidasPerdidas = vidasPerdidas;
	}

	public int getNumMov() {
		return numMov;
	}

	public void setNumMov(int numMov) {
		this.numMov = numMov;
	}
	
	
}
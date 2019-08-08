package com.udb.algoritmos;

import java.util.Scanner;

public class Vigenere {

//	public static void main(String[] args) {
//		Scanner leer = new Scanner(System.in);
//		String cadena;
//		char opc;
//		do {
//			System.out.println("Escoge una opccion" + "\n1.-Cifrar Mensaje" + "\n2.-Descifrar Mensaje" + "\n0.-Salir");
//			opc = leer.nextLine().charAt(0);
//
//			if (opc == '1') {
//				System.out.println("Introduce el mensaje a cifrar");
//				cadena = leer.nextLine();
//				System.out.println(Cifrar(cadena));
//			} else if (opc == '2') {
//				System.out.println("Introduce el mensaje a descifrar");
//				cadena = leer.nextLine();
//				System.out.println(Descifrar(cadena));
//			}
//		} while (opc != '0');
//
//	}
	
	public boolean claveCorrecta(String clave) {
		
		try {
			int claveNumerica = Integer.parseInt(clave);
			return false;
		}catch(Exception e) {
			return true;
		}
		
		
	}

	public String Cifrar(String clave, String Mensaje) {
		String salida = "";
		String Abcedario = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
		char[] claveEquals = new char[Mensaje.length()];
		char[] Msj = Mensaje.toUpperCase().toCharArray();
		int cont = 0;
		for (int c = 0; c < Mensaje.length(); c++) {

			if (Mensaje.charAt(c) == ' ') {
				c++;
			}
			claveEquals[c] = clave.charAt(cont);
			cont++;
			if (cont == clave.length()) {
				cont = 0;
			}
		} //
		int x = 0, y = 0, z;
		for (int c = 0; c < Mensaje.length(); c++) {
			if (Mensaje.charAt(c) == ' ') {
				salida += " ";
				c++;
			}
			for (int f = 0; f < Abcedario.length(); f++) {
				if (Msj[c] == Abcedario.charAt(f)) {
					x = f;

				}
				if (claveEquals[c] == Abcedario.charAt(f)) {
					y = f;

				}

			}
			z = (x + y) % 27;
			salida += Abcedario.charAt(z);

		}

		return salida;
	}

	public static String Descifrar(String clave, String Mensaje) {
		String salida = "";
		String Abcedario = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
		//String clave = "LOUP";
		
		char[] claveEquals = new char[Mensaje.length()];
		char[] Msg = Mensaje.toUpperCase().toCharArray();
		int cont = 0;
		for (int c = 0; c < Mensaje.length(); c++) {

			if (Mensaje.charAt(c) == ' ') {
				c++;
			}
			claveEquals[c] = clave.charAt(cont);
			cont++;
			if (cont == clave.length()) {
				cont = 0;
			}
		}
		cont = 0;
		int x = 0, y = 0, z, t;
		for (int c = 0; c < Mensaje.length(); c++) {
			if (Mensaje.charAt(c) == ' ') {
				salida += " ";
				c++;
			}
			for (int f = 0; f < Abcedario.length(); f++) {
				if (Msg[c] == Abcedario.charAt(f)) {
					x = f;

				}
				if (claveEquals[c] == Abcedario.charAt(f)) {
					y = f;

				}

			}
			z = (y - x);

			if (z <= 0) {
				if (z == 0) {
					salida += "A";
				} else {
					for (int j = 1; j <= Abcedario.length(); j++) {
						cont++;
						if (cont == (z * -1)) {
							salida += Abcedario.charAt(j);
							break;
						}
					}
				}

			} else {
				for (int i = 26; i >= 0; i--) {
					cont++;
					if (cont == z) {
						salida += Abcedario.charAt(i);
						break;
					}
				}
			}

			cont = 0;

		}

		return salida;
	}

}

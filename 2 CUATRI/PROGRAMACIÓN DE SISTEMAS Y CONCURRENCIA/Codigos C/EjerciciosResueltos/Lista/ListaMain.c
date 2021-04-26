/*
 * ListaMain.c
 *
 *  Created on: 28/1/2020
 *      Author: usuario
 */
#include <stdio.h>
#include <Lista.h>

int main(){
	T_Lista l,l2;

	Crear(&l);
	Crear(&l2);

	Rellenar(&l);
	Mostrar(l);
	EscribirF("p1.txt",l);
	printf("\n");

	LeerDeFichero("p1.txt",&l2);
	Mostrar(l2);

	Destruir(&l);
	Destruir(&l2);

	return 0;
}


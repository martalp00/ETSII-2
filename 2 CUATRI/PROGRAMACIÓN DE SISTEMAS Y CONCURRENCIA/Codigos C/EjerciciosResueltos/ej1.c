/*
 * ej1.c
 *
 *  Created on: 8/2/2020
 *      Author: usuario
 */


#include <stdio.h>
#include <stdlib.h>

//Ej1. Paso de parámetros por referencia en C
void funcEj1(int *pa) {
	printf("Valor ej1 = %d\n",*pa);
}

int main(int argc, char *argv[]){
	int valor = 3;

	funcEj1(&valor);

	return 0;
}

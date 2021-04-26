/*
 * ej2.c
 *
 *  Created on: 8/2/2020
 *      Author: usuario
 */

#include <stdio.h>
#include <stdlib.h>

//Ej2. Paso de parámetros por referencia en C
void intercambiar(int* pa,int* pb) {
	int temp;

	temp=*pa;
	*pa=*pb;
	*pb=temp;
}

int main(int argc, char *argv[]){
	int valor1 = 3;
	int valor2 = 5;

	printf("Valores originales = %d %d\n",valor1,valor2);
	intercambiar(&valor1,&valor2);
	printf("Valores originales = %d %d\n",valor1,valor2);

	return 0;
}

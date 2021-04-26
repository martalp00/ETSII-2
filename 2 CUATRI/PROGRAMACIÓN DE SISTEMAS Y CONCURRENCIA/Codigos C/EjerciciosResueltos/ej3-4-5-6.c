/*
 * ej3.c
 *
 *  Created on: 8/2/2020
 *      Author: usuario
 */

#include <stdio.h>
#include <stdlib.h>

const int MAX = 5;

//Ej3y4. Zonas de memoria referenciadas medante punteros
void leerEnteros(int *m,int nelem) {
	int i;

	printf("Introduzca %d valores enteros: ", nelem);
	fflush(stdout);

	for (i=0;i<nelem;i++)
		scanf("%d",&m[i]);
}

//Ej5y6. Arrays pasados como punteros
void mostrarEnteros(int *m,int nelem) {
	int i;

	for (i=0;i<nelem;i++)
		printf("%d ",m[i]);
	printf("\n");
}

int main(int argc, char *argv[]){
	int nelem;
	int array[MAX];
	int *memDin;

	//Ej3y5. Array creado de forma estática
	leerEnteros(array,5);
	mostrarEnteros(array,5);

	//Ej4y6. Zona de memoria creada de forma dinámica
	printf("\nIntroduzca numero elementos en array: ");
	fflush(stdout);
	scanf("%d",&nelem);

	memDin = (int *)malloc(sizeof(int)*nelem);

	if (memDin==NULL)
		perror("Error en peticion de memoria");
	else {
		leerEnteros(memDin,nelem);
		mostrarEnteros(memDin,nelem);
	}

	return 0;
}

/*
 * Principal.c
 *
 *  Created on: 14/6/2016
 *      Author: esc
 */

#include "ListaJugadores.h"
#include <stdio.h>



// Lee el fichero y lo introduce en la lista
void cargarFichero(char * nombreFich, TListaJugadores *lj)
{
	FILE *f = fopen(nombreFich,"rb");
	if (f == NULL) perror("error al abrir el fichero");
	else
	{
		unsigned int datos[3];
		while (fread(datos,sizeof(unsigned int),3,f)==3)
		{
			insertar(lj,datos[1]);
		}
	}
}


int main()
{

	TListaJugadores lj;
	crear(&lj);
    unsigned int num_goles;
	cargarFichero ("goles.bin",&lj);
	printf("Hay un total de %d jugadores\n",longitud(lj));
	fflush(stdout);

	recorrer(lj);
	fflush(stdout);
	printf("Introduce un n�mero de goles: \n");
	fflush(stdout);
	scanf("%d",&num_goles);


	eliminar(&lj,num_goles);
	printf("--------------------------------------\n");
	recorrer(lj);
	printf("Hay un total de %d jugadores\n",longitud(lj));
	fflush(stdout);

	printf ("El jugador que mas goles ha marcado es el que tiene ID: %d\n",maximo(lj));
	fflush(stdout);
	recorrer(lj);
	destruir(&lj);
	printf("fin");
	return 0;
}


int main1()
{
	TListaJugadores lj;
	crear(&lj);
	insertar(&lj,8);
	insertar(&lj,8);
	insertar(&lj,9);
	insertar(&lj,7);
	insertar(&lj,3);
	insertar(&lj,3);
	insertar(&lj,3);
	recorrer(lj);
	eliminar(&lj,1);
	recorrer(lj);
	printf("Hay un total de %d jugadores\n",longitud(lj));
	printf("Maximo %d\n",maximo(lj));
	destruir(&lj);
	return 0;
}

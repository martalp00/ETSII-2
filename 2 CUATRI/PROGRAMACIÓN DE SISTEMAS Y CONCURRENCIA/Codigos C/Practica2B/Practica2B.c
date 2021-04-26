/*
 ============================================================================
 Name        : Practica2B.c
 Author      : MARTA LÓPEZ PÉREZ
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "arbolbb.h"

/**
 * Pide un número "tam" al usuario, y
 * crea un fichero binario para escritura con el nombre "nfichero"
 * en que escribe "tam" numeros (unsigned int) aleatorios
 * Se utiliza srand(time(NULL)) para establecer la semilla (de la libreria time.h)
 * y rand()%100 para crear un número aleatorio entre 0 y 99.
 */
void creafichero(char* nfichero)
{
	int tam, n;

	FILE *ptr = fopen(nfichero,"w");

	if (ptr == NULL)
	{
		perror("No se ha podido abrir el fichero");
	}
	else
	{
		printf("Introduzca una cantidad de números");
		fflush(stdout);
		scanf("%d", &tam);//Introducir cantidad de numeros
		srand(time(NULL));

		for (int i = 0; i < tam; i++)
		{
			n = rand()%100;
			fwrite(&n,sizeof(int),1,ptr);
		}

		fclose(ptr);
	}
}
/**
 * Muestra por pantalla la lista de números (unsigned int) almacenada
 * en el fichero binario "nfichero"
 */
void muestrafichero(char* nfichero)
{
	FILE *ptr = fopen(nfichero,"r");

	if (ptr == NULL)
	{
		perror("No se ha podido abrir el fichero");
	}
	else
	{
		int num;

		while (fread(&num,sizeof(int),1,ptr)==1)
		{
			printf("%d ",num);
		}
		fclose(ptr);
	}
}

/**
 * Guarda en el arbol "*miarbol" los números almacenados en el fichero binario "nfichero"
 */

void cargaFichero(char* nfichero, T_Arbol* miarbol)
{
	FILE *ptr = fopen(nfichero,"r");


	if (ptr == NULL)
	{
		perror("No se ha podido abrir el fichero");
	}
	else
	{
		int num;

		while (fread(&num,sizeof(int),1,ptr)==1)
		{
			Insertar(miarbol, num);
		}
		fclose(ptr);
	}
}

int main() {
	char nfichero[50];
	printf ("Introduce el nombre del fichero binario:\n");
	fflush(stdout);
	scanf ("%s",nfichero);
	fflush(stdin);
	creafichero(nfichero);
	printf("\n Ahora lo leemos y mostramos\n");
	muestrafichero(nfichero);
	fflush(stdout);

	printf ("\n Ahora lo cargamos en el arbol\n");
	T_Arbol miarbol;
	Crear (&miarbol);
	cargaFichero(nfichero,&miarbol);
	printf ("\n Y lo mostramos ordenado\n");
	Mostrar(miarbol);
	fflush(stdout);
	printf("\n Ahora lo guardamos ordenado\n");
	FILE * fich;
	fich = fopen (nfichero, "wb");
	Salvar (miarbol, fich);
	fclose (fich);
	printf("\n Y lo mostramos ordenado\n");
	muestrafichero(nfichero);
	Destruir (&miarbol);

	return EXIT_SUCCESS;
}

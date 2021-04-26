/*
 * ficheros.c
 *
 *  Created on: 12 mar. 2020
 *      Author: mmar
 */

#include <stdio.h>
#include <stdlib.h>

void creaFichero(char * nombre)
{
	FILE *ptr = fopen(nombre,"w");
	if (ptr == NULL)
	{
		perror("no se ha podido abrir el fichero");
	}
	else
	{
		fprintf(ptr,"Esto es un ejemplo");
		fclose(ptr);
	}
}

void leerFichero(char *nombre)
{
	FILE *ptr = fopen(nombre,"r");

	if (ptr == NULL)
	{
		perror("no se ha podido abrir el fichero");
	}
	else
	{
		char cadena[30];
		while (fscanf(ptr,"%s",cadena)==1)
		{
			printf("%s\n",cadena);
		}
		fclose(ptr);
	}
}

void leerFichero2(char *nombre)
{
	FILE *ptr = fopen(nombre,"r");

	if (ptr == NULL)
	{
		perror("no se ha podido abrir el fichero");
	}
	else
	{
		char cadena[30];
		int edad;
		while (fscanf(ptr,"%s %d",cadena,&edad)==2)
		{
			printf("Nombre: %s, edad:%d \n",cadena,edad);
		}
		fclose(ptr);
	}
}

void escribirFicheroBinario(char *nombre)
{
	FILE *ptr = fopen(nombre,"w");

	if (ptr == NULL)
	{
		perror("no se ha podido abrir el fichero");
	}
	else
	{
		int num;
		int i;

		for (i=0;i<100; i++)
		{
			num = rand()%100;
			fwrite(&num,sizeof(int),1,ptr);
		}
		fclose(ptr);
	}
}

void leerFicheroBinario(char *nombre)
{
	FILE *ptr = fopen(nombre,"r");

	if (ptr == NULL)
	{
		perror("no se ha podido abrir el fichero");
	}
	else
	{
		int num;
		while (fread(&num,sizeof(int),1,ptr)==1)
		{
			printf("%d ",num);
		}
		printf("\n");
		fclose(ptr);
	}
}

int main(){
	/*creaFichero("ejemplo.txt");
	leerFichero("ejemplo.txt");
	leerFichero2("Personas.txt");*/
	escribirFicheroBinario("num.bin");
	leerFicheroBinario("num.bin");
	return 0;
}

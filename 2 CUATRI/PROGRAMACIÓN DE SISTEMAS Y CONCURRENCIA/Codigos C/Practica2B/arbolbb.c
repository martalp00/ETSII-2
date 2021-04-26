/*
 * arbolbb.c
 *
 *  Created on: 22/03/2020
 *  Author: MARTA LOPEZ PEREZ
 */

#include "arbolbb.h"
#include <stdio.h>
#include <stdlib.h>

void Crear(T_Arbol* arbol)
{
	*arbol = NULL;
}

void Destruir(T_Arbol* arbol)
{
	if (*arbol!=NULL)
	{
		Destruir(&((*arbol)->izq));
		Destruir(&((*arbol)->der));
		free(*arbol);
		*arbol = NULL;
	}
}

void Insertar(T_Arbol* arbol,unsigned num)
{
	if (*arbol == NULL)
	{
		*arbol = (T_Arbol) malloc(sizeof(struct T_Nodo));//Con esto reservamos la cantidad de memoria necesaria para crear el arbol
		(*arbol)->dato = num;//num es el unico nodo que tenemos en el arbol
		(*arbol)->izq = NULL;
		(*arbol)->der = NULL;
	}
	else
	{
		if ((*arbol)->dato > num)
		{
			Insertar(&((*arbol)->izq),num);
		}
		else if ((*arbol)->dato < num)
		{
			Insertar(&((*arbol)->der),num);
		}
	}
}

void Mostrar(T_Arbol arbol)
{
	if (arbol->izq != NULL)
	{
		Mostrar(arbol->izq);
	}

	printf("%d\n", arbol->dato);

	if (arbol->der != NULL)
	{
		Mostrar(arbol->der);
	}
}

void Salvar(T_Arbol arbol, FILE* fichero)
{
	if (arbol->izq != NULL)
	{
		Salvar((arbol->izq), fichero);
	}
	fwrite(&(arbol->dato),sizeof(int),1,fichero);

	if (arbol ->der != NULL)
	{
		Salvar((arbol->der),fichero);
	}

}

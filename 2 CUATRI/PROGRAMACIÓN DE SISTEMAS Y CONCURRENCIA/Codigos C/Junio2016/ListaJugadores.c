/*
 * ListaJugadores.c
 *
 *  Created on: 24 abr. 2020
 *      Author: mmar
 */

#include "ListaJugadores.h"
#include <stdlib.h>
#include <stdio.h>

//crea una lista vac�a (sin ning�n nodo)
void crear(TListaJugadores *lc)
{
	*lc = NULL;
}

//inserta un nuevo jugador en la lista de jugadores, poniendo 1
//en el n�mero de goles marcados.
//Si ya existe a�ade 1 al n�mero de goles marcados.

TListaJugadores crearNodo(int id)
{
	TListaJugadores aux = malloc(sizeof(struct T_Nodo));
	aux->numjugador = id;
	aux->numgoles = 1;
	aux->sig = NULL;
	return aux;
}

void insertar(TListaJugadores *lj,unsigned int id)
{
	TListaJugadores ant = NULL,act = *lj;
	TListaJugadores aux = NULL;

	while (act!=NULL && act->numjugador<id)
	{
		ant = act;
		act = act->sig;
	}
	if (ant == NULL)
	{
		if (act == NULL)
		{//lista vacia
			*lj = crearNodo(id); //es el primer nodo
		}
		else
		{
			//estoy en el primer nodo
			if (act->numjugador==id)
			{
				act->numgoles++;
			}
			else
			{
				aux = crearNodo(id); //lo inserto como primer nodo
				aux->sig = *lj;
				*lj = aux;
			}
		}
	}
	else if (act == NULL)
	{ //el nuevo nodo va al final
		ant->sig = crearNodo(id);
	}
	else
	{ //ant !=NULL y act!=NULL
		if (act->numjugador == id) act->numgoles++;
		else
		{
			aux = crearNodo(id);
			aux->sig = act;
			ant->sig = aux;
		}
	}
}

//recorre la lista circular escribiendo los identificadores y
//los goles marcados
void recorrer(TListaJugadores lj)
{
	while (lj!=NULL)
	{
		printf("Jugador %d, goles %d \n",lj->numjugador,lj->numgoles);
		lj=lj->sig;
	}
	printf("\n\n");
}

//devuelve el n�mero de nodos de la lista
int longitud(TListaJugadores lj)
{
	int lon = 0;
	while (lj!=NULL)
	{
		lj = lj->sig;
		lon++;
	}
	return lon;
}

//Eliminar. Toma un n�mero de goles como par�metro y
//elimina todos los jugadores que hayan marcado menos que ese n�mero de goles
void eliminar(TListaJugadores *lj,unsigned int n)
{
	TListaJugadores ant = NULL, act = *lj;
	while (act!=NULL)
	{
		if (act->numgoles< n)
		{
			if (ant == NULL)
			{ //es el primer nodo
				*lj = act->sig;
				free(act);
				act = *lj;
			} else
			{
				ant->sig = act->sig;
				free(act);
				act = ant->sig;
			}
		} else
		{
			ant = act;
			act = act->sig;
		}
	}
}


// Devuelve el ID del m�ximo jugador.
//Si la lista est� vac�a devuelve 0.
//Si hay m�s de un jugador con el mismo n�mero de goles que el m�ximo
//devuelve el de mayor ID
// Hay que devolver el identificador, no el n�mero de goles que ha marcado
unsigned int maximo(TListaJugadores lj)
{
	int maxgoles = 0;
	int idMaxGoleador = 0;
	while (lj!=NULL)
	{
		if (lj->numgoles>maxgoles)
		{
			maxgoles = lj->numgoles;
			idMaxGoleador = lj->numjugador;
		} else if (lj->numgoles==maxgoles)
		{
			idMaxGoleador = lj->numjugador;
		}
		lj = lj->sig;
	}
	return idMaxGoleador;
}

//Destruye la lista y libera la memoria)
void destruir(TListaJugadores *lj)
{

	TListaJugadores aux = *lj;
	while (*lj!=NULL)
	{
		aux = *lj;
		*lj = aux->sig;
		free(aux);
	}
}



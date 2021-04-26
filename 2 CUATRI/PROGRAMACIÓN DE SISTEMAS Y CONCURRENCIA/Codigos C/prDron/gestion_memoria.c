/*
 * gestion_memoria.c
 *
 *  Created on: 12/03/2020
 *  Author: MARTA LOPEZ PEREZ
 */

#include "gestion_memoria.h"
#include <stdlib.h>
#include <stdio.h>

/* Crea la estructura utilizada para gestionar la memoria disponible. Inicialmente, sólo un nodo desde 0 a MAX */
	void crear(T_Manejador* manejador)
	{
		T_Manejador aux = (T_Manejador)malloc(sizeof(struct T_Nodo));
		aux->inicio = 0;
		aux->fin = 999;
		aux->sig = NULL;
		*manejador = aux;
	}

/* Destruye la estructura utilizada (libera todos los nodos de la lista. El parámetro manejador debe terminar apuntando a NULL */
	void destruir(T_Manejador* manejador)
	{
	/*	
		if ((*manejador)!=NULL)
		{
			//llamada recursiva
			destruir(&((*manejador)->sig));

			//destruir nodo apuntadp
			free(*manejador);
			(*manejador)=NULL;
		}
	*/
		
		T_Manejador aux;

		while((*manejador)!=NULL)
		{
			aux=*manejador;		//AQUI LO QUE SE HACE QUE UN PUNTERO AUX TENGA LA DIRECCION A LA QUE APUNTA MANEJADOR
			*manejador=(*manejador)->sig;
			free(aux);
		}
	}

/* Devuelve en “dir” la dirección de memoria “simulada” (unsigned) donde comienza el trozo de memoria continua de tamaño “tam” solicitada.
Si la operación se pudo llevar a cabo, es decir, existe un trozo con capacidad suficiente, devolvera TRUE (1) en “ok”; FALSE (0) en otro caso.
 */
	void obtener(T_Manejador *manejador, unsigned tam, unsigned* dir, unsigned* ok)
	{
		T_Manejador act = *manejador, ant = NULL;
		unsigned enc = 0;
		while (!enc && act != NULL)
		{
			if(act->fin - act->inicio + 1 >= tam)
			{
				enc = 1;
			}
			else
			{
				ant = act;
				act = act->sig;
			}
		}
		*ok = enc;
		if(enc)
		{
			*dir = act->inicio;
			act->inicio = act->inicio + tam;
			if(act->inicio > act->fin) // hay que eliminar el bloque si se cruzan
			{
				if(ant == NULL)
				{
					*manejador = act->sig;
				}
				else
				{
					ant->sig = act->sig;
				}
				free(act);
			}
		}
	}

/* Muestra el estado actual de la memoria, bloques de memoria libre */
	void mostrar (T_Manejador manejador)
	{
		T_Manejador aux = manejador;
		while(aux!=NULL)
		{
			printf("[%d,%d]\n", aux->inicio, aux->fin);
			aux = aux->sig;
		}
		printf("*************\n");
	}

/* Devuelve el trozo de memoria continua de tamaño “tam” y que
 * comienza en “dir”.
 * Se puede suponer que se trata de un trozo obtenido previamente.
 */
	void devolver(T_Manejador *manejador,unsigned tam,unsigned dir)
	{
		T_Manejador ptr,ant,aux;

		/* Buscar posicion a insertar */
		ptr=*manejador;
		ant=NULL;

		while ((ptr != NULL) && (ptr->inicio < dir))
		{
			ant=ptr;
			ptr=ptr->sig;
		}

		if (ant==NULL)
		{	/* Insertar al comienzo de la lista */
			aux=(T_Manejador)malloc(sizeof(struct T_Nodo));
			aux->inicio=dir;
			aux->fin=dir+tam-1;
			aux->sig=*manejador;
			*manejador=aux;
		}
		else
		{
			/* Insertar en medio o al final de la lista */
			aux=(T_Manejador)malloc(sizeof(struct T_Nodo));
			aux->inicio=dir;
			aux->fin=dir+tam-1;
			aux->sig=ptr;
			ant->sig=aux;
		}
	}

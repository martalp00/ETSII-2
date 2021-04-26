/*
 * ListaCircular.c
 *
 *  Created on: 1 abr. 2020
 *      Author: mmar
 */

#include "ListaCircular.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

//crea una lista circular vac�a (sin ning�n nodo)
void crear(TListaCircular *lc){
	*lc = NULL;
}

//inserta un nuevo nodo con el dato nombre al final de la lista circular
void insertar(TListaCircular *lc,char *nombre){
	TListaCircular aux = (TListaCircular)malloc(sizeof(struct TNodo));
	//aux->nombre = nombre; //incorrecto
	strcpy(aux->nombre,nombre);
	if (*lc ==NULL){//lista vacía
		aux->sig = aux;
	} else{ //lista no vacía
		aux -> sig = (*lc)->sig;
		(*lc)->sig = aux;
	}
	*lc = aux;
}

//recorre la lista circular escribiendo los nombres de los nodos en la
//pantalla
void recorrer(TListaCircular lc){
	if (lc != NULL){
		TListaCircular fin = lc;
		do{
			lc = lc -> sig;
			printf("%s\n",lc->nombre);

		}while(lc != fin);
	} else {
		printf("La lista está vacía");
	}

}

//devuelve el n�mero de nodos de la lista
int longitud(TListaCircular lc){
	int n = 0;
	if (lc != NULL){
		TListaCircular fin = lc;
		do{
			lc = lc -> sig;
			n++;
		}while(lc != fin);
	}
	return n;
}

//mueve el puntero exterto de la lista n nodos (siguiendo la direcci�n de la
//lista)
void mover(TListaCircular *lc,int n){
	if (*lc !=NULL){
		while (n>0){
			(*lc) = (*lc)->sig;
			n--;
		}
	}
}

//elimina el primer nodo de la lista, y devuelve el nombre que contiene
//a trav�s del par�metro nombre
void extraer(TListaCircular *lc,char *nombre){

	if (*lc!=NULL){
		TListaCircular aux = (*lc)->sig;
		strcpy(nombre,aux->nombre);
		if (aux->sig == aux){
			*lc = NULL;
		}else {
			(*lc)->sig = aux ->sig;
		}
		free(aux);
	}
}

/*
 * lista.c
 *
 *  Created on: 17 abr. 2017
 *      Author: Manuel
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "lista.h"

/*
 * Crea un a lista vacia
 */
void crear(Lista *l){
	*l = NULL;
}

/*
 * Compruba si un a lista esta vacia
 * Devuelve 0 si NO lo est�
 */
int lista_vacia(Lista l){
	return (l == NULL ? !0 : 0);
}

/*
 * Escribe en consola el contenido de una lista de palabras separadas por coma
 * l: lista enlazada de palabras
 */
void escribir(Lista l){
	escribir_fichero(stdout, l);
}

/*
 * Escribe en un fichero de salida el contenido de una lista de palabras separadas por coma
 * fp: Puntero a un objeto FILE que identifica el stream de salida
 * l: lista enlazada de palabras
 */
void escribir_fichero(FILE * fp, Lista l){
	while (l != NULL){
		fprintf(fp, "%s, ", l->palabra);
		l = l->sig;
	}
	fprintf(fp, "\n");
}

/*
 * Inserta una palabra al final de una lista enlazada.
 * No comprueba si la palabra existe, si se desea no repetir palabras
 * se debe utilizar buscar_palabra() y comprobar antes de invocar esta funci�n
 * palabra: la palabra que se desea insertar
 * l: lista enlazada de palabras
 */
void insertar(char* palabra, Lista* l){
	Lista ptrItem = (Lista)malloc(sizeof(struct item));
	strcpy(ptrItem->palabra, palabra);
	ptrItem->sig = NULL;
	if (*l == NULL){ /* lista vacia */
		*l = ptrItem;
	} else {
		Lista l_aux = *l; /* un ptr auxiliar para recorrer la lista */
		/* Buscar el ultimo item */
		while (l_aux->sig != NULL){
			l_aux = l_aux->sig;
		}
		/* l_aux apunta al ultimo item */
		l_aux->sig = ptrItem;
	}
}

/*
 * Elimina todos los items de la lista enlazada
 * Debe delvolver la memoria dinamica utilizada para cada uno de ellos
 * Para comprobar que se eliminan los items
 * escriba un mensaje por consola indicando la palabra de item que se va a eliminar
 * l: La lista enlazada que se desea eliminar
 */
void destruir(Lista* l){
	Lista ptrItem;

	while(*l != NULL){
		ptrItem = *l;
		*l = (*l)->sig;
		printf("Eliminando item %s\n", ptrItem->palabra);
		free(ptrItem);
	}
}

/*
 * Comprueba si una palabra esta en la lista enlazada
 * palabra: la palabra que se desea buscar
 * l: lista enlazada de palabras
 * Devuelve 0 si la palabra NO est� en la lista
 * Devuelve 1 si la palabra S� est� en la lista
 */
int buscar_palabra(char* palabra, Lista l){
	int res = -1;
	while(l != NULL && res != 0){
		res = strcmp(palabra, l->palabra); /* Ojo, strcmp devuelve 0 si son iguales */
		l = l->sig;
	}
	return (res==0? 1:0);
}

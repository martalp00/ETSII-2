/*
 ============================================================================
 Name        : Lista.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <Lista.h>
#include <stdio.h>
#include <stdlib.h>

// Crea la estructura utilizada
void Crear(T_Lista* lista) {
	*lista=NULL;
}

// Destruye la estructura utilizada.
void Destruir(T_Lista* lista) {
	T_Lista borrar;
	while (*lista != NULL) {
		borrar=(*lista);
		*lista = (*lista)->sig;
		free(borrar);
	}
}

// Insertar un elemento al principio.
void InsertarPrincipio(T_Lista *lista, unsigned valor) {
	T_Lista nuevo;

	//Creamos un nuevo nodo
	nuevo=(T_Lista)malloc(sizeof(struct T_Nodo)); // Comprobar si no NULL
	nuevo->num=valor;

	//Enlazamos el nuevo nodo con la lista, insertando al principio
	nuevo->sig = *lista;
	*lista = nuevo;
}

// Rellenar lista
void Rellenar (T_Lista *lista) {
	// El enunciado no dice que hay que hacer
	// La vamos a rellenar con 100 valores en orden decreciente
	int i=0;

	for (i=0;i<100;i++) {
		InsertarPrincipio(lista, i);
	}
}

//Mostrar la lista
void Mostrar (T_Lista lista) { //Se pasa el puntero a la lista por valor
	while (lista != NULL) {
		printf("%d ",lista->num);
		lista=lista->sig;
	}
}

//Escribir a fichero
void EscribirF(char *nombre,T_Lista lista) {
	FILE *fd;
	if ((fd=fopen(nombre,"wt"))==NULL) {
		perror("Error en creacion de fichero");
	}
	else {
		while (lista != NULL) {
			fprintf(fd,"%d ",lista->num);
			lista=lista->sig;
		}
		fclose(fd);
	}
}

//Leer desde fichero
void LeerDeFichero(char*nombre,T_Lista *l) {
	FILE *fd;
	int num,leidos;

	if ((fd=fopen(nombre,"rt"))==NULL) {
		perror("Error en apertura de fichero");
	}
	else {
		while (!feof(fd)) {
			leidos=fscanf(fd,"%d",&num);
			if (leidos>0) {
				InsertarPrincipio(l,num);
			}
		}
		fclose(fd);
	}
}

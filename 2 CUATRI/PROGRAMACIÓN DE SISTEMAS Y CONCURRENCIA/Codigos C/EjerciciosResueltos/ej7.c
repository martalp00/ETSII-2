/*
 * ej7.c
 *
 *  Created on: 8/2/2020
 *      Author: usuario
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const unsigned MAX = 30;

struct Info {
	char nombre[30];
	char apellidos[30];
	int nNotas;
	float* notas;
};

void leerInfo(struct Info* info){
	printf("Introduzca info:\n");

	printf("Nombre: "); fflush(stdout);
    scanf("%s",info->nombre); //Para cuando encuentra un separador
	getchar(); //Para leer el '\n'

	printf("Apellidos: "); fflush(stdout);
	fgets(info->apellidos,MAX,stdin); //lee el '\n'
	info->apellidos[strlen(info->apellidos)-1]='\0'; //Para eliminar '\n'

	printf("Numero de notas: "); fflush(stdout);
	scanf("%d",&(info->nNotas));

	if (info->nNotas > 0){
		info->notas = (float *)malloc(sizeof(float)*info->nNotas);
		if (info->notas != NULL){
			int i;
			for(i=0; i<info->nNotas; i++){
				scanf("%f",&(info->notas[i]));
			}
		}
	}else{
		info->notas = NULL;
	}

}

void mostrarInfo(const struct Info* info){
	printf("%s %s\n",info->nombre,info->apellidos);
	printf("Numero de notas = %d\n",info->nNotas);
	if (info->nNotas > 0){ //if (info->notas != NULL)
		int i;
		for (i=0; i< info->nNotas; i++){
			printf("%.2f ",info->notas[i]);
		}
	}
	fflush(stdout);
}

/*void liberarInfo(struct Info* info){
	//Primero liberamos la memoria asignada para las notas
	if (info->notas != NULL){
		free(info->notas);
	}
	//Luego liberamos la memoria asignada a info y lo ponemos a NULL
	free(info);
	info = NULL; //Esta sentencia no tiene efecto
}*/

void liberarInfo(struct Info** info){
	//Primero liberamos la memoria asignada para las notas
	if ((*info)->notas != NULL){
		free((*info)->notas);
	}
	//Luego liberamos la memoria asignada a info y lo ponemos a NULL
	free(*info);
	*info = NULL;
}

int main(){
	struct Info* ptrInfo;

	ptrInfo = (struct Info*)malloc(sizeof(struct Info));

	leerInfo(ptrInfo);
	mostrarInfo(ptrInfo);
	liberarInfo(ptrInfo);
	if (ptrInfo == NULL) { //Con la implementacion activa no entra aquí
		printf("\nMemoria liberada correctamente");
		fflush(stdout);
	}

	return 0;
}

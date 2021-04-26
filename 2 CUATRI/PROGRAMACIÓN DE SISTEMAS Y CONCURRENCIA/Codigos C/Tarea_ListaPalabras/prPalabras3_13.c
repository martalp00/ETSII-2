/*
 ============================================================================
 Name        : prPalabras3_13.c
 Author      : MFBertoa
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "lista.h"
#define MIN_LETRA (3)
#define MAX_LETRA (13)
#define NUM_TAMANO (MAX_LETRA - MIN_LETRA +1)


void escribir_salida(FILE * fp, Lista* lp);


int main1(void) {
	char *inputfilename = "Lorem_Ipsum.txt";
	FILE *inputfile;
	char *outputfilename = "Palabras3_13.txt";
	FILE *outputfile;
	char palabra[TAM_PAL+1];

	Lista lista_pals[NUM_TAMANO];

	setvbuf(stdout, NULL, _IONBF, 0);
	setvbuf(stderr, NULL, _IONBF, 0);

	/* Indicar archivo */
	if((inputfile = fopen(inputfilename, "r"))==NULL){
		printf("Error abriendo %s", inputfilename);
		return EXIT_FAILURE;
	}

	if((outputfile = fopen(outputfilename, "w"))==NULL){
		printf("Error abriendo %s", outputfilename);
		fclose(inputfile);
		return EXIT_FAILURE;
	}

	/* Incializar lista palabras */
	for (int i = 0; i < NUM_TAMANO; ++i) {
		 crear(&lista_pals[i]);
	}

	/* Leer palabras */
	while(fscanf(inputfile,"%30s", palabra) != EOF ){
		int length = strlen(palabra);
		if (length >= MIN_LETRA && length <= MAX_LETRA){
			if(!buscar_palabra(palabra, lista_pals[length-MIN_LETRA])){
				insertar(palabra, &lista_pals[length-MIN_LETRA]);
			}
		}
	}
	fclose(inputfile);

	/* escribir en consola */
	escribir_salida(stdout, lista_pals);

	/* escribir archivo */
	escribir_salida(outputfile, lista_pals);

	fclose(outputfile);
	return EXIT_SUCCESS;
}

void escribir_salida(FILE * fp, Lista* lp){
	for (int i = 0; i < NUM_TAMANO; ++i) {
		fprintf(fp, "Palabras de %i letras:\n", i+MIN_LETRA);
		if (lista_vacia(lp[i])){
			fprintf(fp, "No se han encontrado palabras de %i letras\n", i+MIN_LETRA);
		}
		escribir_fichero(fp, lp[i]);
		fprintf(fp, "\n");
	}
}

/*
 * TiposEstructurados6.c
 *
 *  Created on: 20 feb. 2019
 *      Author: mmar
 */

#include <stdio.h>
#include <string.h>
int main8(int argc, char ** args){
	int a[2]; //2 componentes, cada uno de 4 bytes (32*2 bits)
	double c[4]; //4 componentes, cada uno de 8 bytes (64*4 bits)
	char d[5]; //5 componentes, cada uno de 1 byte (8*5 bits)
	float f[3];
	//3 componentes, cada uno de 4 bytes (32*3 bits)

	a[0] = 5;
	f[1] = 4.0;
	c[2] = 14.7;
	d[4] = 'a';

	printf("nombre: %c, \t direccion:%d\n",'a',a);
	printf("nombre: %c, \t direccion:%d\n",'b',f);
	printf("nombre: %c, \t direccion:%d\n",'c',c);
	printf("nombre: %c, \t direccion:%d\n",'d',d);
	// no se comprueban las cotas de los arrays, por lo que pueden sobrepasarse
	printf("nombre: %s \t direccion:%d, valor:%d\n","a[0]",&a[0],a[0]);
	f[3] = 8.0;
	printf("nombre: %s \t direccion:%d, valor:%d\n","a[0]",&a[0],a[0]);
	//si nos salimos de la memoria tenemos un error en ejecución "segmentation fault"
	//f[33333]=0;
	//printf("%lf",f[33333]);
	return 0;
}

int main9(int argc,char ** args){
	//int a[2][3]; // 2 filas, 3 columnas, 6 componentes de 4 bytes (6*32 bits)
	char str[8];
	str[0] = 'H';
	str[1] = 'o';
	str[2] = 'l';
	str[3] = 'a';
	str[4] = '\0';//carácter 0 terminador de la cadena
	//str[4] = 0;
	//str[4] = (char) NULL;
	str[5] = 'M';
	printf("%s\n",str);

	//printf("%s\n",str[0]);

	int x;
	float f;
	char s[6];
	scanf("%d",&x);
	printf("%d\n",x);
	scanf("%f",&f);
	printf("%f\n",f);

	scanf("%s",s);
	printf("%s\n",s);
	return 0;
}

int main10(int argc, char ** args){
//funciones de librería para los string
	char c[15] = "Hola Mundo";
	printf("%s longitud:%ld\n",c,strlen(c));
	char d[17] = "Adiós Mundo";
	printf("%s iguales?:%d\n",c,strcmp(c,d));
	strcpy(c,d);
	printf("copiado:%s\n",c);
	char e[50];
	strcpy(e,c);
	strcat(e,d);
	printf("concatenado:%s\n",e);
	return 0;
}

int main11(int args, char **argc){
	char pBuscada[20];
	char palabra[20];
	printf("Busco la palabra: ");
	scanf("%s",pBuscada);
	while (1){
		printf("Introduce una cadena: (0 para terminar) ");
		scanf("%s",palabra);
		if (strcmp(palabra,"0")==0)
			break;
		if (strlen(palabra)<strlen(pBuscada))
			printf("%s es demasiado corta",palabra);
		else if (strcmp(palabra,pBuscada)==0){
			printf("Encontrada!!");
			break;
		}
		else if (strncmp(palabra,pBuscada,3)==0) //busca que coincidan los 3 primeros caracteres
			printf("Empiezan igual....");
		else
			printf("No es la palabra que estamos buscando");
	}
}

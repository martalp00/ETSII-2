/*
 * Ejemplo4.c
 *
 *  Created on: 19 feb. 2020
 *      Author: mmar
 */


#include <stdio.h>
#include <stdlib.h>

int main4(){
	//1100 0010 1110 1101 0100 0000 0000 0000

	unsigned char hex4[4];
	hex4[0]= 0Xc2;
	hex4[1]= 0Xed;
	hex4[2]= 0X40;
	hex4[3]= 0X00;
	printf("Cuatro caracteres sin signo: %d %d %d %d\n", hex4[0],hex4[1],hex4[2],hex4[3]);
	char hex5[4];
	hex5[0]= 0Xc2;
	hex5[1]= 0Xed;
	hex5[2]= 0X40;
	hex5[3]= 0X00;
	printf("Cuatro caracteres con signo: %d %d %d %d\n", hex5[0],hex5[1],hex5[2],hex4[3]);
	char hex3[4];
	hex3[0]= 0Xc2;
	hex3[1]= 0Xed;
	hex3[2]= 0X40;
	hex3[3]= 0X00;
	printf("Cuatro caracteres: %c %c %c %c\n", hex3[0],hex3[1],hex3[2],hex3[3]);
	int hex = 0Xc2ed4000;
	printf("Un entero con signo: %d\n",hex);
	printf("Un entero sin signo: %u\n",hex);

	float hex2 = *(float *)&hex;

	printf("Un float: %.3f\n",hex2);

	//printf("%d %d %d %d\n", hex4[0],hex4[1],hex4[2],hex4[3]);
	return EXIT_SUCCESS; //todo ha ido bien -0 está en stdlib.h
}

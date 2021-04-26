/*
 * TiposBasicos.c
 *
 *  Created on: 19 feb. 2019
 *      Author: mmar
 */

#include <stdio.h>
int main12(int argc,char ** args){
	int x,y;
	char a;
	float f,e;
	double d;

	x = 4;
	y = 7;
	a = 'H';
	f = -3.4;
	e = 54.123456789;
	d = 54.123456789;

	printf("%d %d %c %f %f %lf\n",x,y,a,f,e,d);
	printf("%d %d %c %f %.9f %.9lf\n",x,y,a,f,e,d);
	printf("Fin de la entrada");
	return 0;
}

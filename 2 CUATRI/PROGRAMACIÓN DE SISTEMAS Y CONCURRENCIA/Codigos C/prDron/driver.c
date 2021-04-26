#include "gestion_memoria.h"
#include <stdio.h>


int main(){
	T_Manejador m;
	unsigned dir,ok;
	crear(&m);
	mostrar(m);
	obtener(&m,5,&dir,&ok);
	if (ok) printf("se han podido obtener %u bytes a partir de la direccion %u\n", 5,dir);
	mostrar(m);
	obtener(&m,40,&dir,&ok);
	if (ok) printf("se han podido obtener %u bytes a partir de la direccion %u\n", 40,dir);
	mostrar(m);
	devolver(&m,5,0);
	mostrar(m);
	devolver(&m,1,5);
	mostrar(m);
	devolver(&m,5,40);
	mostrar(m);
	devolver(&m,34,6);
	mostrar(m);
	destruir(&m);
	mostrar(m);
	return 0;
}

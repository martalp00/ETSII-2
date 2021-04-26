/*------------------------------------------------------------------------------
Proyecto Shell de UNIX. Sistemas Operativos
Grados I. Informatica, Computadores & Software
Dept. Arquitectura de Computadores - UMA

Algunas secciones estan inspiradas en ejercicios publicados en el libro
"Fundamentos de Sistemas Operativos", Silberschatz et al.

Para compilar este programa: gcc ProyectoShell.c ApoyoTareas.c -o MiShell
Para ejecutar este programa: ./MiShell
Para salir del programa en ejecucion, pulsar Control+D
------------------------------------------------------------------------------*/

#include "ApoyoTareas.h" // Cabecera del modulo de apoyo ApoyoTareas.c

#define MAX_LINE 256 // 256 caracteres por linea para cada comando es suficiente
#include <string.h>  // Para comparar cadenas de cars. (a partir de la tarea 2)

// --------------------------------------------
//                     MAIN          
// --------------------------------------------

job * tareas;

void manejador_sigchld(int sig)
{
	int status, info, pid_wait;	// VARIABLES
	enum status estado;		
	job* trabajo = tareas;
	job *aux = NULL;
	block_SIGCHLD();
	trabajo = trabajo->next;
	
	while (trabajo != NULL) 
	{
		pid_wait = waitpid(trabajo->pgid, &status, WUNTRACED | WNOHANG);
		estado = analyze_status(status, &info);

		if (pid_wait == trabajo->pgid) 
		{
			printf("\nwait realizado a proceso en background: %s, pid: %i\n",
			trabajo->command, trabajo->pgid);

			if (estado == REANUDADO  || estado== FINALIZADO ) 
			{
				aux = trabajo->next;
				delete_job(tareas,trabajo);
				
				trabajo = aux;

			} 
			else if (estado == SUSPENDIDO) 
			{
				trabajo->ground = DETENIDO;
				trabajo = trabajo->next;

			}

		}
		else
		{
			trabajo=trabajo->next;
		}


	}

	unblock_SIGCHLD();
}

int main(void)
{
	char inputBuffer[MAX_LINE];	// Bufer que alberga el comando introducido
    int background;        		// Vale 1 si el comando introducido finaliza con '&'
    char *args[MAX_LINE/2];		// La linea de comandos (de 256 cars.) tiene 128 argumentos como max
                             	// Variables de utilidad:
    int pid_fork, pid_wait; 	// pid para el proceso creado y esperado
    int status;            		// Estado que devuelve la funcion wait
    enum status status_res;		// Estado procesado por analyze_status()
    int info,res;		      	// Informacion procesada por analyze_status()


	ignore_terminal_signals();
	tareas = new_list("lista_trabajo"); //3
	signal(SIGCHLD,manejador_sigchld);

	while (1)   // El programa termina cuando se pulsa Control+D dentro de get_command()
	{   		
		printf("COMMAND->");
		fflush(stdout);
		get_command(inputBuffer, MAX_LINE, args, &background); 		// Obtener el pr—ximo comando
		
		if(args[0]==NULL) continue;   			// Si se introduce un comando vacio, no hacemos nada

		if(strcmp(args[0],"cd") == 0)			// COMANDO CD
		{		
			if (args[1] == NULL)				// SI es CD "asecas" lleva al HOME
			{			
				chdir(getenv("HOME"));			
			}
			else								
			{					
				chdir(args[1]);
			}
			res = analyze_status(status,&info);

			if (info!=1)
			{		
				printf("Foreground pid: %i, command: %s, %s, info: %i \n",
				getpid(), args[0], status_strings[res], info); 
			}
			continue;
		}
		
		if(strcmp(args[0], "jobs") == 0)  // COMANDO JOBS
		{        
			if(list_size(tareas)==0)
			{
				printf("Empty list \n");
			}
			else
			{
				print_job_list(tareas);
			}
			continue;
		}
			
		if(!strcmp(args[0],"bg"))	//COMANDO BG
		{		
			int posicion;
			job * aux;

			if(args[1]==NULL)		//Si args[1] no existe, cogemos la pos 1
			{		
				posicion=1;
			}
			else
			{
				posicion=atoi(args[1]);
			}
			aux = get_item_bypos(tareas,posicion);

			if(aux==NULL)
			{
				printf("BG ERROR: JOB NOT FOUND \n");
			}
			else
			{
				if(aux->ground==DETENIDO)
				{					
					aux->ground=SEGUNDOPLANO;
					printf("Puesto en background el job %d que estaba suspendido, el job era: %s\n",posicion,aux->command);
					killpg(aux->pgid,SIGCONT);	
				}
			}
			continue;
		}
		
		if(!strcmp(args[0],"fg"))
		{
			int posicion;
			enum status statusfg;
			job * aux;

			if(args[1]==NULL)			//Si args[1] no existe, cogemos la pos 1
			{		
				posicion=1;
			}
			else
			{							
				posicion=atoi(args[1]);
			}
			aux=get_item_bypos(tareas,posicion);

			if(aux==NULL)
			{
				printf("FG ERROR: JOB NOT FOUND \n");
				continue;
			}
			if(aux->ground==DETENIDO || aux->ground==SEGUNDOPLANO)
			{
				printf("Puesto en foreground el job %d que estaba suspendido o en background, el job era: %s\n",posicion,aux->command);
				aux->ground=PRIMERPLANO;
				set_terminal(aux->pgid);	
				killpg(aux->pgid,SIGCONT); 				// manda una señal al grupo de proceso para que continue
				waitpid(aux->pgid,&status,WUNTRACED);
				set_terminal(getpid());
				statusfg=analyze_status(status,&info);

				if(statusfg==SUSPENDIDO)
				{
					aux->ground=DETENIDO;
				}
				else
				{
					delete_job(tareas,aux);
				}
			}
			else
			{
				printf("El proceso no estaba en background o suspendido");
			}
			
			continue;
		}
		if(strcmp(args[0], "logout")==0)
		{
			exit(0);
		}

		/* Los pasos a seguir a partir de aqui, son:

			(1) Genera un proceso hijo con fork()	*/

		pid_fork = fork();
 
		/*	(2) El proceso hijo invocar‡ a execvp() */
			
		if(pid_fork == -1)//Fork erroneo
		{	
			printf ("Failed fork");
			continue;
		}
			
		if(pid_fork == 0)// PROCESO HIJO
		{
			new_process_group(getpid());
			restore_terminal_signals();
			execvp(args[0],args);
			printf("ERROR: command not found %s\n",args[0]);
			printf("Foreground pid: %i, command: %s, %s, info: %i \n",
			getpid(), args[0], status_strings[res], info); 

			exit(EXIT_FAILURE);
		}
		else	// PROCESO PADRE 
		{													/* (3) El proceso padre esperara si background es 0; de lo contrario, "continue" */
			new_process_group(pid_fork);

			if(background == 0) 							//EL PROCESO ESTÁ EN FOREGROUND
			{	
				set_terminal(pid_fork);
				pid_wait = waitpid(pid_fork,&status,WUNTRACED);
				set_terminal(getpid());
				res = analyze_status(status,&info);

				if(res==SUSPENDIDO)//3
				{  
					job *auxi2 = new_job(pid_fork,args[0],DETENIDO);
					block_SIGCHLD();
					add_job(tareas,auxi2);
					unblock_SIGCHLD();
				}
				if (info!=1)											//3
				{  
					printf("Foreground pid: %i, command: %s, %s, info: %i \n",
					pid_fork, args[0], status_strings[res], info);
				}
			}
			else											//EL PROCESO PADRE ESTA EN BACKGROUND
			{ 			 
				job * aux = new_job(pid_fork,args[0],SEGUNDOPLANO);   	//3
				block_SIGCHLD(); 										//3
				add_job(tareas,aux); 									//3
				unblock_SIGCHLD();  									//3
					
				if (info!=1)
				{
					printf("Background job running... pid: %i, command: %s \n",
					pid_fork, args[0]); 
				}
			}	
		}
			 
		/* 	(4) El Shell muestra el mensaje de estado del comando procesado  */
			
			
				
		/* 	(5) El bucle regresa a la funci—n get_command() */
		

	}	 // end while
}



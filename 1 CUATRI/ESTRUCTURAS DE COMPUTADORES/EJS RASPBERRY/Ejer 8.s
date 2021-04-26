
	.include "inter.inc"

onoff: .word 0

.text

	ADDEXC 0x18, irq_handler @ Agregar vector de interrupcion
/* Preparar la pila en modos IRQ y SVC */
		mov 	r0, #0b11010010
		msr 	cpsr_c, r0
		mov 	sp, #0x8000
		mov 	r0, #0b11010011
		msr 	cpsr_c, r0
		mov 	sp, #0x8000000
/* ----------------------------------------- */
		ldr 	r0, =GPBASE
		mov 	r1, #0b00001000000000000000000000000000 @ GPIO9 como salida
		str 	r1, [r0, #GPFSEL0]

/* Habilitar interrupciones */
		ldr 	r0, =INTBASE
		mov 	r1, #0b0010
		str 	r1, [r0, #INTENIRQ1]

@ Interrupcion de contador

		mov 	r0, #0b01010011
		msr 	cpsr_c, r0
/* -------------------------------------------- */
		ldr 	r0, =STBASE
		ldr 	r1, [r0, #STCLO] 			@ Cargar en R1 el valor actual del contador
		ldr 	r2, =500000 				@ Cargar en R2 0,5 segundos
		add 	r1, r2						@ Sumar a R1 el tiempo de espera
		str 	r1, [r0, #STC1] 			@ Guardar el valor en C1 para que salte la excepcion
											@cuando se llegue a ese tiempo
buc: 
		b 		buc 						@ Bucle infinito
	
irq_handler:
		push 	{r0-r2} 					@ Guardar los registros R0-R2 en la pila
		ldr 	r0, =GPBASE
		ldr 	r1, =onoff					@ Almacenar en R1 la direccion de la variable onoff
		ldr 	r2, [r1] 					@ Almacenar en R2 el contenido de la variable
		eors	 r2, #1 					@ Invertir el valor actualizando los flags
		str 	r2, [r1] 					@ Guardarlo de nuevo en la variable
		
		mov 	r1, #0b00000000000000000000001000000000 @ Mascara para GPIO9
		streq 	r1, [r0, #GPCLR0] 			@ Si la variable onoff tenia un 1 se apaga GPIO9
		strne 	r1, [r0, #GPSET0] 			@Si la variable onoff tenia un 0 se enciende GPIO9
		ldr 	r0, =STBASE
		mov 	r1, #0b0010
		str 	r1, [r0, #STCS] 			@ Resetear el estado de interrupcion de C1
		
/* Volver a programar el contador */
		ldr 	r1, [r0, #STCLO]
		ldr 	r2, =500000
		add 	r1, r2
		str 	r1, [r0, #STC1]
		pop 	{r0-r2} 					@ Recuperar los registros R0-R2
		subs	 pc, lr, #4
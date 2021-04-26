	.include "inter.inc"

.text
		mov 	r0, #0
		ADDEXC 	0x18, irq_handler
/* Stack init for IRQ mode */
		mov 	r0, #0b11010010
		msr 	cpsr_c, r0
		mov 	sp, #0x8000
		/* Stack init for SVC mode*/
		mov 	r0, #0b11010011
		msr 	cpsr_c, r0
		mov 	sp, #0x8000000
/*Configuro el led rojo (GPIO9) como salida, y como entrada el GPIO2 Y
		GPIO3 	(pulsadores) */
		ldr 	r0, =GPBASE
		ldr   	r1,    =0b00000000001000000000000001000000
		str 	r1, [r0, #GPFSEL2]
		
/*Configuro el led rojo (GPIO10) como salida */
/*		ldr 	r1, =0b00000000000000000000000000000000*/
/*		str 	r1, [r0, #GPFSEL2]*/

/* Enable falling edge interruptions through GPIO2 y GPIO3 */
		mov 	r3, #0b00000000000000000000000000001100
		str 	r3, [r0, #GPFEN0]
		
/* Encendemos los dos leds (GPIO9 y GPIO10) y los mantenemos encendidos	*/
		mov 	r1, #0b00001000010000000000000000000000
		str 	r1, [r0, #GPSET0]	
		
/* Allow interruptions from any GPIO pin */
		ldr 	r0, =INTBASE
		mov 	r1, #0b00000000000100000000000000000000
		str 	r1, [r0, #INTENIRQ2]
		
/* Habilitar 1 flag para habilitar el IRQ y deshabilitar el FIQ */
//SVC mode, IRQ enabled
		mov 	r0, #0b01010011
		msr 	cpsr_c, r0
infi: 
		b 		infi
	
irq_handler:
		push 	{r0, r1, r2, r3, r4}
		ldr 	r0, =GPBASE
		
/* Check GPIO2 was pressed */
		ldr	 	r2, [r0, #GPEDS0]
//actualizamos los flags
		ands 	r2, #0b00000000000000000000000000000100
		
/* Encender el GPIO9 y apagar el GPIO10 */
		movne 	r1, #0b00000000010000000000000000000000
		strne 	r1, [r0, #GPSET0]
		movne 	r1, #0b00001000000000000000000000000000
		strne 	r1, [r0, #GPCLR0]
		
/* Limpiar el evento GPIO2*/
		movne 	r1, #0b00000000000000000000000000000100
		strne 	r1, [r0, #GPEDS0]
		
/* Check GPIO3 was pressed */
		ldr 	r4, [r0, #GPEDS0]
//actualizamos los flags.
		ands 	r4, #0b00000000000000000000000000001000
		
/* Encender el GPIO10 y apagar el GPIO9 */
	
		movne 	r3, #0b00001000000000000000000000000000
		strne 	r3, [r0, #GPSET0]
		movne 	r3, #0b00000000010000000000000000000000
		strne 	r3, [r0, #GPCLR0]
		
/* Limpiar el evento GPIO3*/
		movne 	r1, #0b00000000000000000000000000001000
		strne 	r1, [r0, #GPEDS0]
fin: 
		pop 	{r0, r1, r2, r3, r4}
		subs 	pc, lr, #4	
		
		
		
		
		
		
		
		
@EJERCICIO 10 OTRO MODO

       .include  "inter.inc"
.text
/* Agrego vector interrupcion */
        ADDEXC  0x18, irq_handler

/* Inicializo la pila en modos IRQ y SVC */
        mov     r0, #0b11010010   @ Modo IRQ, FIQ&IRQ desact
        msr     cpsr_c, r0
        mov     sp, #0x8000
        mov     r0, #0b11010011   @ Modo SVC, FIQ&IRQ desact
        msr     cpsr_c, r0
        mov     sp, #0x8000000

/* Configuro GPIOs 22 y 27 como salida */
        ldr     r0, =GPBASE
        ldr     r1, =0b00000000001000000000000001000000
        str     r1, [r0, #GPFSEL2]
	
/* Enciendo GPIOs 22 y 27 */
        ldr     r0, =GPBASE
/* guia bits           10987654321098765432109876543210*/
        mov     r1, #0b00001000010000000000000000000000
        str     r1, [r0, #GPSET0]

/* Habilito pines GPIO 2 y 3 (boton) para interrupciones*/
        mov     r1, #0b00000000000000000000000000001100
        str     r1, [r0, #GPFEN0]
        ldr     r0, =INTBASE

/* Habilito interrupciones, local y globalmente */
        mov     r1, #0b00000000000100000000000000000000
/* guia bits             10987654321098765432109876543210*/
        str     r1, [r0, #INTENIRQ2]
        mov     r0, #0b01010011   @ Modo SVC, IRQ activo
        msr     cpsr_c, r0

/* Repetir para siempre */
bucle:  b       bucle

/* Rutina de tratamiento de interrupcion */
irq_handler:
        push    {r0, r1, r2}
        ldr     r0, =GPBASE
	
/* Consulto si se ha pulsado el boton GPIO2 */
        ldr     r2, [r0, #GPEDS0]
	mov	r3, r2 
        ands    r2, #0b00000000000000000000000000000100
/* Si: Activo GPIO 22 y apago 27*/
/* guia bits              10987654321098765432109876543210*/
        movne   r1, #0b00000000010000000000000000000000
	strne   r1, [r0, #GPSET0]
/* guia bits              10987654321098765432109876543210*/
        movne   r1, #0b00001000000000000000000000000000
	strne   r1, [r0, #GPCLR0]
	
/* Desactivo el flag GPIO pendiente de atencion
/* guia bits              10987654321098765432109876543210*/
        movne   r1, #0b00000000000000000000000000000100	
	strne   r1, [r0, #GPEDS0]

	
/* Consulto si se ha pulsado el boton GPIO3 */
        ldr     r2, [r0, #GPEDS0]
	mov	r3, r2 
        ands    r2, #0b00000000000000000000000000001000
/* Si: Activo GPIO 27 y apago 22*/
/* guia bits              10987654321098765432109876543210*/
        movne   r1, #0b00001000000000000000000000000000
	strne   r1, [r0, #GPSET0]
/* guia bits              10987654321098765432109876543210*/
        movne   r1, #0b00000000010000000000000000000000
	strne   r1, [r0, #GPCLR0]
	
/* Desactivo el flag GPIO pendiente de atencion
/* guia bits              10987654321098765432109876543210*/
        movne   r1, #0b00000000000000000000000000001000	
	strne   r1, [r0, #GPEDS0]

   
fin:	pop     {r0, r1, r2}
        subs    pc, lr, #4

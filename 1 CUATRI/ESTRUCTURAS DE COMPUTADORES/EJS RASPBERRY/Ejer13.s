.include  "inter.inc"

.text
/* Agrego vector interrupcion */
	ADDEXC  0x18, irq_handler

/* Inicializo la pila en modos IRQ y SVC */
        mov  r0, #0b11010010   @ Modo IRQ, FIQ&IRQ desact
        msr  cpsr_c, r0
        mov  sp, #0x8000
        mov  r0, #0b11010011   @ Modo SVC, FIQ&IRQ desact
        msr  cpsr_c, r0
        mov  sp, #0x8000000

/* Configuro GPIOs 11, 17 como salida */
        ldr  r0, =GPBASE
/* guia bits        xx999888777666555444333222111000*/
	ldr  r1, =0b00000000000000000000000000000000
        str  r1, [r0, #GPFSEL0]
        ldr  r1, =0b00000000001000000000000000001000
        str  r1, [r0, #GPFSEL1]


/* Habilito pines GPIO 2 y 3 (botones) para interrupciones*/
        mov  r1, #0b00000000000000000000000000001100
        str  r1, [r0, #GPFEN0]

/* Habilito interrupciones, local y globalmente */		
        ldr  r0, =INTBASE
/* guia bits          10987654321098765432109876543210*/
        mov  r1, #0b00000000000100000000000000000000
        str  r1, [r0, #INTENIRQ2]
		
        mov  r0, #0b01010011   @ Modo SVC, IRQ activo
        msr  cpsr_c, r0


/* guia bits          10987654321098765432109876543210*/
        mov  r5, #0b00000000000000100000100000000000
        ldr  r0, =STBASE    	@ r0 es un parametro de espera (dir. base ST)
        ldr  r1, =500000	@ r1 es un parametro de espera (microsec.)
        ldr  r4, =GPBASE
        ldr  r3, =continuar
	
/* Repetir para siempre */
bucle:	
        bl      espera        	@ Salta a rutina de espera
	ldr    r6,[r3]
	cmp r6, #0
        streq   r5, [r4, #GPSET0]	@ enciende led
        bl      espera        	@ Salta a rutina de espera
        ldr    r6,[r3]
	cmp r6, #0
        streq   r5, [r4, #GPCLR0]	@apaga led
        b       bucle

/* rutina de espera */
espera:	
        push	{r4,r5}
        ldr     r4, [r0, #STCLO]	@ Lee contador en r4
        add   	r4, r1            	@ r4= r4+medio millon
ret1:   ldr    	r5, [r0, #STCLO]
        cmp  	r5, r4            	@ Leemos CLO hasta alcanzar
        blo    	ret1              	@ el valor de r4
	pop	{r4,r5}
        bx     	lr

/* Rutina de tratamiento de interrupcion */
/* Rutina de tratamiento de interrupcion */
irq_handler:
        push    {r0, r1, r2}
        ldr     r0, =GPBASE
	
/* Consulto si se ha pulsado el boton GPIO2 */
        ldr     r2, [r0, #GPEDS0]
	mov	r3, r2 
        ands    r2, #0b00000000000000000000000000000100
/* Si: Parpadea*/
	moveq r6, #1
	streq r6, [r3]
	
/* Desactivo el flag GPIO pendiente de atencion
/* guia bits              10987654321098765432109876543210*/
        movne   r1, #0b00000000000000000000000000000100	
	strne   r1, [r0, #GPEDS0]

	
/* Consulto si se ha pulsado el boton GPIO3 */
        ldr     r2, [r0, #GPEDS0]
	mov	r3, r2 
        ands    r2, #0b00000000000000000000000000001000
/* Si: Parada*/
	moveq r6, #0
	streq r6, [r3]

	
/* Desactivo el flag GPIO pendiente de atencion
/* guia bits              10987654321098765432109876543210*/
        movne   r1, #0b00000000000000000000000000001000	
	strne   r1, [r0, #GPEDS0]

   
fin:	pop     {r0, r1, r2}
        subs    pc, lr, #4

   
/* Variable global */
continuar: .word 0

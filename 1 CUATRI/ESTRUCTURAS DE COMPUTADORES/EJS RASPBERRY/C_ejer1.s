.include "inter.inc"
 
.text	
	
	@configuro todos los leds
        ldr     r0, =GPBASE
        ldr   	r1, = 0b00001000000000000000000000000000
        str	r1, [r0, #GPFSEL0]  @ Configura GPIO 9, 3 y 4
        ldr   	r1, = 0b00000000001000000000000000001001
        str	r1, [r0, #GPFSEL1]  @ Configura GPIO 10, 11, 17
	ldr   	r1, = 0b00000000001000000000000001000000
        str	r1, [r0, #GPFSEL2]  @ Configura GPIO 22, 27
	
	@enciende todos los leds de inicio
        ldr   	 r1, = 0b00001000010000100000111000000000
        str     r1, [r0, #GPSET0]   @ Enciende GPIO 9, 10, 11, 17, 22, 27

	@compruebo si he pulsado los pulsadores
bucle:	
	ldr	r1, [r0, #GPLEV0]
	ands	r2, r1, #0b00000000000000000000000000000100
	beq	pulsador1
	ands	r2, r1, #0b00000000000000000000000000001000
	beq	pulsador2
	b 	bucle

	@pulsador1 enciende solo los leds 9, 10 y 11 y apaga los 10, 17, 27
pulsador1:	
        ldr   	 r1, = 0b00000000010000000000101000000000
        str     r1, [r0, #GPSET0]   @ Enciende GPIO 9, 11, 22
        ldr   	r1, = 0b00001000000000100000010000000000
        str     r1, [r0, #GPCLR0]   @ Apaga GPIO 10, 17, 27
	b 	bucle
	
	@en el pulsador2 lo pongo todo a cero para que no haga nada, ni encienda ni apague
pulsador2:
        ldr   	r1, = 0b00000000000000000000000000000000
        str     r1, [r0, #GPSET0]   
        ldr   	r1, = 0b00000000000000000000000000000000
        str     r1, [r0, #GPCLR0]   
	b 	bucle

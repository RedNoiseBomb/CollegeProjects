#include "hardware/regs/addressmap.h"
#include "hardware/regs/io_bank0.h"
#include "hardware/regs/timer.h"
#include "hardware/regs/m0plus.h"

.syntax unified
.cpu    cortex-m0plus
.thumb
.global main_asm
.align  4

.equ    DFLT_STATE_STRT, 1            @ Specify the value to start flashing
.equ    DFLT_STATE_STOP, 0            @ Specify the value to stop flashing
.equ    DFLT_ALARM_TIME, 1000000      @ Specify the default alarm timeout

.equ    GPIO_BTN_DN_MSK, 0x00040000   @ Bit-18 for falling-edge event on GP20
.equ    GPIO_BTN_EN_MSK, 0x00400000   @ Bit-22 for falling-edge event on GP21
.equ    GPIO_BTN_UP_MSK, 0x04000000   @ Bit-26 for falling-edge event on GP22

.equ    GPIO_BTN_DN,  20              @ Specify pin for the "down" button
.equ    GPIO_BTN_EN,  21              @ Specify pin for the "enter" button
.equ    GPIO_BTN_UP,  22              @ Specify pin for the "up" button
.equ    GPIO_LED_PIN, 25              @ Specify pin for the built-in LED
.equ    GPIO_DIR_IN,   0              @ Specify input direction for a GPIO pin
.equ    GPIO_DIR_OUT,  1              @ Specify output direction for a GPIO pin

.equ    LED_VAL_ON,    1              @ Specify value that turns the LED "on"
.equ    LED_VAL_OFF,   0              @ Specify value that turns the LED "off"

.equ    GPIO_ISR_OFFSET, 0x74         @ GPIO is int #13 (vector table entry 29)
.equ    ALRM_ISR_OFFSET, 0x40         @ ALARM0 is int #0 (vector table entry 16)

@ Entry point to the ASM portion of the program
main_asm:
    BL      init_gpio_led
    BL      init_gp20_down
    BL      init_gp21_enter
    BL      init_gp22_up   
    BL      gpio_isr_install
    BL      install_alarm_isr
    LDR     R0, =DFLT_ALARM_TIME
    BL      alarm_set
main_loop:
    B       main_loop

@ Subroutine used to initialize the PI Pico built-in LED
init_gpio_led:
    PUSH {LR}
    MOVS R0, #GPIO_LED_PIN
    BL asm_gpio_init
    MOVS R0, #GPIO_LED_PIN
    MOVS R1, #GPIO_DIR_OUT
    BL asm_gpio_set_dir
    POP {PC}

@ Subroutine used to initialize the GP20 button
init_gp20_down:
    PUSH {LR}
    MOVS R0, #GPIO_BTN_DN
    BL asm_gpio_init
    MOVS R0, #GPIO_BTN_DN
    MOVS R1, #GPIO_DIR_IN
    BL asm_gpio_set_dir
    MOVS R0, #GPIO_BTN_DN
    BL asm_gpio_set_irq
    POP {PC}

@ Subroutine used to initialize the GP21 button
init_gp21_enter:
    PUSH {LR}
    MOVS R0, #GPIO_BTN_EN
    BL asm_gpio_init
    MOVS R0, #GPIO_BTN_EN
    MOVS R1, #GPIO_DIR_IN
    BL asm_gpio_set_dir
    MOVS R0, #GPIO_BTN_EN
    BL asm_gpio_set_irq
    POP {PC}

@ Subroutine used to initialize the GP22 button
init_gp22_up:
    PUSH {LR}
    MOVS R0, #GPIO_BTN_UP
    BL asm_gpio_init
    MOVS R0, #GPIO_BTN_UP
    MOVS R1, #GPIO_DIR_IN
    BL asm_gpio_set_dir
    MOVS R0, #GPIO_BTN_UP
    BL asm_gpio_set_irq
    POP {PC}

@ Subroutine used to intstall alarm interrupt
install_alarm_isr:
    LDR     r2, =(PPB_BASE + M0PLUS_VTOR_OFFSET)    
    LDR     r1, [r2]                                
    MOVS    r2, #ALRM_ISR_OFFSET                    
    ADDS    r2, r1
    LDR     r0, =alarm_isr_handler                  
    STR     r0, [r2]     
    
@ Subroutine used to enable the alarm interrupt                           
alarm_isr_enable:
    MOVS R0, #1
    LDR R1, =(PPB_BASE + M0PLUS_NVIC_ICPR_OFFSET)
    STR R0, [R1]
    LDR R1, =(PPB_BASE + M0PLUS_NVIC_ISER_OFFSET)
    STR R0, [R1]
    BX  LR

@ Subroutine used to schedule the alarm
alarm_set:
    MOVS R3, #1
    LDR R1, =(TIMER_BASE + TIMER_INTE_OFFSET)
    LDR R2, [R1]
    ORRS R2, R3
    STR R2, [R1]
    LDR R1, =(TIMER_BASE + TIMER_TIMELR_OFFSET)
    LDR R2, [R1]
    ADD R2, R0
    LDR R1, =(TIMER_BASE + TIMER_ALARM0_OFFSET)
    STR R2, [R1]
    BX  LR

@ Subroutine used to disable the alarm interrupt
alarm_stop:
    MOVS R3, #1
    LDR R1, =(TIMER_BASE + TIMER_INTE_OFFSET)
    LDR R2, [R1]
    BICS R2, R3
    STR R2, [R1]
    LDR R1, =(TIMER_BASE + TIMER_TIMELR_OFFSET)
    LDR R2, [R1]
    LDR R1, =(TIMER_BASE + TIMER_ALARM0_OFFSET)
    STR R2, [R1]
    LDR R0, =(TIMER_BASE + TIMER_INTR_OFFSET)
    MOVS R1, #1
    STR R1, [R0]
    BX  LR

@ Subroutine used to install the GPIO interrupt
gpio_isr_install:
    LDR R2, =(PPB_BASE + M0PLUS_VTOR_OFFSET)
    LDR R1, [R2]
    LDR R2, =GPIO_ISR_OFFSET
    ADD R2, R1
    LDR R0, =gpio_isr_handler
    STR R0, [R2]
    LDR R2, =(PPB_BASE + M0PLUS_NVIC_ICPR_OFFSET)
    MOVS R0, #1
    LSLS R0, #13
    STR R0, [R2]
    LDR R2, =(PPB_BASE + M0PLUS_NVIC_ISER_OFFSET)
    STR R0, [R2]
    BX  LR

@ Subroutine used to handle the alarm interrupts
.thumb_func
alarm_isr_handler:
    PUSH {LR}
    LDR R0, =alarm
    BL printf
    LDR R0, =(TIMER_BASE + TIMER_INTR_OFFSET)
    MOVS R1, #1
    STR R1, [R0]
    BL sub_toggle
    LDR R6, =ltimer
    LDR R0, [R6]
    BL alarm_set
    POP {PC}

@ Subroutine used to toggle the LED light
sub_toggle:
    PUSH    {LR}                        @ Store the link register to the stack as we will call nested subroutines
    MOVS    R0, #GPIO_LED_PIN           @ Set the LED GPIO pin number to r0 for use by asm_gpio_get
    BL      asm_gpio_get                @ Get current the value of the LED GPIO pin (returns to r0)
    CMP     R0, #LED_VAL_OFF            @ Check if the LED GPIO pin value is "off"
    BEQ     led_set_on                  @ If it is "off" then then jump code to turn it on
led_set_off:
    MOVS    R1, #LED_VAL_OFF            @ The LED is currently "on" so we want to turn it "off"
    B       led_set_state               @ Jump to portion of code where we set the state of the LED
led_set_on:
    MOVS    R1, #LED_VAL_ON             @ The LED is currently "off" so we want to turn it "on"
led_set_state:
    MOVS    R0, #GPIO_LED_PIN           @ Set the LED GPIO pin number to r0 for use by asm_gpio_put
    BL      asm_gpio_put                @ Update the the value of the LED GPIO pin (based on value in r1)
    POP     {PC}                        @ Pop the link register from the stack to the program counter

@ Subroutine used to handle GPIO interrupts
.thumb_func
gpio_isr_handler:
    PUSH {LR}

@ Subroutine used to handle GP20 button interrupt
gp20_handle:
    LDR R0, =(IO_BANK0_BASE + IO_BANK0_PROC0_INTS2_OFFSET)
    LDR R1, [R0]
    LDR R4, =GPIO_BTN_DN_MSK
    CMP R1, R4
    BNE gp21_handle
    LDR R6, =lstate
    LDR R5, [R6]
    CMP R5, #0
    BEQ reset_timer
    LDR R6, =ltimer
    LDR R5, [R6]
    ADD R5, R5
    STR R5, [R6]
    LDR R0, =gp20
    BL  printf
    B gpio_isr_finish

@ Subroutine used to reset the timer in case GP21 was pressed previously
reset_timer:
    LDR R6, =ltimer
    LDR R5, =DFLT_ALARM_TIME
    STR R5, [R6]
    B gpio_isr_finish

@ Subroutine used to handle GP21 button interrupt
gp21_handle:
    LDR R0, =(IO_BANK0_BASE + IO_BANK0_PROC0_INTS2_OFFSET)
    LDR R1, [R0]
    LDR R4, =GPIO_BTN_EN_MSK
    CMP R1, R4
    BNE gp22_handle
    LDR R0, =gp21
    BL  printf
    LDR R6, =lstate
    LDR R5, [R6]
    CMP R5, #0
    BEQ gp21_start_alarm
    BL alarm_stop
    LDR R6, =lstate
    LDR R5, =0
    STR R5, [R6]
    B gpio_isr_finish

@ Subroutine used to reschedule the alarm in case it was stopped if GP21 was pressed previously
gp21_start_alarm:
    LDR R6, =ltimer
    LDR R0, [R6]
    BL alarm_set
    LDR R6, =lstate
    LDR R5, =1
    STR R5, [R6]
    B gpio_isr_finish

@ Subroutine used to handle GP22 button interrupt
gp22_handle:
    LDR R0, =(IO_BANK0_BASE + IO_BANK0_PROC0_INTS2_OFFSET)
    LDR R1, [R0]
    LDR R4, =GPIO_BTN_UP_MSK
    CMP R1, R4
    BNE gpio_isr_finish
    LDR R6, =lstate
    LDR R5, [R6]
    CMP R5, #0
    BEQ reset_timer
    LDR R6, =ltimer
    LDR R5, [R6]
    LSRS R5, #1
    STR R5, [R6]
    LDR R0, =gp22
    BL  printf
    B gpio_isr_finish

@ Subroutine used to clear the interrupts
gpio_isr_finish:
    LDR R0, =(IO_BANK0_BASE + IO_BANK0_INTR2_OFFSET)
    LDR R1, =4095
    LSLS R1, #16
    STR R1, [R0]
    POP {PC}


.align 4
gp20:    .asciz "GP20 click event\n"
gp21:    .asciz "GP21 click event\n"
gp22:    .asciz "GP22 click event\n"
alarm:   .asciz "Alarm event\n"

.data
lstate: .word   DFLT_STATE_STRT
ltimer: .word   DFLT_ALARM_TIME
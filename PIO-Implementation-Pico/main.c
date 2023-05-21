#include <stdio.h>
#include "pico/stdlib.h"
#include "hardware/gpio.h"

// Declare the main assembly code entry point.
void main_asm();

// Initialise a GPIO pin
void asm_gpio_init(uint pin) {
    gpio_init(pin);
}

// Set direction of a GPIO pin
void asm_gpio_set_dir(uint pin, bool out) {
    gpio_set_dir(pin, out);
}

// Get the value of a GPIO pin
bool asm_gpio_get(uint pin) {
    return gpio_get(pin);
}

// Set the value of a GPIO pin
void asm_gpio_put(uint pin, bool value) {
    gpio_put(pin, value);
}

// Enable falling-edge interrupt
void asm_gpio_set_irq(uint pin) {
    gpio_set_irq_enabled(pin, GPIO_IRQ_EDGE_FALL, true);
}

// Main entry point of the application
int main() {
    stdio_init_all();              // Initialise all basic IO
    main_asm();                    // Jump into the ASM code
    return 0;                      // Application return code
}
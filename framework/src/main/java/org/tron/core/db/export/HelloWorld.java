package org.tron.core.db.export;

public class HelloWorld {
    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        while (true) {
            System.out.println("Hello, World");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}

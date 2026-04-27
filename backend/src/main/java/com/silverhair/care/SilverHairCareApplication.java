package com.silverhair.care;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SilverHairCareApplication {
        public static void main(String[] args) {
            p("  ██████   ", " ████████  ", " ██      ██ ", "  ████████  ", "██      ██  ", " ██    ██  ", " ██    ██  ");
            p(" ██    ██  ", " ██    ██  ", "   ██  ██   ", "     ██    ", "   ██  ██   ", "  ██    ██  ", "  ██  ██     ");
            p(" ██        ", " ██    ██  ", "    ████    ", "     ██    ", "    ████   ", "   ████████  ", "    ██     ");
            p(" ██        ", " ██    ██  ", "    ████    ", "     ██    ", "    ████    ", "  ████████  ", "    ██     ");
            p(" ██    ██  ", " ██    ██████", "  ████  ", "       ██    ", "   ██  ██    ", " ██    ██  ", "    ██     ");
            p("  ██████   ", " ████████  ", "    ████  ", "       ██    ", " ██      ██  ", " ██    ██  ", "    ██  ");
            SpringApplication.run(SilverHairCareApplication.class, args);
        }
        static void p(String... s){
            for(String a : s) System.out.print(a+" ");
            System.out.println();
        }
}
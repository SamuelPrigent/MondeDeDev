package com.openclassrooms.mddapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

@SpringBootApplication
public class MddApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(MddApiApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void logApplicationStartup() {
    System.out.println(ansi().fg(GREEN).a("---- API is listening on port 8080 ----").reset());
  }

}

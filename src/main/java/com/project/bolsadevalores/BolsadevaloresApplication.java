package com.project.bolsadevalores;

import com.project.bolsadevalores.app.messaging.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BolsadevaloresApplication {

	@Autowired
	Queue queue;

	public static void main(String[] args) {
		SpringApplication.run(BolsadevaloresApplication.class, args);
	}

	@EventListener(SpringApplication.class)
	public void testeMensagem() {
		queue.enviarAtualizacaoDeStatus();
	}
}

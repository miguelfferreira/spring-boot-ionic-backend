package com.mferreira.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.mferreira.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}

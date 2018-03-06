package com.mferreira.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mferreira.cursomc.domain.Cliente;
import com.mferreira.cursomc.repositories.ClienteRepository;
import com.mferreira.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Cliente obj = repo.findByClienteId(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objecto não encontrado! Id: " + id
					+ ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
}

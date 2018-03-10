package com.mferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mferreira.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	@Query("SELECT c FROM Cliente c where c.id = ?1")
	Cliente findByClienteId(Integer id);
	
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}

package com.lirio.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lirio.os.domain.Cliente;
import com.lirio.os.domain.OS;
import com.lirio.os.domain.Tecnico;
import com.lirio.os.domain.enuns.Prioridade;
import com.lirio.os.domain.enuns.Status;
import com.lirio.os.repositories.ClienteRepository;
import com.lirio.os.repositories.OSRepository;
import com.lirio.os.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository OSRepository;
	
	
	public void instaciaDB() {
		
		Tecnico t1 = new Tecnico(null, "Sandro Lirio", "758.811.350-24", "(88) 98888-8888");
		Tecnico t2 = new Tecnico(null, "Linus Torvalds", "463.733.190-08", "(88) 94545-4545");
		Cliente c1 = new Cliente(null, "Betina Campos", "970.739.710-10", "(88) 98888-7777");
		
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OD", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1, t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		OSRepository.saveAll(Arrays.asList(os1));
		
	}

}

package com.lirio.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lirio.os.domain.Pessoa;
import com.lirio.os.domain.Tecnico;
import com.lirio.os.dtos.TecnicoDTO;
import com.lirio.os.repositories.PessoaRepository;
import com.lirio.os.repositories.TecnicoRepository;
import com.lirio.os.services.exceptions.DataIntegratyViolationException;
import com.lirio.os.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	/*
	 * Buscar Tecnico pelo ID
	 */
	public Tecnico findById(Long id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id" + id + ", Tipo: " + Tecnico.class.getName()));
	}

	/*
	 * Buscar todos os Tecnicos da base de dados
	 */
	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	/*
	 * Criar um Tecnico
	 */
	public Tecnico create(TecnicoDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	/*
	 * Atualizar um Tecnico
	 */
	public Tecnico update(Long id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}

		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}

	/*
	 * Deletar um Tecnico pelo ID
	 */
	public void delete(Long id) {
		Tecnico obj = findById(id);

		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Tecnico possui Ordens de Serviço, não pode ser deletado!");
		}
		repository.deleteById(id);
	}
	
	/*
	 * Buscar Tecnico pelo CPF
	 */
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}

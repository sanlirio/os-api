package com.lirio.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lirio.os.domain.Pessoa;
import com.lirio.os.domain.Cliente;
import com.lirio.os.dtos.ClienteDTO;
import com.lirio.os.repositories.PessoaRepository;
import com.lirio.os.repositories.ClienteRepository;
import com.lirio.os.services.exceptions.DataIntegratyViolationException;
import com.lirio.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	/*
	 * Buscar Cliente pelo ID
	 */
	public Cliente findById(Long id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id" + id + ", Tipo: " + Cliente.class.getName()));
	}

	/*
	 * Buscar todos os Clientes da base de dados
	 */
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	/*
	 * Criar um Cliente
	 */
	public Cliente create(ClienteDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return repository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	/*
	 * Atualizar um Cliente
	 */
	public Cliente update(Long id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}

		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}

	/*
	 * Deletar um Cliente pelo ID
	 */
	public void delete(Long id) {
		Cliente obj = findById(id);

		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Pessoa possui Ordens de Serviço, não pode ser deletado!");
		}
		repository.deleteById(id);
	}

	/*
	 * Buscar Cliente pelo CPF
	 */
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}

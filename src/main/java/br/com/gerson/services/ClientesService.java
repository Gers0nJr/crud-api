package br.com.gerson.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.gerson.domain.Clientes;
import br.com.gerson.repositories.ClientesRepository;
import br.com.gerson.services.exception.DataIntegrityException;
import br.com.gerson.services.exception.ObjectNotFoundException;

@Service
public class ClientesService {
	
	@Autowired
	private ClientesRepository clientesRepository;
	
	public List<Clientes> listAll() {
		return clientesRepository.findAll();
	}
	
	public List<Clientes> listWith(String nome){
		
		if(nome.isEmpty()) {
		
			return clientesRepository.findAll();
			
		}
		
		return clientesRepository.findByNomeIgnoreCaseContaining(nome);
		
	}
	
	@Transactional
	public Clientes add(Clientes obj) {
		obj.setId(null);
		obj = clientesRepository.save(obj);
		return obj;
	}
	
	public Clientes update(Integer id, Clientes obj) {	
		Clientes clientes = item(id);
		BeanUtils.copyProperties(obj, clientes, "id");
		return clientesRepository.save(clientes);
	}
	
	public Clientes item(Integer id) {	
		Optional<Clientes> obj = clientesRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Clientes.class.getName()));
	}
	
	public void delete(Integer id) {	
		item(id);	
		try {			
			clientesRepository.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}
}

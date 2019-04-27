package br.com.gerson.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.gerson.domain.Servicos;
import br.com.gerson.repositories.ServicosRepository;
import br.com.gerson.services.exception.DataIntegrityException;
import br.com.gerson.services.exception.ObjectNotFoundException;

@Service
public class ServicosService {
	
	@Autowired
	private ServicosRepository servicosRepository;
	
	public List<Servicos> listAll() {
		return servicosRepository.findAll();
	}
	
	public List<Servicos> listWith(String nome){
		
		if(nome.isEmpty()) {
		
			return servicosRepository.findAll();
			
		}
		
		return servicosRepository.findByNomeIgnoreCaseContaining(nome);
		
	}
	
	@Transactional
	public Servicos add(Servicos obj) {
		obj.setId(null);
		obj = servicosRepository.save(obj);
		return obj;
	}
	
	public Servicos update(Integer id, Servicos obj) {	
		Servicos servicos = item(id);
		BeanUtils.copyProperties(obj, servicos, "id");
		return servicosRepository.save(servicos);
	}
	
	public Servicos item(Integer id) {	
		Optional<Servicos> obj = servicosRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Servicos.class.getName()));
	}
	
	public void delete(Integer id) {	
		item(id);	
		try {			
			servicosRepository.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}
}

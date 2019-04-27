package br.com.gerson.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gerson.domain.Clientes;
import br.com.gerson.dtos.ClientesDTO;
import br.com.gerson.services.ClientesService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/clientes")
public class ClientesResource {

	@Autowired
	private ClientesService clientesService;
	
	@GetMapping
	public ResponseEntity<List<ClientesDTO>> listAll() {
		List<Clientes> list = clientesService.listAll(); 
		List<ClientesDTO> listDto = list.stream().map(obj -> new ClientesDTO(obj)).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping("/consulta")
	public ResponseEntity<List<Clientes>> listWith(
			@RequestParam(value="nome", defaultValue = "") String nome){	
		List<Clientes> list = clientesService.listWith(nome);
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Clientes clientes) {
		Clientes obj = clientesService.add(clientes);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Clientes> update(@PathVariable Integer id, @Valid @RequestBody Clientes clientes) {
		Clientes obj = clientesService.update(id, clientes);
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clientesService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Clientes> item(@PathVariable Integer id) {
		Clientes obj = clientesService.item(id);
		return ResponseEntity.ok().body(obj);
	}
	
}

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

import br.com.gerson.domain.Servicos;
import br.com.gerson.dtos.ServicosDTO;
import br.com.gerson.services.ServicosService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/servicos")
public class ServicosResource {

	@Autowired
	private ServicosService servicosService;
	
	@GetMapping
	public ResponseEntity<List<ServicosDTO>> listAll() {
		List<Servicos> list = servicosService.listAll();
		List<ServicosDTO> listDto = list.stream().map(obj -> new ServicosDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping("/consulta")
	public ResponseEntity<List<Servicos>> listWith(
			@RequestParam(value="nome", defaultValue = "") String nome){	
		List<Servicos> list = servicosService.listWith(nome);
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Servicos servicos) {
		Servicos obj = servicosService.add(servicos);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Servicos> update(@PathVariable Integer id, @Valid @RequestBody Servicos servicos) {
		Servicos obj = servicosService.update(id, servicos);
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		servicosService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Servicos> item(@PathVariable Integer id) {
		Servicos obj = servicosService.item(id);
		return ResponseEntity.ok().body(obj);
	}
	
}

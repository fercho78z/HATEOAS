package com.api.hateoas.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.api.hateoas.model.Cuentas;
import com.api.hateoas.model.Balance;
import com.api.hateoas.service.CuentaService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

	@Autowired 
	private CuentaService cuentaS;

	    @GetMapping
	    public ResponseEntity<List<Cuentas>> listarCuentas() {
	    	List<Cuentas> cuentas= cuentaS.listAll();
	    	if(cuentas.isEmpty()) {
	        return ResponseEntity.noContent().build();
	        
	    	}
	    	
	    	for(Cuentas cuenta:cuentas) {
	    		//sin import static
	    		//cuenta.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).mostrarCuenta(cuenta.getId())).withSelfRel());
	    		//con import static
	    		cuenta.add(linkTo(methodOn(CuentaController.class).mostrarCuenta(cuenta.getId())).withSelfRel());
	    		/*cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(),null)).withRel("depositos"));
	    		cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(),null)).withRel("retiros"));
	    		*/cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
	    		
	    	}
	    	CollectionModel<Cuentas> modelo = CollectionModel.of(cuentas);
	    	modelo.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withSelfRel());
	    	return new ResponseEntity<>(cuentas,HttpStatus.OK);
	    }
	    @GetMapping("/{id}")
	    public ResponseEntity<Cuentas> mostrarCuenta(@PathVariable Integer id){
	    	try {
	    		Cuentas cuenta=cuentaS.get(id);
	    		//sin import static
	    		//cuenta.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CuentaController.class).mostrarCuenta(cuenta.getId())).withSelfRel());
	    		//con import static
	    		cuenta.add(linkTo(methodOn(CuentaController.class).mostrarCuenta(cuenta.getId())).withSelfRel());
	    		cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
	    		
	    		return new ResponseEntity<>(cuenta,HttpStatus.OK);
	    	}
	    	catch(Exception exception){
	    		return ResponseEntity.notFound().build();
	    		
	    	}
	    }

	    @PostMapping
	    public ResponseEntity<Cuentas> guardarCuenta(@RequestBody Cuentas cuenta){
	    	Cuentas cuentaDb = cuentaS.save(cuenta);
	    	cuentaDb.add(linkTo(methodOn(CuentaController.class).mostrarCuenta(cuentaDb.getId())).withSelfRel());
    		cuentaDb.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
	    	//return new ResponseEntity<>(cuentaDb,HttpStatus.CREATED);
    		return ResponseEntity.created(linkTo(methodOn(CuentaController.class).mostrarCuenta(cuentaDb.getId())).toUri()).body(cuentaDb);
	    	
	    }
	    @PutMapping("/{id}")
	    public ResponseEntity<Cuentas> editarCuenta(@RequestBody Cuentas cuenta) {
	    	Cuentas cuentaDb = cuentaS.save(cuenta);
	    	cuentaDb.add(linkTo(methodOn(CuentaController.class).mostrarCuenta(cuentaDb.getId())).withSelfRel());
    		cuentaDb.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
	    	return new ResponseEntity<>(cuenta,HttpStatus.OK);
	    	
	    }
	    
	    @ResponseBody
	    @PatchMapping({"/{id}/deposito"," method = RequestMethod.PATCH"})
	    public ResponseEntity<Cuentas> depositarDinero(@PathVariable Integer id , @RequestBody Balance balance) {	    	
	    	final ObjectMapper mapper = new ObjectMapper()
	    		    .disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
	    		    .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
	    		    .disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)        
	                .disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
	        
	    	System.out.println("Controller monto: "+ balance.getBalance() + "-" +"id: " + id);
	    	Cuentas cuentaDb = cuentaS.depositar(balance.getBalance(), id);
	    	cuentaDb.add(linkTo(methodOn(CuentaController.class).mostrarCuenta(cuentaDb.getId())).withSelfRel());
    		/*cuentaDb.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaDb.getId(),null)).withRel("depositos"));
    		cuentaDb.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaDb.getId(),null)).withRel("retiros"));
    		*/cuentaDb.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
	    	return new ResponseEntity<>(cuentaDb,HttpStatus.OK);
	    }
	    @ResponseBody
	    @PatchMapping({"/{id}/retiro"," method = RequestMethod.PATCH"})
	    public ResponseEntity<Cuentas> retirarDinero(@PathVariable Integer id , @RequestBody Balance balance) {
	    	 
	    	System.out.println("Controller monto: "+ balance.getBalance() + "-" +"id: " + id);

	    	Cuentas cuentaDb = cuentaS.retirar((float)balance.getBalance(), id);
	    	cuentaDb.add(linkTo(methodOn(CuentaController.class).mostrarCuenta(cuentaDb.getId())).withSelfRel());
    		/*cuentaDb.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaDb.getId(),null)).withRel("retiros"));
    		cuentaDb.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuentaDb.getId(),null)).withRel("depositos"));
    		*/cuentaDb.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
	    	return new ResponseEntity<>(cuentaDb,HttpStatus.OK);
	    }
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Cuentas> eliminarCuenta(@PathVariable Integer id){
	      	try {
	    	    cuentaS.delete(id);
	    		return new ResponseEntity<>(HttpStatus.OK);
	    	}
	    	catch(Exception exception){
	    		return ResponseEntity.notFound().build();
	    		
	    	}
	    }

}

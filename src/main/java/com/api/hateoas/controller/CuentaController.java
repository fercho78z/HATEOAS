package com.api.hateoas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.hateoas.model.Cuentas;
import com.api.hateoas.service.CuentaService;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

	@Autowired 
	private CuentaService cuentaS;

	    @GetMapping
	    public ResponseEntity<List<Cuentas>> listarCuentas(){
	    	List<Cuentas> cuenta= cuentaS.listAll();
	    	if(cuenta.isEmpty()) {
	        return ResponseEntity.noContent().build();
	        
	    	}
	    	
	    	return new ResponseEntity<>(cuenta,HttpStatus.OK);
	    }
	    @GetMapping("/{id}")
	    public ResponseEntity<Cuentas> mostrarCuenta(@PathVariable Integer id){
	    	try {
	    		Cuentas cuenta=cuentaS.get(id);
	    		return new ResponseEntity<>(cuenta,HttpStatus.OK);
	    	}
	    	catch(Exception exception){
	    		return ResponseEntity.notFound().build();
	    		
	    	}
	    }

	    @PostMapping
	    public ResponseEntity<Cuentas> guardarCuenta(@RequestBody Cuentas cuenta){
	    	Cuentas cuentaDb = cuentaS.save(cuenta);
	    	return new ResponseEntity<>(cuentaDb,HttpStatus.CREATED);
	    	
	    }
	    @PutMapping("/{id}")
	    public ResponseEntity<Cuentas> editarCuenta(@RequestBody Cuentas cuenta){
	    	Cuentas cuentaDb = cuentaS.save(cuenta);
	    	return new ResponseEntity<>(cuenta,HttpStatus.OK);
	    	
	    }
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Cuentas> eliminarCuenta(@PathVariable Integer id){
	      	try {
	    		cuentaS.delete(id);
	    		return ResponseEntity.notFound().build();
	    	}
	    	catch(Exception exception){
	    		return ResponseEntity.notFound().build();
	    		
	    	}
	    }

}

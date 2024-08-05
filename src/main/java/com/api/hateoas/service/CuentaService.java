package com.api.hateoas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.hateoas.model.Cuentas;
import com.api.hateoas.repository.CuentaRespository;
import com.api.hateoas.exception.CuentaNoFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CuentaService {

	@Autowired
	private CuentaRespository cuentaR;
	
	public List<Cuentas> listAll(){
		return cuentaR.findAll();
	}
	
	public Cuentas get(Integer id) {
		return cuentaR.findById(id).get();
		}
	public Cuentas save(Cuentas cuenta) {
		return cuentaR.save(cuenta);
	}
	
	public void delete(Integer id) throws CuentaNoFoundException {
		if(cuentaR.existsById(id)) {
			cuentaR.deleteById(id);
			throw new CuentaNoFoundException("Cuenta Encontrada: " + id);
		}
		throw new CuentaNoFoundException("Cuenta no encontrada: " + id);
	}
	
	public Cuentas depositar(float balance, Integer id) {
		System.out.println("Service monto : "+ balance + "-" +"id : " + id);
		cuentaR.actualizarBalance(balance, id);
	
		return cuentaR.findById(id).get();
	}
	public Cuentas retirar(float balance, Integer id) {
		cuentaR.actualizarBalance(-balance, id);
		System.out.println("Service Retiro monto : "+ -balance + "-" +"id : " + id);
		return cuentaR.findById(id).get();
		
	}
}

package com.api.hateoas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.hateoas.model.Cuentas;
import com.api.hateoas.repository.CuentaRespository;

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
	
	public void delete(Integer id) {
		cuentaR.deleteById(id);
	}
}

package com.api.hateoas;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.api.hateoas.model.Cuentas;
import com.api.hateoas.repository.CuentaRespository;

@DataJpaTest
@Rollback(value=true)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class CuentaRespositoryTest {
	
@Autowired
private CuentaRespository cuentaR;
	@Test
	void testAgregarCuentas() {
		Cuentas cuenta= new Cuentas(122313,10000);
		Cuentas guardarCuenta= cuentaR.save(cuenta);
		Assertions.assertThat(guardarCuenta).isNotNull(); //comprobamos que la cuenta no se a nula
		Assertions.assertThat(guardarCuenta.getId()).isGreaterThan(0);//comprobammos que el id sea mayor que cero
	}


}

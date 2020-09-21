package pos_java_jdcb.pos_java_jdcb;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancoJdbc {

	@Test
	public void initBanco() {
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava  userposjava = new Userposjava();
		
		//para setar 'dinamicamente'		
		userposjava.setNome("jooj");
		userposjava.setEmail("jooj@gmail.com");
		
		userPosDAO.salvar(userposjava);
	}
	
	@Test
	public void initSalvarTelefone() {
		UserPosDAO userPosDAO = new UserPosDAO();
		Telefone telefone = new Telefone();
		
		telefone.setNumero("81 988854351");
		telefone.setTipo("celular");
		telefone.setUsuario(6L);
		
		userPosDAO.salvarTelefone(telefone);
		
	}
	
	@Test
	public void testeCarregaFoneUser() {
		UserPosDAO dao = new UserPosDAO();
		List<BeanUserFone> beanUserfones =  dao.listaUserFone(6L);
		
		for (BeanUserFone beanUserFone : beanUserfones) {
			System.out.println(beanUserFone);
			System.out.println("----------------------");
		}		
		
	}
	
	@Test
	public void testeDeleteUserFone() {
		UserPosDAO dao = new UserPosDAO();
		dao.deleteFonesPorUser(3L);
	}
	
	@Test
	public void initListar() {
		UserPosDAO dao = new UserPosDAO();
		
		try {
		List<Userposjava>  userposjava = dao.listar();
		
		for(Userposjava users: userposjava) {
			
			System.out.println(users);
			System.out.println("--------------");
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initBuscar() {
		UserPosDAO dao = new UserPosDAO();
		
		try {
		Userposjava userposjava = dao.buscar(3L);
		
		System.out.println(userposjava);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initAtualizar() {
		UserPosDAO dao = new UserPosDAO();
		
		
		try {
			Userposjava usuario = dao.buscar(3L);
			usuario.setNome("Mariazinha games");
			usuario.setEmail("mariogames@gmail.com");
			
			dao.atualizar(usuario);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	@Test
	public void initDeletar() {
		UserPosDAO dao = new UserPosDAO();		
		
		try {						
			dao.deletar(8L);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}

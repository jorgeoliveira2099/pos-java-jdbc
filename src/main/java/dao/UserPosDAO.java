package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class UserPosDAO {

	private Connection connection;
	
	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(Userposjava userposjava) {
		
		try {
			
		String sql = "insert into userposjava (nome, email) values ( ?, ?)";
		PreparedStatement insert = connection.prepareStatement(sql);
		
		
		insert.setString(1, userposjava.getNome());
		insert.setString(2, userposjava.getEmail());
		
				
		insert.execute(); //rodar a query
		connection.commit(); //salvar no banco se deu tudo certo
		
		}catch(Exception e) {
			try {
			connection.rollback(); // reverte a alteração caso deu algum erro
			}catch(Exception i) {
				i.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	//fim do salvar
	
	public void salvarTelefone(Telefone telefone) {
		try {
			
			String sql = "insert into telefoneuser (numero, tipo, usuariopessoa ) values (?, ?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			
			
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			
					
			insert.execute(); //rodar a query
			
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {			
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public List<Userposjava> listar() throws Exception{
		List<Userposjava> list = new ArrayList<Userposjava>();
		
		String sql = "SELECT * FROM userposjava";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			Userposjava user = new Userposjava();
			user.setId(resultado.getLong("id"));
			user.setNome(resultado.getString("nome"));
			user.setEmail(resultado.getString("email"));
			
			list.add(user);
		}
		
		return list;
	}
	
	public List<BeanUserFone> listaUserFone (Long idUser){
		
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
		String sql = "SELECT nome, numero, email FROM telefoneuser as fone";			
			   sql += " INNER JOIN userposjava as userp";
			   sql += " on fone.usuariopessoa = userp.id";
			   sql += " where userp.id = " + idUser;				
			   
			   try {
				   PreparedStatement statement  = connection.prepareStatement(sql);
				   ResultSet resultset = statement.executeQuery();
				   
				while(resultset.next()) {
					BeanUserFone userFone = new BeanUserFone();
					
					userFone.setNome(resultset.getString("nome"));
					userFone.setNumero(resultset.getString("numero"));
					userFone.setEmail(resultset.getString("email"));
					
					beanUserFones.add(userFone);
				}
				
			   }catch(Exception e) {
				   e.printStackTrace();
			   }
		
		
		
		return beanUserFones;
	}
	
	public void deleteFonesPorUser(Long idUser) {
		
		try {
			String sqlFone = "delete from telefoneuser where usuariopessoa =" + idUser;
			String sqlUser = "delete from userposjava where id =" + idUser;
		
			PreparedStatement statement  = connection.prepareStatement(sqlFone);
			statement.executeUpdate();
			connection.commit();
			
		    statement  = connection.prepareStatement(sqlUser);
			statement.executeUpdate();
			connection.commit();		 
		 
		}catch(Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}
	}
	
	public Userposjava buscar(Long id) throws Exception{
		Userposjava retorno = new Userposjava();
		
		String sql = "SELECT * FROM userposjava where id = " + id;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) { //aqui vai retornar um usuario apenas			
			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));			
		}
		
		return retorno;
	}
	
	public void atualizar(Userposjava userposjava) {
		try {
			String sql = "UPDATE userposjava set nome = ?, email = ?  where id = " + userposjava.getId();
		
			PreparedStatement statement = connection.prepareStatement(sql);
		    statement.setString(1, userposjava.getNome());
		    statement.setString(2, userposjava.getEmail());
		    statement.execute();
		    connection.commit();
		    
		}catch(Exception e) {
			try {
			connection.rollback();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	public void deletar(Long id) {
		try {
			String sql = "DELETE FROM userposjava where id = " + id;
		
			PreparedStatement statement = connection.prepareStatement(sql);
			//aqui também funciona, mas fica menos uma linha de coodigo
		  //  statement.setLong(1, userposjava.getId() );
		   
		    statement.execute();
		    
		    connection.commit();	
		    
		}catch(Exception e) {
			try {
			connection.rollback();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	
}

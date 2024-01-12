package bll;

import java.util.List;

import bo.Carte;
import dal.DALException;
import dal.GenericDAO;
import dal.jdbc.CarteDAOJdbcImpl;

public class CarteBLL {
	private GenericDAO<Carte> dao;
	
	public CarteBLL() throws BLLException {
		try {
			dao = new CarteDAOJdbcImpl();
		} catch (DALException e) {
			throw new BLLException(e);
		}
	}
	
	public List<Carte> selectAll() throws BLLException {
		try {
			return dao.selectAll();
		} catch (DALException e) {
			throw new BLLException(e);
		}
	}
	
	public Carte selectById(int id) throws BLLException {
		try {
			return dao.selectById(id);
		} catch (DALException e) {
			throw new BLLException(e);
		}
	}
	
	public Carte insert(String titre) throws BLLException {
		Carte carte = new Carte();
		carte.setTitre(titre);
		try {
			dao.insert(carte);
		} catch (DALException e) {
			throw new BLLException(e);
		}
		return carte;
	}
	
	public void update(Carte carte) throws BLLException {
		try {
			dao.update(carte);
		} catch (DALException e) {
			throw new BLLException(e);
		}
	}
	
	public void delete(int id) throws BLLException {
		try {
			dao.delete(id);
		} catch (DALException e) {
			throw new BLLException(e);
		}
	}
}

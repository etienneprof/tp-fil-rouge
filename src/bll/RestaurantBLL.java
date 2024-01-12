package bll;

import java.util.List;

import bo.Horaire;
import bo.Restaurant;
import bo.Table;
import dal.DALException;
import dal.GenericDAO;
import dal.jdbc.RestaurantDAOJdbcImpl;

public class RestaurantBLL {
	private GenericDAO<Restaurant> dao;
	
	public RestaurantBLL() throws BLLException {
		try {
			dao = new RestaurantDAOJdbcImpl();
		} catch (DALException e) {
			throw new BLLException(e);
		}
	}
	
	public List<Restaurant> selectAll() throws BLLException {
		try {
			return dao.selectAll();
		} catch (DALException e) {
			throw new BLLException(e);
		}
	}
	
	public Restaurant selectById(int id) throws BLLException {
		try {
			return dao.selectById(id);
		} catch (DALException e) {
			throw new BLLException(e);
		}
	}
	
	public Restaurant insert(String nom, String adresse, List<Horaire> horaires, List<Table> tables) throws BLLException {
		Restaurant restaurant = new Restaurant();
		restaurant.setNom(nom);
		restaurant.setAdresse(adresse);
		restaurant.setHoraires(horaires);
		restaurant.setTables(tables);
		try {
			dao.insert(restaurant);
		} catch (DALException e) {
			throw new BLLException(e);
		}
		return restaurant;
	}
	
	public void update(Restaurant restaurant) throws BLLException {
		try {
			dao.update(restaurant);
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

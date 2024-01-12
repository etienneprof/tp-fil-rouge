package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import bo.Carte;
import bo.Horaire;
import bo.Restaurant;
import bo.Table;
import dal.ConnectionProvider;
import dal.DALException;
import dal.GenericDAO;

public class RestaurantDAOJdbcImpl implements GenericDAO<Restaurant> {
	private static final String TABLE_NAME = " restaurants ";
	
	private static final String DELETE = "DELETE FROM"+ TABLE_NAME +" WHERE id = ?";
	private static final String UPDATE = "UPDATE "+ TABLE_NAME +" SET nom = ?, adresse = ?, url_img = ?, id_carte = ? WHERE id = ?";
	private static final String INSERT = "INSERT INTO "+ TABLE_NAME +" (nom, adresse) VALUES (?,?)";
	private static final String INSERT_HORAIRE = "INSERT INTO horaires (jour, ouverture, fermeture, id_restaurant) VALUES (?,?,?,?)";
	private static final String INSERT_TABLE = "INSERT INTO tables (nb_places, etat, id_restaurant) VALUES (?,?,?)";
	private static final String SELECT_BY_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String SELECT = "SELECT * FROM "+ TABLE_NAME;

	private Connection cnx;
	
	public RestaurantDAOJdbcImpl() throws DALException {
		cnx = ConnectionProvider.getConnection();
	}
	
	@Override
	public List<Restaurant> selectAll() throws DALException {
		List<Restaurant> restaurants = new ArrayList<>();
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Restaurant restaurant = new Restaurant();
				restaurant.setId(rs.getInt("id"));
				restaurant.setNom(rs.getString("nom"));
				restaurant.setAdresse(rs.getString("adresse"));
				restaurant.setUrlImage(rs.getString("url_img"));
				
				Carte carte = new Carte();
				carte.setId(rs.getInt("id_carte"));
				restaurant.setCarte(carte);
				
				restaurants.add(restaurant);
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération des données :\n" + e.getMessage(), e);
		}
		return restaurants;
	}

	@Override
	public Restaurant selectById(int id) throws DALException {
		Restaurant restaurant = null;
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				restaurant = new Restaurant();
				restaurant.setId(rs.getInt("id"));
				restaurant.setNom(rs.getString("nom"));
				restaurant.setAdresse(rs.getString("adresse"));
				restaurant.setUrlImage(rs.getString("url_img"));
				
				Carte carte = new Carte();
				carte.setId(rs.getInt("id_carte"));
				restaurant.setCarte(carte);
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération des données :\n" + e.getMessage(), e);
		}
		return restaurant;
	}

	@Override
	public void insert(Restaurant donnee) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, donnee.getNom());
			ps.setString(2, donnee.getAdresse());
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				donnee.setId(rs.getInt(1));
			}
			for (Horaire current : donnee.getHoraires()) {
				PreparedStatement psHoraires = cnx.prepareStatement(INSERT_HORAIRE);
				psHoraires.setString(1, current.getJour());
				psHoraires.setTime(2, Time.valueOf(current.getOuverture()));
				psHoraires.setTime(3, Time.valueOf(current.getFermeture()));
				psHoraires.setInt(4, donnee.getId());
				psHoraires.executeUpdate();
			}
			for (Table current : donnee.getTables()) {
				PreparedStatement psTables = cnx.prepareStatement(INSERT_TABLE);
				psTables.setInt(1, current.getNbPlaces());
				psTables.setString(2, current.getEtat());
				psTables.setInt(3, donnee.getId());
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de l'insertion des données :\n" + e.getMessage(), e);
		}
	}

	@Override
	public void update(Restaurant donnee) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(UPDATE);
			ps.setString(1, donnee.getNom());
			ps.setString(2, donnee.getAdresse());
			ps.setString(3, donnee.getUrlImage());
			ps.setInt(4, donnee.getCarte().getId());
			ps.setInt(5, donnee.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la mise à jour des données :\n" + e.getMessage(), e);
		}
	}

	@Override
	public void delete(int id) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(DELETE);
			ps.setInt(1, id);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la suppression des données :\n" + e.getMessage(), e);
		}
	}
}

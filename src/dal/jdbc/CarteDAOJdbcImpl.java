package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Carte;
import dal.ConnectionProvider;
import dal.DALException;
import dal.GenericDAO;

public class CarteDAOJdbcImpl implements GenericDAO<Carte> {
	private static final String TABLE_NAME = " cartes ";
	
	private static final String DELETE = "DELETE FROM"+ TABLE_NAME +" WHERE id = ?";
	private static final String UPDATE = "UPDATE "+ TABLE_NAME +" SET titre = ? WHERE id = ?";
	private static final String INSERT = "INSERT INTO "+ TABLE_NAME +" (titre) VALUES (?)";
	private static final String SELECT_BY_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String SELECT = "SELECT * FROM "+ TABLE_NAME;

	private Connection cnx;
	
	public CarteDAOJdbcImpl() throws DALException {
		cnx = ConnectionProvider.getConnection();
	}
	
	@Override
	public List<Carte> selectAll() throws DALException {
		List<Carte> cartes = new ArrayList<>();
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Carte carte = new Carte();
				carte.setId(rs.getInt("id"));
				carte.setTitre(rs.getString("titre"));
				
				cartes.add(carte);
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération des données :\n" + e.getMessage(), e);
		}
		return cartes;
	}

	@Override
	public Carte selectById(int id) throws DALException {
		Carte carte = null;
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				carte = new Carte();
				carte.setId(rs.getInt("id"));
				carte.setTitre(rs.getString("titre"));
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération des données :\n" + e.getMessage(), e);
		}
		return carte;
	}

	@Override
	public void insert(Carte donnee) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, donnee.getTitre());
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				donnee.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de l'insertion des données :\n" + e.getMessage(), e);
		}
	}

	@Override
	public void update(Carte donnee) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(UPDATE);
			ps.setString(1, donnee.getTitre());
			ps.setInt(2, donnee.getId());
			
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

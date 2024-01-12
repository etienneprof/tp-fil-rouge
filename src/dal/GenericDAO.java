package dal;

import java.util.List;

public interface GenericDAO<T> {
	/*
	 * Toutes les classes qui implémentent GenericDAO doivent proposer les méthodes selectAll,
	 * selectById, update, insert, delete
	 */
	List<T> selectAll() throws DALException;
	T selectById(int id) throws DALException;
	
	void insert(T donnee) throws DALException;
	void update(T donnee) throws DALException;
	void delete(int id) throws DALException;
}

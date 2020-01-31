package app.dao.imp;

import org.springframework.stereotype.Service;

import app.dao.RestDao;

import app.repository.imp.CityRepository;

@Service("CitiesServices")
public class CitiesServices extends RestDao {

	public CitiesServices(CityRepository cityRepository) {
		super(cityRepository);
		// TODO Auto-generated constructor stub
	}

}

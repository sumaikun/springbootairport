package app.dao.imp;

import org.springframework.stereotype.Service;

import app.dao.RestDao;

import app.repository.imp.CountryRepository;

@Service("CountriesServices")
public class CountriesServices extends RestDao{
	
	public CountriesServices(CountryRepository countryRepository) {
		super(countryRepository);
		// TODO Auto-generated constructor stub
	}
	

}

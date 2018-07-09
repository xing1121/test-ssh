package com.sf.ssh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.ssh.dao.PersonDao;
import com.sf.ssh.domain.Person;
import com.sf.ssh.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonDao personDao;
	
	@Override
	public Person queryById(Long id) {
		return personDao.selectById(id);
	}

	@Override
	public List<Person> queryAll() {
		return personDao.selectAll();
	}

	@Override
	public void insert(Person person) {
		personDao.save(person);		
	}

	@Override
	public void updateById(Person person) {
		personDao.updateById(person);
	}

	@Override
	public void deleteById(Long id) {
		personDao.deleteById(id);
	}

}

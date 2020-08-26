package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.converter.custom.PersonConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v1.PersonVOV2;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.request.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonConverter personConverter;

	public PersonVO findById(Long id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records for this id"));
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public List<PersonVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO create(PersonVO personVO) {
		var entity = DozerConverter.parseObject(personVO, Person.class);
		return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
	}

	public PersonVOV2 createV2(PersonVOV2 personVOV2) {
		var entity = personConverter.convertVOToEntity(personVOV2);
		return personConverter.convertEntityToVO(repository.save(entity));
	}
	
	public PersonVO update(PersonVO personVO) {
		Person entity = repository.findById(personVO.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records for this id"));
		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());
		return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
	}

	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records for this id"));
		repository.delete(entity);
	}
}

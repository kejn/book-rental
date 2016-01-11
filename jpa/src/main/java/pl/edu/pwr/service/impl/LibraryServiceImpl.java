package pl.edu.pwr.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.mapper.impl.LibraryMapper;
import pl.edu.pwr.service.LibraryService;
import pl.edu.pwr.to.LibraryTo;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private LibraryDao libraryDao;

	@Autowired
	private LibraryMapper libraryMapper;

	private List<LibraryTo> set2List(Set<LibraryTo> set) {
		return set.stream().collect(Collectors.toList());
	}

	@Override
	public List<LibraryTo> findAll() {
		return set2List(libraryMapper.map2To(libraryDao.findAll()));
	}

	@Override
	public List<LibraryTo> findLibrariesByName(String libraryName) {
		return set2List(libraryMapper.map2To(libraryDao.findLibrariesByName(libraryName)));
	}

	@Override
	public LibraryTo findLibraryById(BigDecimal libraryId) {
		return libraryMapper.map2To(libraryDao.findOne(libraryId));
	}

}

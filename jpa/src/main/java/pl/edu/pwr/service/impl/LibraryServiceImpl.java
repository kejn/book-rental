package pl.edu.pwr.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.mapper.impl.LibraryMapper;
import pl.edu.pwr.service.LibraryService;
import pl.edu.pwr.to.BookTo;
import pl.edu.pwr.to.LibraryTo;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private LibraryDao libraryDao;

	@Autowired
	private LibraryMapper libraryMapper;

	@Override
	public List<LibraryTo> findLibrariesByName(String libraryName) {
		return libraryMapper.map2To(libraryDao.findLibrariesByName(libraryName)).stream().collect(Collectors.toList());
	}

	@Override
	public List<BookTo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

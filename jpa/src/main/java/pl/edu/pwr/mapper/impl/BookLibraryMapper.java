package pl.edu.pwr.mapper.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.mapper.Mapper;
import pl.edu.pwr.to.BookLibraryTo;

@Component
public class BookLibraryMapper implements Mapper<BookLibraryTo, BookLibraryEntity> {

	@Autowired
	private LibraryMapper libraryMapper;
	
	protected BookLibraryMapper() {
	}
	
	public BookLibraryMapper(LibraryMapper libraryMapper) {
		this.libraryMapper = libraryMapper;
	}
	
	@Override
	public BookLibraryTo map2To(BookLibraryEntity entity) {
		return new BookLibraryTo(entity.getBookId(), libraryMapper.map2To(entity.getLibrary()), entity.getQuantity());
	}

	@Override
	public BookLibraryEntity map2Entity(BookLibraryTo to) {
		return new BookLibraryEntity(new BookEntity(to.getBookId()), libraryMapper.map2Entity(to.getLibrary()), to.getQuantity());
	}

	@Override
	public Set<BookLibraryTo> map2To(Collection<BookLibraryEntity> entities) {
		return entities.stream().map(this::map2To).collect(Collectors.toSet());
	}

	@Override
	public Set<BookLibraryEntity> map2Entity(Collection<BookLibraryTo> tos) {
		return tos.stream().map(this::map2Entity).collect(Collectors.toSet());
	}

}

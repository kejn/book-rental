package pl.edu.pwr.mapper.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.mapper.Mapper;
import pl.edu.pwr.to.BookTo;

@Component
public class BookMapper implements Mapper<BookTo, BookEntity>{

	@Autowired
	private AuthorMapper authorMapper;

	@Autowired
	private LibraryMapper libraryMapper;

	protected BookMapper() {
	}

	public BookMapper(AuthorMapper authorMapper, LibraryMapper libraryMapper) {
		this.authorMapper = authorMapper;
		this.libraryMapper = libraryMapper;
	}

	@Override
	public BookTo map2To(BookEntity bookEntity) {
		return new BookTo(bookEntity.getId(), bookEntity.getTitle(), authorMapper.map2To(bookEntity.getAuthors()),
		    libraryMapper.map2To(bookEntity.getLibraries()));
	}

	@Override
	public BookEntity map2Entity(BookTo bookTo) {
		return new BookEntity(bookTo.getId(), bookTo.getTitle(), authorMapper.map2Entity(bookTo.getAuthors()),
				libraryMapper.map2Entity(bookTo.getLibraries()));
	}

	@Override
	public List<BookTo> map2To(Collection<BookEntity> bookEntities) {
		return bookEntities.stream().map(this::map2To).collect(Collectors.toList());
	}

	@Override
	public List<BookEntity> map2Entity(Collection<BookTo> bookTos) {
		return bookTos.stream().map(this::map2Entity).collect(Collectors.toList());
	}

}

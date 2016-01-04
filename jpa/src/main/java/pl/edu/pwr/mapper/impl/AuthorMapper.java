package pl.edu.pwr.mapper.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pl.edu.pwr.entity.AuthorEntity;
import pl.edu.pwr.mapper.Mapper;
import pl.edu.pwr.to.AuthorTo;

@Component
public class AuthorMapper implements Mapper<AuthorTo, AuthorEntity> {

	@Override
	public AuthorTo map2To(AuthorEntity authorEntity) {
		return new AuthorTo(authorEntity.getId(), authorEntity.getFirstName(), authorEntity.getLastName());
	}

	@Override
	public AuthorEntity map2Entity(AuthorTo authorTo) {
		return new AuthorEntity(authorTo.getId(), authorTo.getFirstName(), authorTo.getLastName());
	}

	@Override
	public Set<AuthorTo> map2To(Collection<AuthorEntity> authorEntities) {
		return authorEntities.stream().map(this::map2To).collect(Collectors.toSet());
	}

	@Override
	public Set<AuthorEntity> map2Entity(Collection<AuthorTo> authorTos) {
		return authorTos.stream().map(this::map2Entity).collect(Collectors.toSet());
	}

}

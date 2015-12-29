package pl.edu.pwr.dao;

import java.math.BigDecimal;

import pl.edu.pwr.entity.BookEntity;

public interface BookDao {

	BookEntity findOne(BigDecimal bookId);

}

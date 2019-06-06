package demo.basicapi.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import demo.basicapi.domain.Contact;
import demo.basicapi.mappers.ContactMapper;

public interface ContactDao {

	@Mapper(ContactMapper.class)
	@SqlQuery("select * from contact where id = :id")
	Contact getContactById(@Bind("id") int id);
}

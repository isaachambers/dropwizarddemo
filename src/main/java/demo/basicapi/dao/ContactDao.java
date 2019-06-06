package demo.basicapi.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import demo.basicapi.domain.Contact;
import demo.basicapi.mappers.ContactMapper;

public interface ContactDao {

	@Mapper(ContactMapper.class)
	@SqlQuery("select * from contact where id = :id")
	Contact getContactById(@Bind("id") long id);

	@GetGeneratedKeys
	@SqlUpdate("insert into contact (id, firstName, lastName, phone) values (NULL, :firstName, :lastName, :phone)")
	long createContact(@Bind("firstName") String firstName, @Bind("lastName") String lastName,
			@Bind("phone") String phone);

	@SqlUpdate("update contact set firstName = :firstName, lastName = :lastName, phone = :phone where id = :id")
	void updateContact(@Bind("id") long id, @Bind("firstName") String firstName, @Bind("lastName") String lastName,
			@Bind("phone") String phone);

	@SqlUpdate("delete from contact where id = :id")
	void deleteContact(@Bind("id") long id);

	@Mapper(ContactMapper.class)
	@SqlQuery("select * from contact")
	List<Contact> getAll();
}

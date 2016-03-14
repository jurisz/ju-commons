package integration.persistence;

import integration.IntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.juz.common.persistence.EntityUniqueCheckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.juz.common.util.DateTimeUtils.date;

@Transactional
public class BasicPersistenceTest extends IntegrationTest {

	private static final Logger log = LoggerFactory.getLogger(BasicPersistenceTest.class);

	@Autowired
	private AuthorRepository authorRepo;

	@Autowired
	private BookRepository bookRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EntityUniqueCheckRepository entityUniqueCheckRepository;

	@Test
	public void testPersist() throws Exception {
		Author author = new Author();
		author.setBirthDate(date("1975-10-05"));
		author.setName("author");

		Book book = new Book();
		book.setTitle("title1");
		author.addBook(book);

		authorRepo.save(author);

		List<Author> list = authorRepo.findAll();
		Author saved1 = list.get(0);
		assertThat(saved1.getName(), is("author"));
		assertThat(saved1.getBirthDate(), is(date("1975-10-05")));
	}

	@Test
	public void shouldUpdateEntityUpdateField() throws Exception {

		Author author = new Author();
		author.setName("author");
		authorRepo.save(author);
		entityManager.flush();
		List<Author> list = authorRepo.findAll();
		Author saved1 = list.get(0);
		LocalDateTime updated1 = saved1.getUpdated();

		Thread.sleep(5);
		saved1.setName("new author name");
		entityManager.flush();
		list = authorRepo.findAll();
		Author saved2 = list.get(0);
		LocalDateTime updated2 = saved2.getUpdated();

		log.info("2nd update:" + updated2 + " insertDate:" + updated1);
		Assert.assertTrue(updated2.isAfter(updated1));
	}

	@Test
	public void isSameEntityChecks() throws Exception {
		Author author = new Author();
		author.setBirthDate(date("1975-10-05"));
		author.setName("author");

		Book book1 = new Book();
		book1.setTitle("title1");
		author.addBook(book1);

		Book book2 = new Book();
		book2.setTitle("title2");
		author.addBook(book2);

		authorRepo.save(author);
		bookRepo.save(book1);
		bookRepo.save(book2);

		List<Author> list = authorRepo.findAll();
		Author saved1 = list.get(0);
		assertThat(saved1.getName(), is("author"));
		assertThat(saved1.getBirthDate(), is(date("1975-10-05")));

		assertThat(saved1.isSameEntity(author), is(true));
		assertThat(saved1.isSameEntity(book1), is(false));

		Book savedBook = saved1.getBooks().get(0);
		assertThat(savedBook.isSameEntity(book1), is(true));
		assertThat(savedBook.isSameEntity(author), is(false));

		Book savedBook2 = saved1.getBooks().get(1);
		assertThat(savedBook2.isSameEntity(book2), is(true));
		assertThat(savedBook.isSameEntity(savedBook2), is(false));
	}

	@Test(expected = NullPointerException.class)
	public void isSameEntityFailsIfNotSaved() throws Exception {
		Book book1 = new Book();
		book1.setTitle("title1");

		Book book2 = new Book();
		book2.setTitle("title2");

		book1.isSameEntity(book2);
	}

	@Test
	public void entityUniqueChecks() throws Exception {
		assertTrue(entityUniqueCheckRepository.checkIsUnique(Author.class, "name", "author-1"));

		Author author = new Author();
		author.setBirthDate(date("1975-10-05"));
		author.setName("author-1");

		authorRepo.save(author);

		assertFalse(entityUniqueCheckRepository.checkIsUnique(Author.class, "name", "author-1"));
		assertTrue(entityUniqueCheckRepository.checkIsUnique(Author.class, "name", "author-1", author.getId()));
	}
}

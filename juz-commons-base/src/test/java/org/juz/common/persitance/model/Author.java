package org.juz.common.persitance.model;

import com.google.common.collect.Lists;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.juz.common.persistence.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;

@SequenceGenerator(name = "seq_gen", sequenceName = "authors_seq", allocationSize = 1)
@Audited
@Entity
@Table(name = "authors",
		indexes = {@Index(name = "idx_authors_name", columnList = "name")}
)
@BatchSize(size = 10)
public class Author extends BaseEntity {

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	@OptimisticLock(excluded = true)
	@OrderBy("id desc")
	@NotAudited
	private List<Book> books = Lists.newArrayList();

	private String name;

	private LocalDate birthDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addBook(Book book) {
		book.setAuthor(this);
		this.books.add(book);
	}

	public List<Book> getBooks() {
		return copyOf(books);
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
}

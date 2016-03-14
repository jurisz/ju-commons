package integration.persistence;

import org.hibernate.annotations.BatchSize;
import org.hibernate.envers.Audited;
import org.juz.common.persistence.model.BaseEntity;

import javax.persistence.*;

@SequenceGenerator(name = "seq_gen", sequenceName = "books_seq", allocationSize = 1)
@Audited
@Entity
@Table(name = "books",
		indexes = {@Index(name = "idx_books_author", columnList = "author_id")}
)
@BatchSize(size = 10)
public class Book extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "author_id")
	private Author author;

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Author getAuthor() {
		return author;
	}
}

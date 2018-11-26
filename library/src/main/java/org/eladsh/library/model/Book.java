package org.eladsh.library.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "callNo")})
public class Book {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	Long id;
	@Column(unique = true, nullable = false, length = 100)
    String callNo;
	@Column(length = 100)
    String name;
    @Column(length = 100)
    String author;
    @Column(length = 100)
    String publisher;
    @Column
    int quantity;
    @Column
    int issued = 0;
    @Column
    Date addedDate = new Date();
    
}


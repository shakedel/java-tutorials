package org.eladsh.library.model;

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
        @UniqueConstraint(columnNames = "email") })
public class Librarian {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	Long id;
	@Column(nullable = false, length = 100)
    String name;
	@Column(nullable = false, length = 100)
    String password;
    @Column(unique = true, nullable = false, length = 100)
    String email;
    @Column(length = 100)
    String address;
    @Column(length = 100)
    String city;
    @Column(length = 100)
    String contactNo;
    
}


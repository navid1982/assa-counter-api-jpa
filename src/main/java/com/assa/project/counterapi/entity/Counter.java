package com.assa.project.counterapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

//Table - Counter
@Entity
@Table(name="Counter")
/*
 * @NamedQueries( value = {
 * 
 * @NamedQuery(name="query_get_all_counters", query="select c from counter c"),
 * 
 * @NamedQuery(name="query_get_all_counters", query="select c from counter c")
 * 
 * } )
 */

public class Counter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private long id;
	
	@Column(name="name", nullable = false, unique=true)
	@Pattern(regexp = "\\A(?!\\s*\\Z).+", message="name can't be empty string")
	private String name;
	
	@Column(name="value", nullable = false)
	private int value;
	
	public Counter() {}

	public Counter(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Counter [id=" + id + ", name=" + name + ", value=" + value + "]";
	}

}

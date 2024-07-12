package com.viettridao.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Table(name="Appraiser")
public class Appraiser {
	@Column(name = "AppraiserId", columnDefinition = "varchar(10)")
	@Id
	String appraiserId;
	
	@OneToMany(mappedBy = "appraiser")
	List<Asset> assets = new ArrayList<Asset>();
	
	@Column(name = "Name")
	String name;
	
	@Column(name = "Experience")
	String experience;
	
	@Column(name = "Email")
	String email;
	
	@Column(name = "Phone")
	String phone;
	
	@Column(name = "Address")
	String address;
	
	@Column(name = "DeletedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime deleteAt;
}

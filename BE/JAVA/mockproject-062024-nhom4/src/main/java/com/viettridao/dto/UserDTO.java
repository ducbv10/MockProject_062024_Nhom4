package com.viettridao.dto;

import java.util.Date;

import com.viettridao.entity.enums.Gender;
import com.viettridao.entity.enums.UserIsBan;
import com.viettridao.entity.enums.UserVerify;

public class UserDTO {

	private String userId;

	private String userName;

	private String passWord;

	private String avatar;

	private String name;

	private Date dateOfBirth;

	private Gender gender;

	private String phone;

	private String email;

	private String addressId;

	private UserIsBan isBan;

	private UserVerify verify;

	public UserDTO() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public UserIsBan getIsBan() {
		return isBan;
	}

	public void setIsBan(UserIsBan isBan) {
		this.isBan = isBan;
	}

	public UserVerify getVerify() {
		return verify;
	}

	public void setVerify(UserVerify verify) {
		this.verify = verify;
	}
}

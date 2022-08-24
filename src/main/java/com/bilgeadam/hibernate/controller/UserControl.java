package com.bilgeadam.hibernate.controller;

import com.bilgeadam.hibernate.repository.UserDao;

public class UserControl {

	public static void main(String[] args) {

		UserDao userDao = new UserDao();
		// User myUser = new User("HarryPotter", "helloIamHarryPotter", "Male");
		// userDao.save(myUser);
		userDao.delete(1);

	}

}

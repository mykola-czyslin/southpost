/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The encoded password generator.
 * Created by mchys on 08.03.2018.
 */
public class EncoderTool {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
		for (String password : args) {
			System.out.println(password + " : " + encoder.encode(password));
		}
	}
}

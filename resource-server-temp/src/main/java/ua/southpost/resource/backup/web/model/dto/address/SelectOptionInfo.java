/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.dto.address;

/**
 * Abstraction for select option info.
 * Created by mchys on 29.03.2018.
 */
public class SelectOptionInfo<T> {
	private T id;
	private String name;

	public static <T> SelectOptionInfo<T> create(T id, String name) {
		SelectOptionInfo<T> optionInfo = new SelectOptionInfo<>();
		optionInfo.setId(id);
		optionInfo.setName(name);
		return optionInfo;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

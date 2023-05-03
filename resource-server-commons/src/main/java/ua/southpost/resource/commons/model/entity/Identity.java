/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.commons.model.entity;

/**
 * Defines generic identity object.
 * Created by mchys on 17.02.2018.
 */
public interface Identity<T> {
	T getId();

	void setId(T id);
}

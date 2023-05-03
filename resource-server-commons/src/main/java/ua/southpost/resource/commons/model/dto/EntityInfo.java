/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.commons.model.dto;

/**
 * Declares contract on carrying lookup entity info.
 * Created by mchys on 27.05.2018.
 */
public interface EntityInfo<I> {
	I getId();

	void setId(I id);

	String getTextValue();

	void setTextValue(String textValue);
}

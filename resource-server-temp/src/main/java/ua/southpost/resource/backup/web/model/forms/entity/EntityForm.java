/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity;

/**
 * The generic entity form.
 * Created by mchys on 17.03.2018.
 */
public interface EntityForm<I> {

	I getId();

	void setId(I id);
}

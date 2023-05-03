package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.commons.model.dto.EntityInfo;

public interface EntityInfoTypeToIdentityTypeResolver {

    Class<? extends Identity<?>> resolveIdentityType(Class<? extends EntityInfo<?>> entityInfoType);
}

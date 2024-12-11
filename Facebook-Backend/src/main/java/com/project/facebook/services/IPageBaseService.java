package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.projections.profiles.ProfileFullnameAndIdProjection;
import com.project.facebook.responses.profile.ProfileFullnameAndIdResponse;

public interface IPageBaseService {
    PageBase getPageBaseById(Long id) throws DataNotFoundException;
    Boolean checkPathnameExists(String pathname);
    ProfileFullnameAndIdResponse getFullnameAndIdByPathname(String pathname);
}

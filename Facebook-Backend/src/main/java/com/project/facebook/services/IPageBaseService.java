package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;

public interface IPageBaseService {
    PageBase getPageBaseById(Long id) throws DataNotFoundException;
}

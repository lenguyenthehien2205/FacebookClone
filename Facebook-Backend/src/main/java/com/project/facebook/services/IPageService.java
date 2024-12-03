package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Page;
import com.project.facebook.models.Profile;

public interface IPageService {
    Page getPageById(Long id) throws DataNotFoundException;
}

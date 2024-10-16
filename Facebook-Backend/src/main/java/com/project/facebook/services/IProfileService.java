package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.models.User;

public interface IProfileService {
    Profile getProfileById(Long id) throws DataNotFoundException;
    PageBase updateProfileAvatar(Long id, String avatarFileName) throws DataNotFoundException;
    PageBase updateProfileCoverPhoto(Long id, String avatarFileName) throws DataNotFoundException;
}

package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.models.User;
import com.project.facebook.repositories.PageBaseRepository;
import com.project.facebook.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService{
    private final ProfileRepository profileRepository;
    private final PageBaseRepository pageBaseRepository;
    private final PageBaseService pageBaseService;
    @Override
    public Profile getProfileById(Long id) throws DataNotFoundException {
        return profileRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Profile with id = "+id+" not found")
        );
    }
    @Override
    public PageBase updateProfileAvatar(Long profileId, String avatarFileName) throws DataNotFoundException{
        Profile existingProfile = getProfileById(profileId);
        PageBase pageBase = pageBaseService.getPageBaseById(existingProfile.getPageBase().getId());
        pageBase.setAvatar(avatarFileName);
        return pageBaseRepository.save(pageBase);
    }
    @Override
    public PageBase updateProfileCoverPhoto(Long profileId, String avatarFileName) throws DataNotFoundException{
        Profile existingProfile = getProfileById(profileId);
        PageBase pageBase = pageBaseService.getPageBaseById(existingProfile.getPageBase().getId());
        pageBase.setCoverPhoto(avatarFileName);
        return pageBaseRepository.save(pageBase);
    }
}

package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.PageBase;
import com.project.facebook.projections.profiles.ProfileFullnameAndIdProjection;
import com.project.facebook.repositories.PageBaseRepository;
import com.project.facebook.responses.media.MediaImageProfileResponse;
import com.project.facebook.responses.profile.ProfileFullnameAndIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageBaseService implements IPageBaseService{
    private final PageBaseRepository pageBaseRepository;
    @Override
    public PageBase getPageBaseById(Long id) throws DataNotFoundException {
        return pageBaseRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Page base not found")
        );
    }
    @Override
    public Boolean checkPathnameExists(String pathname){
        return pageBaseRepository.existsByPathName(pathname);
    }
    @Override
    public ProfileFullnameAndIdResponse getFullnameAndIdByPathname(String pathname){
        ProfileFullnameAndIdProjection projection = pageBaseRepository.getFullnameAndIdByPathname(pathname);
        return ProfileFullnameAndIdResponse.convertToResponse(projection);
    }
}

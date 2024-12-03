package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Page;
import com.project.facebook.models.Profile;
import com.project.facebook.repositories.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService implements IPageService{
    private final PageRepository pageRepository;
    @Override
    public Page getPageById(Long id) throws DataNotFoundException {
        return pageRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Profile with id = "+id+" not found")
        );
    }
}

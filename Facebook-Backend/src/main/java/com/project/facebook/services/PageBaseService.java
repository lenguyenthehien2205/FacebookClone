package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.PageBase;
import com.project.facebook.repositories.PageBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Media;
import com.project.facebook.repositories.MediaRepository;
import com.project.facebook.responses.media.MediaPostResponse;
import com.project.facebook.responses.media.MediaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaService implements IMediaService{
    private final MediaRepository mediaRepository;
    @Override
    public Media getMediaById(Long mediaId) throws DataNotFoundException {
        return mediaRepository.findById(mediaId)
                .orElseThrow(() -> new DataNotFoundException("Media not found"));
    }
    @Override
    public MediaPostResponse getMediaByPostId(Long postId) {
        List<Media> medias = mediaRepository.findByPostId(postId);
        List<MediaResponse> mediaResponses = medias.stream()
                .map(MediaResponse::fromMedia)
                .collect(Collectors.toList());

        return MediaPostResponse.builder()
                .mediaResponses(mediaResponses)
                .totalMedias(medias.size())
                .build();
    }
}

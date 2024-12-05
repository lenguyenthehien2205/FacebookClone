package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Media;
import com.project.facebook.responses.media.MediaImageProfileResponse;
import com.project.facebook.responses.media.MediaPostResponse;

import java.util.List;

public interface IMediaService {
    Media getMediaById(Long mediaId) throws DataNotFoundException;
    MediaPostResponse getMediaByPostId(Long postId);

    List<MediaImageProfileResponse> getImagesByProfileId(Long profileId);
}

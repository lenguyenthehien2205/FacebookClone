package com.project.facebook.services;

import com.project.facebook.exceptions.DataNotFoundException;
import com.project.facebook.models.Media;
import com.project.facebook.responses.media.MediaPostResponse;

public interface IMediaService {
    Media getMediaById(Long mediaId) throws DataNotFoundException;
    MediaPostResponse getMediaByPostId(Long postId);
}

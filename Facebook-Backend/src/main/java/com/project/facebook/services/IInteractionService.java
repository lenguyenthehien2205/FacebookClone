package com.project.facebook.services;

import com.project.facebook.responses.interaction.InteractionDetailResponse;
import com.project.facebook.responses.interaction.InteractionResponse;

public interface IInteractionService {
    InteractionResponse getInteractionPost(Long postId);
    InteractionResponse getInteractionMedia(Long mediaId);
    InteractionDetailResponse getInteractionDetailPost(Long postId);
    InteractionDetailResponse getInteractionDetailPostWithInteractionType(Long postId, String interactionType);
    InteractionDetailResponse getInteractionDetailMedia(Long mediaId);
}

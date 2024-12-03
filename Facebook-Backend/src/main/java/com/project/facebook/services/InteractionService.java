package com.project.facebook.services;

import com.project.facebook.models.Interaction;
import com.project.facebook.models.Page;
import com.project.facebook.models.Profile;
import com.project.facebook.models.User;
import com.project.facebook.repositories.InteractionRepository;
import com.project.facebook.repositories.PageRepository;
import com.project.facebook.repositories.ProfileRepository;
import com.project.facebook.responses.interaction.InteractionDetailResponse;
import com.project.facebook.responses.interaction.InteractionResponse;
import com.project.facebook.responses.interaction.InteractorNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InteractionService implements IInteractionService{
    private final InteractionRepository interactionRepository;
    private final PageRepository pageRepository;
    private final ProfileRepository profileRepository;
    private final Map<String, Integer> interactionScores = Map.of(
            "like", 7, "love", 6, "care", 5, "haha", 4, "wow", 3, "sad", 2, "angry", 1
    );
    @Override
    public InteractionResponse getInteractionPost(Long postId) {
//        List<InteractorNameResponse> postInteractorResponses = interactionRepository
//                .findByPostMediaIdAndPostMediaType(postId, "post")
//                .stream()
//                .map(this::createInteractorNameResponse)
//                .limit(19)
//                .collect(Collectors.toList());

        int totalPostInteractions = interactionRepository.countByPostMediaIdAndPostMediaType(postId, "post");
        List<String> postInteractionTypes = interactionRepository.findInteractionTypesByPostId(postId);

        Map<String, Long> postInteractionCounts = postInteractionTypes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        String highestPostInteraction = getHighestInteraction(postInteractionCounts, interactionScores);
        String secondHighestPostInteraction = getSecondHighestInteraction(postInteractionCounts, interactionScores, highestPostInteraction);

        return InteractionResponse.builder()
//                .interactorNameResponses(postInteractorResponses)
                .totalInteractions(totalPostInteractions)
                .highestInteraction(highestPostInteraction)
                .secondHighestInteraction(secondHighestPostInteraction)
                .build();
    }
    public InteractionResponse getInteractionMedia(Long mediaId) {
//        List<InteractorNameResponse> mediaInteractorResponses = interactionRepository
//                .findByPostMediaIdAndPostMediaType(mediaId, "media")
//                .stream()
//                .map(this::createInteractorNameResponse)
//                .limit(19)
//                .collect(Collectors.toList());

        int totalMediaInteractions = interactionRepository.countByPostMediaIdAndPostMediaType(mediaId, "media");
        List<String> mediaInteractionTypes = interactionRepository.findInteractionTypesByMediaId(mediaId);

        Map<String, Long> mediaInteractionCounts = mediaInteractionTypes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

//        Map<String, Integer> interactionScores = Map.of(
//                "like", 7, "love", 6, "care", 5, "haha", 4, "wow", 3, "sad", 2, "angry", 1
//        );

        String highestMediaInteraction = getHighestInteraction(mediaInteractionCounts, interactionScores);
        String secondHighestMediaInteraction = getSecondHighestInteraction(mediaInteractionCounts, interactionScores, highestMediaInteraction);

        return InteractionResponse.builder()
//                .interactorNameResponses(mediaInteractorResponses)
                .totalInteractions(totalMediaInteractions)
                .highestInteraction(highestMediaInteraction)
                .secondHighestInteraction(secondHighestMediaInteraction)
                .build();
    }

    // lay ra chi tiet luot tuong tac cua bai viet
    @Override
    public InteractionDetailResponse getInteractionDetailPost(Long postId) {
        List<InteractorNameResponse> interactorPostResponses = interactionRepository
                .findByPostMediaIdAndPostMediaType(postId, "post")
                .stream()
                .map(this::createInteractorNameResponse)
                .limit(19)
                .collect(Collectors.toList());
        int totalPostInteractions = interactionRepository.countByPostMediaIdAndPostMediaType(postId, "post");
        return InteractionDetailResponse.builder()
                .interactorNameResponses(interactorPostResponses)
                .totalInteractions(totalPostInteractions)
                .build();
    }
    @Override
    public InteractionDetailResponse getInteractionDetailPostWithInteractionType(Long postId, String interactionType) {
        List<InteractorNameResponse> interactorPostResponses = interactionRepository
                .findByPostMediaIdAndPostMediaTypeAndInteractionType(postId, "post", interactionType)
                .stream()
                .map(this::createInteractorNameResponse)
                .limit(19)
                .collect(Collectors.toList());
        int totalPostInteractions = interactionRepository.countByPostMediaIdAndPostMediaTypeAndInteractionType(postId, "post", interactionType);
        return InteractionDetailResponse.builder()
                .interactorNameResponses(interactorPostResponses)
                .totalInteractions(totalPostInteractions)
                .build();
    }
    // lay ra chi tiet luot tuong tac cua media
    @Override
    public InteractionDetailResponse getInteractionDetailMedia(Long mediaId) {
        List<InteractorNameResponse> interactorMediaResponses = interactionRepository
                .findByPostMediaIdAndPostMediaType(mediaId, "media")
                .stream()
                .map(this::createInteractorNameResponse)
                .limit(19)
                .collect(Collectors.toList());
        int totalPostInteractions = interactionRepository.countByPostMediaIdAndPostMediaType(mediaId, "media");
        return InteractionDetailResponse.builder()
                .interactorNameResponses(interactorMediaResponses)
                .totalInteractions(totalPostInteractions)
                .build();
    }
    // chuyen Interaction nhan vao thanh InteractorNameResponse
    private InteractorNameResponse createInteractorNameResponse(Interaction interaction) {
        InteractorNameResponse interactorNameResponse = new InteractorNameResponse();
        if (interaction.getInteractorType().equals(User.PROFILE)) {
            Optional<Profile> profOpt = profileRepository.findById(interaction.getInteractorId());
            if (profOpt.isPresent()) {
                Profile prof = profOpt.get();
                interactorNameResponse.setInteractorName(prof.getDisplayFormat().equals("firstname_lastname")
                        ? prof.getFirstName() + " " + prof.getLastName()
                        : prof.getLastName() + " " + prof.getFirstName());
            }
        } else if (interaction.getInteractorType().equals(User.PAGE)) {
            Optional<Page> pageOpt = pageRepository.findById(interaction.getInteractorId());
            if (pageOpt.isPresent()) {
                Page page = pageOpt.get();
                interactorNameResponse.setInteractorName(page.getPageName());
            }
        }
        return interactorNameResponse;
    }

    private String getHighestInteraction(Map<String, Long> interactionCounts, Map<String, Integer> interactionScores) {
        return interactionCounts.entrySet().stream()
                .max(Comparator.<Map.Entry<String, Long>>comparingLong(Map.Entry::getValue)
                        .thenComparingInt(entry -> interactionScores.getOrDefault(entry.getKey(), 0))
                )
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private String getSecondHighestInteraction(Map<String, Long> interactionCounts, Map<String, Integer> interactionScores, String highestInteraction) {
        return interactionCounts.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(highestInteraction))
                .max(Comparator.<Map.Entry<String, Long>>comparingLong(Map.Entry::getValue)
                        .thenComparingInt(entry -> interactionScores.getOrDefault(entry.getKey(), 0))
                )
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}

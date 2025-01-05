package com.project.facebook.dtos;

import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ConversationDTO {
    private Long id;
    private Long person1;
    private Long person2;
}

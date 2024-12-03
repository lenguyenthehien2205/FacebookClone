export interface Interaction {
    total_interactions: number;
    highest_interaction: string;
    second_highest_interaction: 'like' | 'love' | 'care' | 'haha' | 'wow' | 'sad' | 'angry';
}
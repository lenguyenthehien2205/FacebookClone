import { ChangeDetectionStrategy, Component, inject, input, signal } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { InteractionService } from 'src/app/core/services/interaction.service';
import { ApiResponse } from 'src/app/features/auth/responses/api.response';

@Component({
  selector: 'app-post-interactions',
  templateUrl: './post-interactions.component.html',
  styleUrl: './post-interactions.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PostInteractionsComponent {
  postId = input.required<number>();
  interactionCount = input.required<number>();
  highestInteraction = input.required<string>();
  secondHighestInteraction = input.required<string>();
  commentCount = input.required<number>();
  shareCount = input.required<number>();
  interactionDetails = signal('');
  highestInteractionDetails$ = new BehaviorSubject<string>('Đang tải...');
  secondHighestInteractionDetails$ = new BehaviorSubject<string>('Đang tải...');
  interactionService = inject(InteractionService);
  interactionIcons = [
    {
      name: "like",
      title: "Thích"
    },
    {
      name: "love",
      title: "Yêu thích"
    },
    {
      name: "care",
      title: "Thương thương"
    },
    {
      name: "haha",
      title: "Haha"
    },
    {
      name: "wow",
      title: "Wow"
    },
    {
      name: "sad",
      title: "Buồn"
    },
    {
      name: "angry",
      title: "Phẫn nộ"
    }
  ]

  getTitleByName(name: string): string {
    const interaction = this.interactionIcons.find(item => item.name === name);
    return interaction ? interaction.title : 'Không rõ';
  }
  
  
  loadInteractionDetails() {
    if (!this.interactionDetails()) {
      this.interactionService.getInteractionPost(this.postId()).subscribe({
        next: (response: ApiResponse) => {
          const names = response.data.interactor_name_responses;
          const allNames = names.map((item: { interactor_name: string }) => item.interactor_name);
          const finalString = allNames.concat('và 100 người khác...').join('<br>');
          // const finalString = allNames.join('<br>');
          this.interactionDetails.set(finalString);
        }
      });
    }
  }
  loadInteractionByTypeDetail() {
    // Lấy dữ liệu cho highestInteraction
    this.interactionService
      .getInteractionByTypePost(this.postId(), this.highestInteraction())
      .subscribe({
        next: (response: ApiResponse) => {
          const names = response.data.interactor_name_responses;
          const title = this.getTitleByName(this.highestInteraction());
          const allNames = names.map((item: { interactor_name: string }) => item.interactor_name);
          const finalString = `<h1 class="font-bold">${title}</h1>` + allNames.join('<br>');
          this.highestInteractionDetails$.next(finalString); // Cập nhật dữ liệu
        },
        error: (err) => {
          console.error('Error:', err);
          this.highestInteractionDetails$.next('Không thể tải dữ liệu');
        },
      });

    // Lấy dữ liệu cho secondHighestInteraction
    this.interactionService
      .getInteractionByTypePost(this.postId(), this.secondHighestInteraction())
      .subscribe({
        next: (response: ApiResponse) => {
          const names = response.data.interactor_name_responses;
          const title = this.getTitleByName(this.secondHighestInteraction());
          const allNames = names.map((item: { interactor_name: string }) => item.interactor_name);
          const finalString = `<h1 class="font-bold">${title}</h1>` + allNames.join('<br>');
          this.secondHighestInteractionDetails$.next(finalString); // Cập nhật dữ liệu
        },
        error: (err) => {
          console.error('Error:', err);
          this.secondHighestInteractionDetails$.next('Không thể tải dữ liệu');
        },
      });
  }
}

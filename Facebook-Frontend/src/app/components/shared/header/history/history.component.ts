import { Component, output } from '@angular/core';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {
  closeHistory = output<void>();
  onClose(){
    this.closeHistory.emit();
  }
}

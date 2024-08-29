import { Component, Input, Output, EventEmitter, input, output } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent {
   routerLink = input.required<string>();
   isActive = input.required<boolean>();
   styleClass = input<string>();
   buttonClick = output<void>();

  onClick() {
    this.buttonClick.emit();
  }
}
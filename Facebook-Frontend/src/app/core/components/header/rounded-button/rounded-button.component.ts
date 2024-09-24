import { ChangeDetectionStrategy, Component, input, output, Output } from '@angular/core';

@Component({
  selector: 'app-rounded-button',
  templateUrl: './rounded-button.component.html',
  styleUrl: './rounded-button.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RoundedButtonComponent {
  icon = input.required<string>();
  name = input.required<string>();
  isActive = input.required<boolean>();
  buttonClick = output<void>();

  onClick() {
    this.buttonClick.emit();
  }
}
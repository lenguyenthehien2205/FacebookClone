import { Component, Input, Output, EventEmitter, input, output } from '@angular/core';

@Component({
  selector: 'app-navbar-button',
  templateUrl: './navbar-button.component.html',
  styleUrls: ['./navbar-button.component.css']
})
export class NavbarButtonComponent {
   routerLink = input.required<string>();
   isActive = input.required<boolean>();
   styleClass = input<string>();
   buttonClick = output<void>();

  onClick() {
    this.buttonClick.emit();
  }
}
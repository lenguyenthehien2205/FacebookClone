import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-birthday',
  templateUrl: './birthday.component.html',
  styleUrl: './birthday.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BirthdayComponent {

}

import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-left-panel',
  templateUrl: './left-panel.component.html',
  styleUrl: './left-panel.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LeftPanelComponent {

}
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { ThemeSubInfo } from 'src/app/interfaces/themeSubInfo.interface';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit {
  public themes$!: Observable<ThemeSubInfo[]>;

  constructor(private titleService: Title, private themeService: ThemeService) {}

  ngOnInit(): void {
    // title
    this.titleService.setTitle('Themes / MDD');
    // themes
    this.themes$ = this.themeService.getThemesSubsInfo();
  }

  // subscribe to a theme with themeId => and refresh theme$ (with userIsSubscribed info)
  subscribe(themeId: number): void {
    this.themes$ = this.themeService.subscribeToTheme(themeId);
  }
}

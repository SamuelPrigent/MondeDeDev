import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit {
  public themes$!: Observable<Theme[]>;

  constructor(private titleService: Title, private themeService: ThemeService) {}

  ngOnInit(): void {
    // title
    this.titleService.setTitle('Themes');
    // themes
    this.themes$ = this.themeService.getThemes();
  }

  subscribe(): void {
    console.log('Subscribe to theme :');
  }
}

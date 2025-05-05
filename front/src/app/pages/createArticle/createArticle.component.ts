import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ArticleService } from 'src/app/services/article.service';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-createArticle',
  templateUrl: './createArticle.component.html',
  styleUrls: ['./createArticle.component.scss'],
})
export class CreateArticleComponent implements OnInit {
  public articleForm: FormGroup;
  public themesList$!: Observable<Theme[]>;

  constructor(
    private titleService: Title,
    private articleService: ArticleService,
    private fb: FormBuilder,
    private router: Router,
    private themeService: ThemeService
  ) {
    this.articleForm = this.fb.group({
      theme: ['', Validators.required],
      title: ['', [Validators.required, Validators.maxLength(120)]],
      description: ['', [Validators.required, Validators.maxLength(1500)]],
    });
  }

  ngOnInit(): void {
    // title
    this.titleService.setTitle('Nouvel article / MDD');
    // theme list
    this.themesList$ = this.themeService.getThemes();
  }

  public createArticle(): void {
    if (this.articleForm.valid) {
      const articleData = {
        ...this.articleForm.value,
      };

      this.articleService
        .postArticle(articleData)
        .pipe(
          catchError(error => {
            console.error('Error creating article:', error);
            return of(null);
          })
        )
        .subscribe(response => {
          if (response) {
            // si post créé
            this.router.navigate(['/articles'], {
              state: { showArticleCreated: true },
            });
          }
        });
    }
  }

  public back(): void {
    window.history.back();
  }
}

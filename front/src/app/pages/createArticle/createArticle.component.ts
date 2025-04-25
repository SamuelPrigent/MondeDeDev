import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ArticleService } from 'src/app/services/article.service';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-createArticle',
  templateUrl: './createArticle.component.html',
  styleUrls: ['./createArticle.component.scss'],
})
export class CreateArticleComponent implements OnInit {
  public articleForm: FormGroup;

  constructor(
    private titleService: Title,
    private articleService: ArticleService,
    private fb: FormBuilder,
    private sessionService: SessionService,
    private router: Router
  ) {
    this.articleForm = this.fb.group({
      theme: ['', Validators.required],
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.titleService.setTitle('Nouvel article');
  }

  public createArticle(): void {
    if (this.articleForm.valid) {
      const articleData = {
        ...this.articleForm.value,
        authorId: this.sessionService.sessionInformation!.userId,
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
            this.router.navigate(['/articles']);
          }
        });
    }
  }

  public back(): void {
    window.history.back();
  }
}

import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProgrammService } from '../../services/programm.service';
import { FilmEingebenDto } from '../../dtos/programm';
import { NavbarComponent } from '../../../common/components/navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-film-hinzufuegen-page',
  imports: [
    NavbarComponent,
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './film-hinzufuegen-page.component.html',
  styleUrl: './film-hinzufuegen-page.component.css',
  standalone: true
})
export class FilmHinzufuegenPageComponent {
  filmForm: FormGroup;
  fskOptions = [0, 6, 12, 16, 18];
  erfolgreichHinzugefuegt = false;
  fehler: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private programmService: ProgrammService,
    private router: Router
  ) {
    this.filmForm = this.formBuilder.group({
      titel: ['', Validators.required],
      laufzeit: ['', [Validators.required, Validators.min(1)]],
      posterUrl: [''],
      fsk: [''],
      beschreibung: ['', Validators.required],
      genre: [''],
      hauptdarsteller: [''],
      regie: [''],
      sprache: ['']
    });
  }

  filmHinzufuegen(): void {
    if (this.filmForm.valid) {
      this.fehler = null;
      const filmDto: FilmEingebenDto = {
        titel: this.filmForm.value.titel,
        laufzeit: Number(this.filmForm.value.laufzeit),
        beschreibung: this.filmForm.value.beschreibung,
        posterUrl: this.filmForm.value.posterUrl || undefined,
        fsk: this.filmForm.value.fsk ? Number(this.filmForm.value.fsk) : undefined,
        genre: this.filmForm.value.genre || undefined,
        hauptdarsteller: this.filmForm.value.hauptdarsteller || undefined,
        regie: this.filmForm.value.regie || undefined,
        sprache: this.filmForm.value.sprache || undefined
      };

      this.programmService.fuegeFilmHinzu(filmDto).subscribe({
        next: (film) => {
          this.erfolgreichHinzugefuegt = true;
          this.filmForm.reset();
          setTimeout(() => {
            this.router.navigate(['/programm']);
          }, 2000);
        },
        error: (error) => {
          this.fehler = 'Fehler beim Hinzufügen des Films. Bitte versuchen Sie es erneut.';
          console.error('Error adding film:', error);
        }
      });
    } else {
      this.fehler = 'Bitte füllen Sie alle Pflichtfelder aus.';
    }
  }
}

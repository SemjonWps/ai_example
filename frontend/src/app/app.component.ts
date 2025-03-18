import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Vorstellung, VorstellungService } from './vorstellung.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'frontend';

  vorstellungen: Vorstellung[];

  constructor(private vorstellungService: VorstellungService) {
  }

  ngOnInit() {
    this.vorstellungService.findAll().subscribe(data :Vorstellung[] => {
      this.vorstellungen = data;
      console.log(this.vorstellungen);
    });
  }
}

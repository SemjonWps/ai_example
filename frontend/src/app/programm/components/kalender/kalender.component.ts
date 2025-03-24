import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {DatePipe, NgClass, NgForOf} from '@angular/common';
import {DatumService} from '../../services/datum.service';
import {format, isBefore} from 'date-fns';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {distinctUntilChanged, map} from 'rxjs';

@Component({
  selector: 'app-kalender',
  imports: [
    DatePipe,
    NgClass,
    NgForOf
  ],
  providers: [DatePipe],
  templateUrl: './kalender.component.html',
  styleUrl: './kalender.component.css'
})
export class KalenderComponent implements OnInit {

  constructor(private datumService: DatumService, private router: Router, private readonly activatedRoute: ActivatedRoute) {
    this.activatedRoute.queryParamMap.pipe(
      map((queryParamMap) => queryParamMap.get('datum')),
      map((value) => value ? new Date(value) : undefined),
      distinctUntilChanged())
      .subscribe(value => {
        this.selectedDate = value ?? this.today;
        this.dateSelected.emit(this.selectedDate);
      });
  }

  today: Date = new Date("2025-03-19");

  selectableDates: Date[] = [this.today];

  selectedDate: Date = this.today;

  @Output()
  dateSelected = new EventEmitter<Date>();

  ngOnInit(): void {
    this.selectableDates = this.datumService.getWochentage(this.today)
  }

  selectDate(datum: Date): void {
    this.navigate({'datum': format(datum, 'yyyy-MM-dd')});
  }

  isBeforeToday(date: Date): boolean {
    return isBefore(date, this.today)
  }

  navigate(queryParams: Params): Promise<boolean> {
    return this.router.navigate([], {
      queryParams, queryParamsHandling: 'merge', replaceUrl: true,
    });
  }
}

import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DatePipe, NgClass, NgForOf} from '@angular/common';
import {DatumService} from '../../services/datum.service';
import {isBefore} from 'date-fns';

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

  constructor(private datumService: DatumService) {
  }

  today: Date = new Date("2025-03-19");

  selectableDates: Date[] = [this.today];

  @Input()
  selectedDate: Date = this.today;

  @Output()
  dateSelected = new EventEmitter<Date>();

  ngOnInit(): void {
    this.selectableDates = this.datumService.getWochentage(this.today)
  }

  selectDate(date: Date): void {
    this.selectedDate = date;
    this.dateSelected.emit(date);
  }

  isBeforeToday(date: Date): boolean {
    return isBefore(date, this.today)
  }
}

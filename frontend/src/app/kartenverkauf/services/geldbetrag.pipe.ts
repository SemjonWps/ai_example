import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'geldbetrag'
})
export class GeldbetragPipe implements PipeTransform {

  transform(centBetrag: number): string {
    const centBetragString: string = centBetrag.toString();
    return centBetragString.substring(0, centBetragString.length - 2) + "," + centBetragString.substring(centBetragString.length - 2) + " Euro"
  }
}

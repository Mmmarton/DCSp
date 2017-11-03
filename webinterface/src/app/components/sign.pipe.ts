import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "sign"
})

export class SignPipe implements PipeTransform {
  transform(value) {
    if (value >= 0) {
      return "+" + value;
    }
    return "-" + value;
  }
}
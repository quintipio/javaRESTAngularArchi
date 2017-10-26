import {ChangeDetectorRef, Component,  OnInit} from '@angular/core';
import {CommunicationErrorService} from '../service/CommunicationErrorService';
import {TranslateService} from '../translate/translate.service';

@Component({
  selector:'error-component',
  template:'<div class="alert alert-danger center-text" *ngIf="isError">' +
              '<p>{{error}}</p>' +
            '</div>',
})
export class ErrorComponent implements OnInit{

  isError:boolean;
  error:string;

  constructor(private communicationErrorService: CommunicationErrorService, private ref: ChangeDetectorRef, private _translate: TranslateService) {
  }

  ngOnInit(): void {
    this.communicationErrorService.componentMethodCalled$.subscribe(
      res => {
        if(res.status === 504) {
          this.error =  this._translate.instant('Le serveur ne repond pas');
        }
        else {
          this.error = this._translate.instant('Une erreur inconnue est survenue');
        }
        this.isError = true;
        this.ref.detectChanges();
      }
    )
  }


}

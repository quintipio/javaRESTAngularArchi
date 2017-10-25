import {ChangeDetectorRef, Component,  OnInit} from '@angular/core';
import {CommunicationErrorService} from '../service/CommunicationErrorService';

@Component({
  selector:'error-component',
  template:'<div class="alert alert-danger center-text" *ngIf="isError">' +
              '<p>{{error}}</p>' +
            '</div>',
})
export class ErrorComponent implements OnInit{

  isError:boolean;
  error:string;

  constructor(private communicationErrorService: CommunicationErrorService, private ref: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.communicationErrorService.componentMethodCalled$.subscribe(
      res => {
        if(res.status === 504) {
          this.error = "Le serveur ne répond pas";
        }
        else {
          this.error = "Une erreur inconnue est survenue";
        }
        this.isError = true;
        this.ref.detectChanges();
      }
    )
  }


}

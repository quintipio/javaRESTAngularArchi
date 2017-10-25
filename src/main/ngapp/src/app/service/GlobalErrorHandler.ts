import {ErrorHandler, Injectable} from '@angular/core';
import {CommunicationErrorService} from './CommunicationErrorService';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler{



  constructor(private communicationErrorService : CommunicationErrorService) {

  }

  handleError(error) {
    this.communicationErrorService.callComponentMethod(error);
    throw error;
  }


}


import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class CommunicationErrorService {

  private componentMethodCallSource = new Subject<any>();

  componentMethodCalled$ = this.componentMethodCallSource.asObservable();

  callComponentMethod(error) {
    this.componentMethodCallSource.next(error);
  }
}

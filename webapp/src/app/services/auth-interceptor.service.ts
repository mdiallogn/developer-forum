import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, of} from "rxjs";
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Global} from "../global-classes/global";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private AUTH_HEADER = 'Authorization';
  private token = Global.TOKEN;
  private refreshTokenInProgress = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    //set authorization for path /users/all
    if (req.url.includes("/users/all")){
      req = req.clone({
        headers: new HttpHeaders({Authorization: 'Bearer ' + Global.TOKEN})
      });
    }
    return next.handle(req);
    // return next.handle(req).pipe(
    //   catchError((error: HttpErrorResponse) => {
    //     if (error && error.status === 401) {
    //       // 401 errors are most likely going to be because we have an expired token that we need to refresh.
    //       if (this.refreshTokenInProgress) {
    //         // If refreshTokenInProgress is true, we will wait until refreshTokenSubject has a non-null value
    //         // which means the new token is ready and we can retry the request again
    //         return this.refreshTokenSubject.pipe(
    //           filter(result => result !== null),
    //           take(1),
    //           switchMap(() => next.handle(this.addAuthenticationToken(req)))
    //         );
    //       } else {
    //         this.refreshTokenInProgress = true;
    //
    //         // Set the refreshTokenSubject to null so that subsequent API calls will wait until the new token has been retrieved
    //         this.refreshTokenSubject.next(null);
    //
    //         return this.refreshAccessToken().pipe(
    //           switchMap((success: boolean) => {
    //             this.refreshTokenSubject.next(success);
    //             return next.handle(this.addAuthenticationToken(req));
    //           }),
    //           // When the call to refreshToken completes we reset the refreshTokenInProgress to false
    //           // for the next time the token needs to be refreshed
    //           finalize(() => this.refreshTokenInProgress = false)
    //         );
    //       }
    //     } else {
    //       return throwError(error);
    //     }
    //   })
    // );
  }

  private refreshAccessToken(): Observable<any> {
    return of(Global.TOKEN);
  }

  private addAuthenticationToken(request: HttpRequest<any>): HttpRequest<any> {
    if (!this.token) {
      return request;
    }
    return request.clone({
      headers: request.headers.append('Authorization', 'Bearer ' + this.token)//this.AUTH_HEADER
    });
  }
}

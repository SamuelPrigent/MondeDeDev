import { timer, Observable } from 'rxjs';

export const DEFAULT_LOADING_DELAY = 300;

export function loadingDelay$(ms: number = DEFAULT_LOADING_DELAY): Observable<number> {
  return timer(ms);
}

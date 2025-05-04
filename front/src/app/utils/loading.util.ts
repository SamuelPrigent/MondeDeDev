import { timer, Observable } from 'rxjs';

export const DEFAULT_LOADING_DELAY = 260;

export function loadingDelay$(ms: number = DEFAULT_LOADING_DELAY): Observable<number> {
  return timer(ms);
}

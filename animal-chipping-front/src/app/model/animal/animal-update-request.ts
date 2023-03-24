import { AnimalRequest } from './animal-request';
import { LifeStatus } from './enum/life-status.enum';

export interface AnimalUpdateRequest extends AnimalRequest {
  lifeStatus: LifeStatus;
}

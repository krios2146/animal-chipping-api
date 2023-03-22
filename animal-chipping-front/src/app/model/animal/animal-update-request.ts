import { AnimalRequest } from './animal-request';

export interface AnimalUpdateRequest extends AnimalRequest {
  lifeStatus: string;
}

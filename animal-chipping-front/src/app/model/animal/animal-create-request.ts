import { AnimalRequest } from './animal-request';

export interface AnimalCreateRequest extends AnimalRequest {
  animalTypes: number[];
}

import { Gender } from "./enum/gender.enum";
import { LifeStatus } from "./enum/life-status.enum";

export interface AnimalResponse {
  id: number;
  animalTypes: number[];
  weight: number;
  length: number;
  height: number;
  gender: Gender;
  lifeStatus: LifeStatus;
  chippingDateTime: string;
  chipperId: number;
  chippingLocationId: number;
  visitedLocations: number[];
  deathDateTime: string;
}

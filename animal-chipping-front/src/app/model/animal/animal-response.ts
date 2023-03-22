export interface AnimalResponse {
  id: number;
  animalTypes: number[];
  weight: number;
  length: number;
  height: number;
  gender: string;
  lifeStatus: string;
  chippingDateTime: string;
  chipperId: number;
  chippingLocationId: number;
  visitedLocations: number[];
  deathDateTime: string;
}

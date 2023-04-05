import { Gender } from "./enum/gender.enum";

export interface AnimalRequest {
  weight: number;
  length: number;
  height: number;
  gender: Gender;
  chipperId: number;
  chippingLocationId: number;
}

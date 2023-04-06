import { LocationResponse } from "../location/location-response";

export interface VisitedLocation {
    id: number;
    dateTimeOfVisitLocationPoint: string;
    location: LocationResponse;
}
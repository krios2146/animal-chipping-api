# Animal Chipping API

## Entities

- Account
- Animal
- Animal Type
- Location
- Animal Visited Location

## API Declarations

The JSON format is used in the Body of the request.

### User authentication

#### Registration

**POST** ~ /registration

Request

```json
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "password": "string"
}
```

Response

```json
{
  "id": "long",
  "firstName": "string",
  "lastName": "string",
  "email": "string"
}
```

### Accounts

#### Account info by ID

**GET** ~ /accounts/{accountId}

Request

```json
{
}
```

Response

```json
 {
  "id": "long",
  "firstName": "string",
  "lastName": "string",
  "email": "string"
}
```

#### Search account by parameters

**GET** ~ /accounts/search?firstName={firstName}  
&lastName={lastName}  
&email={email}  
&from={from}  
&size={size}

Parameters

- `firstName` // only the part of the name can be used, case-insensitive, if null, does not participate in filtering
- `lastName` // only the part of the name can be used, case-insensitive, if null, does not participate in filtering
- `email` // only the part of the name can be used, case-insensitive, if null, does not participate in filtering
- `from` // the number of items to skip to generate the results page (0 by default)
- `size` // the number of items on the page (10 by default)

Request

```json
{
}
```

Response

```json
 [
  {
    "id": "long",
    "firstName": "string",
    "lastName": "string",
    "email": "string"
  }
]
```

#### Update user account details

**PUT** ~ /accounts/{accountId}

Request

```json
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "password": "string"
}
```

Response

```json
{
  "id": "long",
  "firstName": "string",
  "lastName": "string",
  "email": "string"
}
```

#### Delete user account

**DELETE** ~ /accounts/{accountId}

Request

```json
{
}
```

Response

```json
{
}
```

### Locations

#### Location info by ID

**GET** ~ /locations/{locationId}

Request

```json
{
}
```

Response

```json
{
  "id": "long",
  "latitude": "double",
  "longitude": "double"
}
```

#### Add location

**POST** ~ /locations

Request

```json
{
  "latitude": "double",
  "longitude": "double"
}
```

Response

```json
{
  "id": "long",
  "latitude": "double",
  "longitude": "double"
}
```

#### Update location details

**PUT** ~ /locations/{locationId}

Request

```json
{
  "latitude": "double",
  "longitude": "double"
}
```

Response

```json
{
  "id": "long",
  "latitude": "double",
  "longitude": "double"
}
```

#### Delete location

**DELETE** ~ /locations/{locationId}

Request

```json
{
}
```

Response

```json
{
}
```

### Animal types

#### Animal type info by ID

**GET** ~ /animals/types/{typeId}

Request

```json
{
}
```

Response

```json
{
  "id": "long",
  "type": "string"
}
```

#### Add animal type

**POST** ~ /animals/types

Request

```json
{
  "type": "string"
}
```

Response

```json
{
  "id": "long",
  "type": "string"
}
```

#### Update animal type details

**PUT** ~ /animals/types/{typeId}

Request

```json
{
  "type": "string"
}
```

Response

```json
{
  "id": "long",
  "type": "string"
}
```

#### Delete animal type

**DELETE** ~ /animals/types/{typeId}

Request

```json
{
}
```

Response

```json
{
}
```

### Animal

#### Animal info by ID

**GET** ~ /animals/{animalId}

Request

```json
{
}
```

Response

```json
{
  "id": "long",
  "animalTypes": "[long]",
  "weight": "float",
  "length": "float",
  "height": "float",
  "gender": "string",
  "lifeStatus": "string",
  "chippingDateTime": "dateTime",
  "chipperId": "long",
  "chippingLocationId": "long",
  "visitedLocations": "[long]",
  "deathDateTime": "dateTime"
}
```

#### Search animal by parameters

**GET** ~ /animals/search?startDateTime={startDateTime}  
&endDateTime={endDateTime}  
&chipperId={chipperId}  
&chippingLocationId={chippingLocationId}  
&lifeStatus={lifeStatus}
&gender={gender}
&from={from}
&size={size}

Parameters

- `startDateTime` // date and time before which the animal was chipped, if null, doesn't participate in filtering
- `endDateTime` // date and time up to which the animal was chipped, if null, doesn't participate in filtering
- `chipperId` // if null, does not participate in filtering
- `chippingLocationId` // if null, does not participate in filtering
- `lifeStatus` // if null, does not participate in filtering
- `gender` // if null, does not participate in filtering
- `from` // the number of items to skip to generate the results page (0 by default)
- `size` // the number of items on the page (10 by default)

Request

```json
{
}
```

Response

```json
 [
  {
    "id": "long",
    "animalTypes": "[long]",
    "weight": "float",
    "length": "float",
    "height": "float",
    "gender": "string",
    "lifeStatus": "string",
    "chippingDateTime": "dateTime",
    "chipperId": "long",
    "chippingLocationId": "long",
    "visitedLocations": "[long]",
    "deathDateTime": "dateTime"
  }
]
```

#### Add animal

**POST** ~ /animals

Request

```json
{
  "animalTypes": "[long]",
  "weight": "float",
  "length": "float",
  "height": "float",
  "gender": "string",
  "chipperId": "int",
  "chippingLocationId": "long"
}
```

Response

```json
{
  "id": "long",
  "animalTypes": "[long]",
  "weight": "float",
  "length": "float",
  "height": "float",
  "gender": "string",
  "lifeStatus": "string",
  "chippingDateTime": "dateTime",
  "chipperId": "long",
  "chippingLocationId": "long",
  "visitedLocations": "[long]",
  "deathDateTime": "dateTime"
}
```

#### Update animal details

**PUT** ~ /animals/{animalId}

Request

```json
{
  "weight": "float",
  "length": "float",
  "height": "float",
  "gender": "string",
  "lifeStatus": "string",
  "chipperId": "int",
  "chippingLocationId": "long"
}
```

Response

```json
{
  "id": "long",
  "animalTypes": "[long]",
  "weight": "float",
  "length": "float",
  "height": "float",
  "gender": "string",
  "lifeStatus": "string",
  "chippingDateTime": "dateTime",
  "chipperId": "long",
  "chippingLocationId": "long",
  "visitedLocations": "[long]",
  "deathDateTime": "dateTime"
}
```

#### Delete animal

**DELETE** ~ /animals/{animalId}

Request

```json
{
}
```

Response

```json
{
}
```

#### Add animal type to the animal

**POST** ~ /animals/{animalId}/types/{typeId}

Request

```json
{
}
```

Response

```json
{
  "id": "long",
  "animalTypes": "[long]",
  "weight": "float",
  "length": "float",
  "height": "float",
  "gender": "string",
  "lifeStatus": "string",
  "chippingDateTime": "dateTime",
  "chipperId": "long",
  "chippingLocationId": "long",
  "visitedLocations": "[long]",
  "deathDateTime": "dateTime"
}
```

#### Update animal type of the animal

**PUT** ~ /animals/{animalId}/types

Request

```json
{
  "oldTypeId": "long",
  "newTypeId": "long"
}
```

Response

```json
{
  "id": "long",
  "animalTypes": "[long]",
  "weight": "float",
  "length": "float",
  "height": "float",
  "gender": "string",
  "lifeStatus": "string",
  "chippingDateTime": "dateTime",
  "chipperId": "long",
  "chippingLocationId": "long",
  "visitedLocations": "[long]",
  "deathDateTime": "dateTime"
}
```

#### Delete animal type of the animal

**DELETE** ~ /animals/{animalId}/types/{typeId}

Request

```json
{
}
```

Response

```json
{
  "id": "long",
  "animalTypes": "[long]",
  "weight": "float",
  "length": "float",
  "height": "float",
  "gender": "string",
  "lifeStatus": "string",
  "chippingDateTime": "dateTime",
  "chipperId": "long",
  "chippingLocationId": "long",
  "visitedLocations": "[long]",
  "deathDateTime": "dateTime"
}
```

### Animal Visited Locations

#### Visited locations info

**GET** ~ /animals/{animalId}/locations
?startDateTime={startDateTime}
&endDateTime={endDateTime}  
&from={from}  
&size={size}

Parameters

- `startDateTime` // date and time before which the visited animal locations should be searched, if null, doesn't
  participate in filtering
- `endDateTime` // date and time, up to which visited animal locations should be searched, if null, doesn't participate
  in filtering
- `from` // the number of items to skip to generate the results page (0 by default)
- `size` // the number of items on the page (10 by default)

Request

```json
{
}
```

Response

```json
[
  {
    "id": "long",
    "dateTimeOfVisitLocation": "dateTime",
    "locationId": "long"
  }
]
```

#### Add visited location to an animal

**POST** ~ /animals/{animalId}/locations/{locationId}

Request

```json
{
}
```

Response

```json
[
  {
    "id": "long",
    "dateTimeOfVisitLocation": "dateTime",
    "locationId": "long"
  }
]
```

#### Update visited location of an animal

**PUT** ~ /animals/{animalId}/locations

Request

```json
{
  "visitedLocationId": "long",
  "locationId": "long"
}
```

Response

```json
[
  {
    "id": "long",
    "dateTimeOfVisitLocation": "dateTime",
    "locationId": "long"
  }
]
```

#### Update visited location of an animal

**PUT** ~ /animals/{animalId}/locations/{visitedLocationId}

Request

```json
{
}
```

Response

```json
{
}
```

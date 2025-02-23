# TV Show Module API Documentation

## TV Show Module

### TV Shows

| HTTP Method | Endpoint           | Description                            | Access Level | Request Body | Response         |
| ----------- | ------------------ | -------------------------------------- | ------------ | ------------ | ---------------- |
| **GET**     | `/tv-shows`        | Retrieve all TV shows using pagination | Public       | None         | List of TV shows |
| **GET**     | `/tv-shows/{id}`   | Retrieve a specific TV show by ID      | Public       | None         | TV show details  |
| **POST**    | `/tv-shows`        | Create a new TV show                   | Admin Only   | TV show data | Created TV show  |
| **POST**    | `/tv-shows/upload` | Upload a list of TV shows using CSV    | Admin Only   | CSV file     | Status message   |
| **PUT**     | `/tv-shows/{id}`   | Update a TV show                       | Admin Only   | TV show data | Updated TV show  |
| **DELETE**  | `/tv-shows/{id}`   | Soft delete a TV show                  | Admin Only   | None         | Status message   |

### TV Show Cast

| HTTP Method | Endpoint                     | Description                                                       | Access Level | Request Body                           | Response             |
| ----------- | ---------------------------- | ----------------------------------------------------------------- | ------------ | -------------------------------------- | -------------------- |
| **GET**     | `/tv-shows/{id}/cast`        | Retrieve cast for a TV show                                       | Public       | None                                   | List of cast members |
| **POST**    | `/tv-shows/{id}/cast`        | Add cast to a TV show                                             | Admin Only   | Cast details                           | Added cast member    |
| **PATCH**   | `/tv-shows/cast/{id}`        | Update the character name & season number of a TV show cast by ID | Admin Only   | Character name & Season Number Request | Updated cast details |
| **DELETE**  | `/tv-shows/cast/{id}/delete` | Soft delete a TV show cast by ID                                  | Admin Only   | None                                   | Status message       |

### TV Show Seasons & Episodes

#### TV Show Seasons

| HTTP Method | Endpoint                 | Description                                     | Access Level | Request Body   | Response       |
| ----------- | ------------------------ | ----------------------------------------------- | ------------ | -------------- | -------------- |
| **GET**     | `/tv-shows/seasons/{id}` | Retrieve a specific TV show season with details | Public       | None           | Season details |
| **POST**    | `/tv-shows/seasons`      | Create a new TV season                          | Admin Only   | Season details | Created season |
| **PUT**     | `/tv-shows/seasons/{id}` | Update a TV show season                         | Admin Only   | Season details | Updated season |
| **DELETE**  | `/tv-shows/seasons/{id}` | Soft delete a TV show season                    | Admin Only   | None           | Status message |

#### TV Show Episodes

| HTTP Method | Endpoint                         | Description                         | Access Level | Request Body    | Response         |
| ----------- | -------------------------------- | ----------------------------------- | ------------ | --------------- | ---------------- |
| **GET**     | `/tv-shows/seasons/episodes`     | Retrieve all TV show episodes       | Public       | None            | List of episodes |
| **GET**     | `/tv-shows/seasons/episode/{id}` | Retrieve a specific TV show episode | Public       | None            | Episode details  |
| **POST**    | `/tv-shows/seasons/episode`      | Create a new TV show episode        | Admin Only   | Episode details | Created episode  |
| **PUT**     | `/tv-shows/seasons/episode/{id}` | Update a TV show episode            | Admin Only   | Episode details | Updated episode  |
| **DELETE**  | `/tv-shows/seasons/episode/{id}` | Soft delete a TV show episode       | Admin Only   | None            | Status message   |

## Notes

- **Pagination** is applied when retrieving all TV shows.
- **Soft Delete** means the record is marked as deleted but not permanently removed.
- **Admin Only** endpoints require authentication and admin privileges.
- **CSV Upload** should follow the correct format to avoid parsing issues.

### Authentication & Authorization

- Public endpoints can be accessed without authentication.
- Admin-only endpoints require a valid **JWT token** with **admin role**.

### Error Handling

- **400 Bad Request**: Invalid input data.
- **401 Unauthorized**: User not authenticated.
- **403 Forbidden**: User does not have admin rights.
- **404 Not Found**: Requested resource does not exist.
- **500 Internal Server Error**: Unexpected server error.

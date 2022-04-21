
# Unknown's Issue Tracker backend

My own implementation of an issue tracker aimed mostly at my needs.




## Deployment

This project is deployed on [Heroku](https://issue-tracker-upce-heroku.herokuapp.com/swagger-ui/).


## Environment Variables

To run this project, you will need to configure the following environment variables

`DATABASE_URL`
`JWT_SECRET`
`FRONTEND_URL`


## Features

- Issue management
- Comment management
- User management
- Pagination
- Sorting of returned data
- JWT authentication


## API Reference

#### Get all issues

```http
  GET /api/v1/issues/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `Authorization` | `string` | Generated JWT token |

#### Get issue by id

```http
  GET /api/v1/issues/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization` | `string` | Generated JWT token |
| `id`      | `long` | **Required**. Id of issue to fetch |

#### Get issues by author's username

```http
  GET /api/v1/issues/{username}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization` | `string` | Generated JWT token |
| `username`      | `string` | **Required**. Username of the author of the issues to fetch |

#### Post new issue

```http
  POST /api/v1/issues/create
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `Authorization` | `string` | **Required**. Generated JWT token |
| `body` | `IssueCreateDto` | **Required**. The issue to create |

#### Delete issue by id

```http
  DELETE /api/v1/issues/delete/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `Authorization` | `string` | **Required**. Generated JWT token |
| `id`      | `long` | **Required**. Id of issue to delete |

#### Put issue

```http
  PUT /api/v1/issues/update/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `Authorization` | `string` | **Required**. Generated JWT token |
| `id`      | `long` | **Required**. Id of issue to update |
| `body` | `IssueCreateDto` | **Required**. The issue to create |
## Related

Link to frontend repository

[Frontend](https://github.com/Unkn0wn126/nnpia_sem_pr_frontend)


## Acknowledgements

 - [Readme created with readme.so](https://readme.so)
 - [Made with the help of Baeldung tutorials](https://www.baeldung.com/)
 - [Made with the help of Bezkoder tutorials](https://www.bezkoder.com/)
 - [Made with the help of Petr Filip's tutorials](https://www.youtube.com/c/PetrFilipTix)

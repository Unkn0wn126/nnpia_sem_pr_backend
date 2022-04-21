
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

### Comments

```http
GET /api/v1/comments/
```

Get all comments

##### Parameters

| Name          | Located in | Description | Required | Schema     |
|---------------|------------|-------------| -------- |------------|
| direction     | query      | direction   | No | string     |
| orderBy       | query      | orderBy     | No | [ string ] |
| pageNumber    | query      | pageNumber  | No | integer    |
| pageSize      | query      | pageSize    | No | integer    |
| Authorization | headers    | JWT token   | No | string     |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [CommentPageGetDto](#commentpagegetdto) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

```http
POST /api/v1/comments/create/{id}
```

Post comment to issue

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- |----------| ---- |
| commentCreateDto | body | commentCreateDto | Yes      | [CommentCreateDto](#commentcreatedto) |
| id | path | id | Yes      | long |
| Authorization | headers    | JWT token   | Yes      | string     |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 201 | Created |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |

```http
DELETE /api/v1/comments/delete/{id}
```

Delete comment by id

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |
| Authorization | headers    | JWT token   | Yes      | string     |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 204 | No Content |
| 401 | Unauthorized |
| 403 | Forbidden |

```http
GET /api/v1/comments/issue/{id}
```

Get comments by issue id

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- |----------| ---- |
| direction | query | direction | No       | string |
| id | path | id | Yes      | long |
| orderBy | query | orderBy | No       | [ string ] |
| pageNumber | query | pageNumber | No       | integer |
| pageSize | query | pageSize | No       | integer |
| Authorization | headers    | JWT token   | No       | string     |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [CommentPageGetDto](#commentpagegetdto) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

```http
PUT /api/v1/comments/update/{id}
```

Update comment by id

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| commentUpdateDto | body | commentUpdateDto | Yes | [CommentUpdateDto](#commentupdatedto) |
| id | path | id | Yes | long |
| Authorization | headers    | JWT token   | Yes      | string     |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 201 | Created |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |

```http
GET /api/v1/comments/user/{username}
```

Get comments by author username

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| direction | query | direction | No | string |
| orderBy | query | orderBy | No | [ string ] |
| pageNumber | query | pageNumber | No | integer |
| pageSize | query | pageSize | No | integer |
| username | path | username | Yes | string |
| Authorization | headers    | JWT token   | No       | string     |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [CommentPageGetDto](#commentpagegetdto) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

```http
GET /api/v1/comments/{id}
```

getCommentById

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |
| Authorization | headers    | JWT token   | No       | string     |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [CommentGetDto](#commentgetdto) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### Issues

```http
GET /api/v1/issues/
```

Get all issues

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| direction | query | direction | No | string |
| orderBy | query | orderBy | No | [ string ] |
| pageNumber | query | pageNumber | No | integer |
| pageSize | query | pageSize | No | integer |
| Authorization | headers    | JWT token   | No       | string     |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [IssuePageGetDto](#issuepagegetdto) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

```http
POST /api/v1/issues/create
```

Post issue

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- |----------| ---- |
| issueCreateDto | body | issueCreateDto | Yes      | [IssueCreateDto](#issuecreatedto) |
| Authorization | headers    | JWT token   | Yes      | string     |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 201 | Created |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |

```http
DELETE /api/v1/issues/delete/{id}
```

#### DELETE
##### Summary

Delete issue by id

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |
| Authorization | headers    | JWT token   | Yes      | string     |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 204 | No Content |
| 401 | Unauthorized |
| 403 | Forbidden |

```http
PUT /api/v1/issues/update/{id}
```

Update issue by id

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |
| issueUpdateDto | body | issueUpdateDto | Yes | [IssueUpdateDto](#issueupdatedto) |
| Authorization | headers    | JWT token   | Yes      | string     |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 201 | Created |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |

```http
GET /api/v1/issues/user/{username}
```

Get issues by author username

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- |----------| ---- |
| direction | query | direction | No       | string |
| orderBy | query | orderBy | No       | [ string ] |
| pageNumber | query | pageNumber | No       | integer |
| pageSize | query | pageSize | No       | integer |
| username | path | username | Yes      | string |
| Authorization | headers    | JWT token   | No       | string     |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [IssuePageGetDto](#issuepagegetdto) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

```http
GET /api/v1/issues/{id}
```

Get issue by id

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |
| Authorization | headers    | JWT token   | No       | string     |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [IssueGetDto](#issuegetdto) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### Authentication

```http
POST /api/v1/login
```

Login

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| loginRequest | body | loginRequest | Yes | [JwtRequest](#jwtrequest) |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

```http
POST /api/v1/register
```

Register

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| userRegisterDto | body | userRegisterDto | Yes | [ApplicationUserCreateDto](#applicationusercreatedto) |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 201 | Created |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |

### Users

```http
GET /api/v1/users/
```

Get users

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| direction | query | direction | No | string |
| orderBy | query | orderBy | No | [ string ] |
| pageNumber | query | pageNumber | No | integer |
| pageSize | query | pageSize | No | integer |
| Authorization | headers    | JWT token   | No       | string     |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ApplicationUserPageGetDto](#applicationuserpagegetdto) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

```http
DELETE /api/v1/users/delete/{id}
```

Delete user by id

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- |----------| ---- |
| id | path | id | Yes      | long |
| Authorization | headers    | JWT token   | Yes      | string     |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 204 | No Content |
| 401 | Unauthorized |
| 403 | Forbidden |

```http
GET /api/v1/users/id/{id}
```

Get user by id

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |
| Authorization | headers    | JWT token   | No       | string     |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ApplicationUserGetDto](#applicationusergetdto) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

```http
PUT /api/v1/users/update/password/{id}
```

Update user password by id

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |
| userUpdateDto | body | userUpdateDto | Yes | [ApplicationUserUpdatePasswordDto](#applicationuserupdatepassworddto) |
| Authorization | headers    | JWT token   | Yes      | string     |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 201 | Created |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |

```http
PUT /api/v1/users/update/{id}
```

Update user by id

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | id | Yes | long |
| userUpdateDto | body | userUpdateDto | Yes | [ApplicationUserUpdateDto](#applicationuserupdatedto) |
| Authorization | headers    | JWT token   | Yes      | string     |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 201 | Created |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |

```http
GET /api/v1/users/{username}
```

Get user by username

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- |----------| ---- |
| username | path | username | Yes      | string |
| Authorization | headers    | JWT token   | No       | string     |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ApplicationUserGetDto](#applicationusergetdto) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### Models

#### ApplicationUserCreateDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| email | string |  | Yes |
| password | string |  | Yes |
| profile | [ProfileCreateDto](#profilecreatedto) |  | No |
| username | string |  | Yes |

#### ApplicationUserGetDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| created | [Timestamp](#timestamp) |  | No |
| email | string |  | Yes |
| id | long |  | No |
| lastEdited | [Timestamp](#timestamp) |  | No |
| profile | [ProfileGetDto](#profilegetdto) |  | No |
| roles | [ [RoleGetDto](#rolegetdto) ] |  | No |
| state | string | _Enum:_ `"ACTIVE"`, `"BANNED"`, `"INACTIVE"` | Yes |
| username | string |  | Yes |

#### ApplicationUserPageGetDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| currentPage | integer |  | No |
| totalItems | long |  | No |
| totalPages | integer |  | No |
| users | [ [ApplicationUserGetDto](#applicationusergetdto) ] |  | No |

#### ApplicationUserUpdateDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| email | string |  | No |
| profile | [ProfileUpdateDto](#profileupdatedto) |  | Yes |
| state | string |  | Yes |

#### ApplicationUserUpdatePasswordDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| newPassword | string |  | Yes |
| oldPassword | string |  | Yes |

#### CommentCreateDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| content | string |  | Yes |

#### CommentGetDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| author | [ApplicationUserGetDto](#applicationusergetdto) |  | No |
| content | string |  | No |
| created | [Timestamp](#timestamp) |  | No |
| id | long |  | No |
| lastEdited | [Timestamp](#timestamp) |  | No |

#### CommentPageGetDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| comments | [ [CommentGetDto](#commentgetdto) ] |  | No |
| currentPage | integer |  | No |
| totalItems | long |  | No |
| totalPages | integer |  | No |

#### CommentUpdateDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| content | string |  | Yes |

#### IssueCreateDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| content | string |  | Yes |
| dueDate | dateTime |  | No |
| header | string |  | Yes |
| severity | string |  | Yes |
| visibility | string |  | Yes |

#### IssueGetDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| author | [ApplicationUserGetDto](#applicationusergetdto) |  | No |
| completionState | string | _Enum:_ `"DONE"`, `"IN_PROGRESS"`, `"TODO"` | Yes |
| content | string |  | No |
| dueDate | dateTime |  | No |
| header | string |  | No |
| id | long |  | No |
| lastEdited | [Timestamp](#timestamp) |  | No |
| published | [Timestamp](#timestamp) |  | No |
| severity | string | _Enum:_ `"HIGH"`, `"LOW"`, `"MEDIUM"` | Yes |
| visibility | string | _Enum:_ `"INTERNAL"`, `"PRIVATE"`, `"PUBLIC"` | Yes |

#### IssuePageGetDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| currentPage | integer |  | No |
| issues | [ [IssueGetDto](#issuegetdto) ] |  | No |
| totalItems | long |  | No |
| totalPages | integer |  | No |

#### IssueUpdateDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| completionState | string |  | Yes |
| content | string |  | Yes |
| dueDate | dateTime |  | No |
| header | string |  | Yes |
| severity | string |  | Yes |
| visibility | string |  | Yes |

#### JwtRequest

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| password | string |  | No |
| username | string |  | No |

#### ProfileCreateDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| nickname | string |  | Yes |
| profilePicture | string |  | No |

#### ProfileGetDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| created | Timestamp |  | No |
| id | long |  | No |
| lastEdited | Timestamp |  | No |
| nickname | string |  | Yes |
| profilePicture | string |  | No |

#### ProfileUpdateDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| nickname | string |  | Yes |
| profilePicture | string |  | No |

#### RoleGetDto

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| type | string | _Enum:_ `"ROLE_ADMIN"`, `"ROLE_USER"` | No |

## Related

Link to frontend repository

[Frontend](https://github.com/Unkn0wn126/nnpia_sem_pr_frontend)


## Acknowledgements

 - [Readme created with readme.so](https://readme.so)
 - [Made with the help of Baeldung tutorials](https://www.baeldung.com/)
 - [Made with the help of Bezkoder tutorials](https://www.bezkoder.com/)
 - [Made with the help of Petr Filip's tutorials](https://www.youtube.com/c/PetrFilipTix)

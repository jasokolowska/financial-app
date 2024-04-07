<p>
    <img src="https://img.shields.io/github/last-commit/jasokolowska/financial-app/main" alt="Last update">
    <img src="https://img.shields.io/github/contributors/jasokolowska/financial-app" alt="Number of contributors">
    <img src="https://img.shields.io/github/forks/jasokolowska/financial-app" alt="Number of forks">
    <img src="https://img.shields.io/github/stars/jasokolowska/financial-app" alt="Number of stars">
</p>

# Financial App

Full-stack app for manag personal budget. Allows you to enter new budgets and enter expenses divided into categories.
&nbsp;

## Features

- Adding budgets
- Adding expenses
- Statistics on expense categories
- Full responsive version

&nbsp;

## Demo

tbd

&nbsp;

## Tech Stack

**Client:**
- React
- SCSS
- HTML

**Server:**
- Java 17
- Spring Boot 3
- PostgreSql
- Springdoc Openapi
- Mapstruct
- Liquibase
- Docker
&nbsp;
## Installation

### Prerequisites

You will need the following things properly installed on your computer.

- [Git](https://git-scm.com/)

### Installation

- `git clone https://github.com/jasokolowska/financial-app` this repository
- `cd financial-app`

### Running / Development
- run command `docker-compose build`
- run command `docker-compose up`
- you can check out all available and working endpoints at http://localhost:8089/swagger-ui/index.html
- for each endpoint you can test how it works with 'Try it out' button and it will connect with backend side (it works as Postman - you have to provide a correct body execute the request and the you will get the response form the backend)
- you need user credentials for most of the operations (beside register or login)
  - for testing restricted endpoints you need a JWT Token, 
  - to receive JWT Token, register new user with designed endpoint, in the response, you will receive token in the response:
  
  `{
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHVzZXIuY29tIiwiZXhwIjoxNzEwNTM1NDg4fQ.xNNJc-JRe6pLW86Xva9JhCapzY_jh4DpVlthEvDlBhM"
    }`
  - then you can authorize your request by pasting the token into 
  - ![img.png](img.png)
- &nbsp;
## Special thanks

Thanks to our Mentors from [nowoczesnyjunior.pl](https://nowoczesnyjunior.pl/)

Root-Endpoint: http://localhost:8080/student-management-system/api/v1/student

**Endpoints:**
GET(allStudents): ""
GET (bylastname) QUERY: /getbylastname
POST: ""
DELETE: /{id}
PUT: ""
PATCH: /{id}

**h2-Console**
Address: http://localhost:8080/student-management-system/h2
JDBC-URL: jdbc:h2:mem:s-m-s
Username: sa
Password: sa

**Example-POST**
JSON-body: 
{
"firstName" :"karin",
"lastName" :"ismael",
"email" :"karin@ismael.com",
"phoneNumber" : "0835623"
}

**Example-PUT**
JSON-body:
{
"id" : "1",
"firstName" :"karin",
"lastName" :"ismael",
"email" :"karin@ismael.com",
"phoneNumber" : "076521"
}

**Example-PATCH**
JSON-body:
{
"firstName" :"anna",
"lastName" :"eriksson"
}

**Example-QUERY**
name: "lastName"
value: "ismael"

Root-Endpoints: 
http://localhost:8080/student-management-system/api/v1/student
http://localhost:8080/student-management-system/api/v1/subject
http://localhost:8080/student-management-system/api/v1/teacher

**h2-Console**
Address: http://localhost:8080/student-management-system/h2
JDBC-URL: jdbc:h2:mem:s-m-s
Username: sa
Password: sa
--------------------------------------------------------------

STUDENT
**Endpoints:**
GET(allStudents): ""
GET (bylastname) QUERY: /getbylastname
POST: ""
DELETE: /{id}
PUT: ""
PATCH: /{id}

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
--------------------------------------------------------------

SUBJECT
**Endpoints:**
GET(allSubjects): ""
GET (byid): /{id} 
POST: ""
DELETE: /{id}

**Example-POST**
JSON-body:
{
"name" :"Math"
}
----------------------------------------------------------------

TEACHER
**Endpoints:**
GET(allTeachers): ""
GET (byid): /{id}
POST: ""
DELETE: /{id}

**Example-POST**
JSON-body:
{
"firstName" :"bettan",
"lastName" :"bettsson",
"email" :"bettan@bettsson.com",
"phoneNumber" : "0104378"
}
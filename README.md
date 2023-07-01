# Custumer Service

### EndPoints:

Consulta de customer
#Get http://localhost:8080/customers

response: 
```json
[
    {
        "id": 1,
        "name": "Mariazinha",
        "email": "mariazinha@email.com",
        "addresses": []
    },
    {
        "id": 2,
        "name": "Joãozinho",
        "email": "joaozinho@email.com",
        "addresses": []
    }
]
```
cadastro de customer
@Post http://localhost:8080/customers
body:
```json
{
    "name": "Lucas Maraia",
    "email": "l.cliniomaraia@gmail.com"
   
}
```
atualização de customer
@Put http://localhost:8080/customers/{id}
body:
```json
{
    "name": "Lucas Clinio Maraia",
    "email": "l.cliniomaraia@gmail.com"
   
}
```
exclusão de customer
@Delete http://localhost:8080/customers/{id}

cadastro de endereço customer
@Post http://localhost:8080/customers/{id}/addresses
body:
```json
[
  {
    "street": "Avenida dos Ourives",
    "city": "São Paulo",
    "addressComplement": "bloco 5 ap 81"
  },
  {
   "street": "Rua Joao Semeraro",
    "city": "São Paulo",
    "addressComplement": "bloco 1 ap 22"
  }
]





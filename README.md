# Gestao de Hospedagem
Este projeto permite cadastrar novos hóspedes e realizar reservas com datas para checkin e checkout.
 
## API Reference
### URL Base: http://localhost:8080/
#### Salvar novo hóspede
 
```http
 POST /api/hospede
```
 
| Parameter | Type | Description |
| :-------- | :------- | :------------------------- |
| `Body` | `void` | **Salvar um novo hóspede no banco de dados** |
 
```
Response - 200
```
```
RequestBody
{
 "name": String,
 "cpf": String
 "phone": String
}
```
 
#### Listagem de hópedes
 
```http
 GET /api/hospede
```
 
| Parameter | Type | Description |
| :-------- | :------- | :------------------------- |
| `Body` | `List` | **Retorna uma lista de hóspedes** |
 
```
Response - 200
[
 {
 		"id": Long,
		"name": String,
		"cpf": String,
		"phone": String
 }
]
```
 
#### Criar nova reserva
 
```http
 POST /api/reserva
```
 
| Parameter | Type | Description |
| :-------- | :------- | :------------------------- |
| `Body` | `void` | **Salvar um novo hóspede no banco de dados** |
 
```
Response - 200
```
```
RequestBody
{
  "checkin": "LocalDateTime" - (2023-10-20T14:30:00)
  "checkout": "LocalDateTime" - (2023-10-21T14:30:00)
  "id_guest": Long,
  "isGarage": boolean
}
```

## Modo de instalar
- Instale Java 11, Spring Boot e PostgreSQL;
- Clone o projeto na sua maquina e importe o projeto para sua IDE;
- No PostgreSQL, crie um novo banco de dados com o nome de 'gestao-hospedagem'
- Rode a aplicação

## Testes
- Após seguir os passos de instalação
- Navegue até o diretório rais do projeto
- Siga para o diretório de testes
- ```cd gestao-hospedagem\src\test\java\com\example\gestaohospedagem\service ```
- Execute os teste com Junit4

Se você encontrar algum problema ou tiver dúvidas, sinta-se à vontade para abrir uma issue.

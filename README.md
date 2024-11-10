# aop

POST http://localhost:8080/account
{
"client_id": 1,
"account_type": "DEBIT",
"account_balance": 100
}

POST http://localhost:8080/transaction
{
"account_id": 1,
"amount": 100
}

## Kafka task 1
### Kafka 1.1

Для ошибки
POST http://localhost:8080/transaction
{
"account_id": 11,
"amount": 100
}

### Kafka 1.2
Долгий запрос

GET http://localhost:8080/timeout-aspect-long-execution

Короткий запрос

GET http://localhost:8080/timeout-aspect-short-execution


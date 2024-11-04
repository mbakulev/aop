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
